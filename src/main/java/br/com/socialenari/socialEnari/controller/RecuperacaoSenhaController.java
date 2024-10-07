package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.EmailService;
import br.com.socialenari.socialEnari.service.UsuarioService;
import br.com.socialenari.socialEnari.utils.CodigoRecuperacaoUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class RecuperacaoSenhaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    private Map<String, CodigoRecuperacao> codigosRecuperacao = new ConcurrentHashMap<>();

    @GetMapping("/recuperacao")
    public String exibirFormularioRecuperacao() {
        return "recuperacao";
    }

    @PostMapping("/recuperacao")
    public String recuperarSenha(@RequestParam("email") String email, HttpSession session, Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        if (usuario != null) {
            String codigoRecuperacao = CodigoRecuperacaoUtils.gerarCodigo();
            String assunto = "Recuperação de Senha";
            String corpo = "TOKEN PARA RECUPERAÇÃO: " + codigoRecuperacao;
            codigosRecuperacao.put(email, new CodigoRecuperacao(codigoRecuperacao, System.currentTimeMillis() + 10 * 60 * 1000));
            session.setAttribute("email", email);
            emailService.enviarEmailRecuperacao(email, assunto, corpo);
            model.addAttribute("mensagem", "Instruções para recuperação de senha foram enviadas para o seu e-mail.");
        } else {
            model.addAttribute("mensagem", "Email não encontrado.");
        }
        return "recuperacao";
    }

    @PostMapping("/recuperacao/confirmar")
    public String exibirFormularioConfirmacao(@RequestParam("codigo") String codigo, HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");

        if (email == null) {
            model.addAttribute("mensagem", "Sessão expirada. Por favor, inicie o processo de recuperação novamente.");
            return "recuperacao";  // Retorna ao formulário de recuperação
        }

        CodigoRecuperacao codigoRecuperacao = codigosRecuperacao.get(email);
        if (codigoRecuperacao != null && codigoRecuperacao.getCodigo().equals(codigo) &&
                System.currentTimeMillis() <= codigoRecuperacao.getExpiracao()) {
            model.addAttribute("email", email);
            return "trocarSenha";  // Redireciona para a página de troca de senha
        } else {
            model.addAttribute("erro", "Código inválido ou expirado.");  // Mensagem de erro
            model.addAttribute("email", email);  // Mantém o email no modelo
            return "confirmarCodigo";  // Retorna para a mesma página de confirmação
        }
    }

    @PostMapping("/recuperacao/trocarSenha")
    public String trocarSenha(@RequestParam("novaSenha") String novaSenha,
                              @RequestParam("confirmeSenha") String confirmeSenha,
                              HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");

        if (email == null) {
            model.addAttribute("mensagem", "Sessão expirada. Por favor, inicie o processo de recuperação novamente.");
            return "recuperacao";
        }

        if (novaSenha.equals(confirmeSenha)) {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (usuario != null) {
                usuario.setSenha(novaSenha);
                usuarioService.atualizarUsuario(usuario);
                session.removeAttribute("email");
                model.addAttribute("mensagem", "Senha trocada com sucesso.");
                return "redirect:/login";
            } else {
                model.addAttribute("mensagem", "Usuário não encontrado.");
            }
        } else {
            model.addAttribute("mensagem", "As senhas não conferem.");
        }
        return "trocarSenha";
    }

    private static class CodigoRecuperacao {
        private String codigo;
        private long expiracao;

        public CodigoRecuperacao(String codigo, long expiracao) {
            this.codigo = codigo;
            this.expiracao = expiracao;
        }

        public String getCodigo() {
            return codigo;
        }

        public long getExpiracao() {
            return expiracao;
        }
    }
}
