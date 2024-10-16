// js/comunidades.js

document.addEventListener("DOMContentLoaded", function () {
    carregarComunidades();
});

function carregarComunidades() {
    const communityList = document.getElementById('communityList');
    communityList.innerHTML = ''; // Limpa a lista existente

    // Simulação de comunidades em memória
    const comunidades = window.comunidades || []; // Assume que as comunidades estão armazenadas globalmente

    // Adiciona cada comunidade à lista
    comunidades.forEach(comunidade => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <img src="${comunidade.ComunidadeImagem}" alt="Imagem da Comunidade" width="50">
            <strong>${comunidade.ComunidadeNome}</strong>
            <p>${comunidade.ComunidadeDescricao}</p>
            <button onclick="verComunidade('${comunidade.ComunidadeNome}')">Ver Comunidade</button>
        `;
        communityList.appendChild(listItem);
    });
}

function criarComunidade() {
    const nome = document.getElementById('communityName').value;
    const descricao = document.getElementById('communityDescription').value;
    const imagem = document.getElementById('communityImage').files[0];

    // Cria um novo objeto comunidade
    const novaComunidade = {
        ComunidadeNome: nome,
        ComunidadeDescricao: descricao,
        ComunidadeImagem: URL.createObjectURL(imagem) // Gera URL temporária para exibição
    };

    // Adiciona a nova comunidade à lista global
    window.comunidades = window.comunidades || []; // Inicializa se não existir
    window.comunidades.push(novaComunidade);

    // Limpa os campos do formulário
    document.getElementById('createCommunityForm').reset();
    carregarComunidades(); // Atualiza a lista exibida
}

function verComunidade(nome) {
    // Lógica para navegar até a página da comunidade específica
    window.location.href = `/comunidadeDetalhe.html?nome=${encodeURIComponent(nome)}`; // Modifique conforme necessário
}
