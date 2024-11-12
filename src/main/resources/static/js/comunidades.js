document.addEventListener("DOMContentLoaded", function () {
    carregarComunidades();
});

// Função para gerar um UUID válido
function gerarUUID() {
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

    const comunidades = window.comunidades || [];

    comunidades.forEach(comunidade => {
        const listItem = document.createElement('li');
        listItem.className = 'community-item'; // Classe CSS para estilizar o item da lista

        listItem.innerHTML = `
            <img src="${comunidade.ComunidadeImagem}" alt="Imagem da Comunidade" class="community-image">
            <div class="community-info">
                <strong class="community-name">${comunidade.ComunidadeNome}</strong>
                <p class="community-description">${comunidade.ComunidadeDescricao}</p>
            </div>
            <button onclick="verComunidade('${comunidade.id}')" class="view-community-button">Ver Comunidade</button>
        `;
        
        communityList.appendChild(listItem);
    });
}

// Função para criar uma nova comunidade
function criarComunidade() {
    const nome = document.getElementById('communityName').value;
    const descricao = document.getElementById('communityDescription').value;
    const imagem = document.getElementById('communityImage').files[0];

    const novaComunidade = {
        id: gerarUUID(),
        ComunidadeNome: nome,
        ComunidadeDescricao: descricao,
        ComunidadeImagem: URL.createObjectURL(imagem)
    };

    window.comunidades = window.comunidades || [];
    window.comunidades.push(novaComunidade);

    document.getElementById('createCommunityForm').reset();
    carregarComunidades();
}

function verComunidade(id) {
    window.location.href = `/comunidades/detalhe/${id}`;
}

document.addEventListener("DOMContentLoaded", function () {
    carregarDetalhesComunidade();
    carregarPosts();
});

function carregarDetalhesComunidade() {
    const urlParams = new URLSearchParams(window.location.search);
    const idComunidade = urlParams.get('id');

    const comunidades = window.comunidades || [];
    const comunidade = comunidades.find(c => c.id === idComunidade);

    if (comunidade) {
        document.getElementById('communityName').innerText = comunidade.ComunidadeNome;
        document.getElementById('communityImage').src = comunidade.ComunidadeImagem;
        document.getElementById('communityDescription').innerText = comunidade.ComunidadeDescricao;

        carregarPublicacoes(comunidade);
    } else {
        alert("Comunidade não encontrada!");
    }
}

function addPost() {
    const postContent = document.getElementById("newPostContent").value;
    if (postContent.trim() === "") {
        alert("O conteúdo do post não pode estar vazio!");
        return;
    }

    const postsContainer = document.getElementById("posts");
    const postDiv = document.createElement("div");
    postDiv.className = 'post-item';
    postDiv.textContent = postContent;
    postsContainer.appendChild(postDiv);
    document.getElementById("newPostContent").value = "";
}

function carregarPublicacoes(comunidade) {
    const postsContainer = document.getElementById('posts');
    postsContainer.innerHTML = '';

    const publicacoes = window.publicacoes || [];
    const publicacoesDaComunidade = publicacoes.filter(p => p.comunidadeNome === comunidade.ComunidadeNome);

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
