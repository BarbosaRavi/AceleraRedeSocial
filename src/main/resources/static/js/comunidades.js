document.addEventListener("DOMContentLoaded", function () {
    carregarComunidades();
});

// Função para gerar um UUID válido
function gerarUUID() {
    // Gera um UUID (baseado na especificação UUID v4)
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0,
            v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

// Função para carregar e exibir as comunidades
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
            <button onclick="verComunidade('${comunidade.id}')">Ver Comunidade</button>
        `;
        communityList.appendChild(listItem);
    });
}

// Função para criar uma nova comunidade
function criarComunidade() {
    const nome = document.getElementById('communityName').value;
    const descricao = document.getElementById('communityDescription').value;
    const imagem = document.getElementById('communityImage').files[0];

    // Cria um novo objeto comunidade
    const novaComunidade = {
        id: gerarUUID(),  // Gerando um UUID válido
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

// Função para navegar para a página de detalhes da comunidade
function verComunidade(id) {
    // Lógica para navegar até a página da comunidade específica, usando o ID
    window.location.href = `/comunidades/detalhe/${id}`; // Passando o ID na URL
}

document.addEventListener("DOMContentLoaded", function () {
    carregarDetalhesComunidade();
    carregarPosts(); // Chama a função para carregar posts
});

// Função para carregar os detalhes da comunidade na página de detalhes
function carregarDetalhesComunidade() {
    const urlParams = new URLSearchParams(window.location.search);
    const idComunidade = urlParams.get('id');  // Recupera o ID da comunidade da URL

    // Simulação de comunidades em memória
    const comunidades = window.comunidades || []; // Assume que as comunidades estão armazenadas globalmente

    // Encontra a comunidade correspondente pelo ID
    const comunidade = comunidades.find(c => c.id === idComunidade);

    if (comunidade) {
        document.getElementById('communityName').innerText = comunidade.ComunidadeNome;
        document.getElementById('communityImage').src = comunidade.ComunidadeImagem;
        document.getElementById('communityDescription').innerText = comunidade.ComunidadeDescricao;

        // Carregar publicações (se houver)
        carregarPublicacoes(comunidade);
    } else {
        alert("Comunidade não encontrada!");
    }
}

// Função para adicionar um novo post
function addPost() {
    const postContent = document.getElementById("newPostContent").value;
    if (postContent.trim() === "") {
        alert("O conteúdo do post não pode estar vazio!");
        return;
    }

    // Aqui você pode adicionar lógica para enviar o novo post para o servidor e atualizar a lista de posts
    const postsContainer = document.getElementById("posts");
    const postDiv = document.createElement("div");
    postDiv.textContent = postContent; // Exibe o conteúdo do post
    postsContainer.appendChild(postDiv); // Adiciona o novo post à lista de posts
    document.getElementById("newPostContent").value = ""; // Limpa o textarea
}

// Função para carregar publicações da comunidade
function carregarPublicacoes(comunidade) {
    const postsContainer = document.getElementById('posts');
    postsContainer.innerHTML = ''; // Limpa a lista de posts

    // Simulação de publicações em memória
    const publicacoes = window.publicacoes || []; // Assume que as publicações estão armazenadas globalmente

    // Filtra as publicações da comunidade atual
    const publicacoesDaComunidade = publicacoes.filter(p => p.comunidadeNome === comunidade.ComunidadeNome);

    // Exibe as publicações
    if (publicacoesDaComunidade.length > 0) {
        publicacoesDaComunidade.forEach(post => {
            const postElement = document.createElement('div');
            postElement.className = 'post';
            postElement.innerHTML = `<p><strong>${post.usuario}:</strong> ${post.conteudo}</p>`;
            postsContainer.appendChild(postElement);
        });
    } else {
        postsContainer.innerHTML = '<p>Nenhuma publicação encontrada nesta comunidade.</p>';
    }
}
