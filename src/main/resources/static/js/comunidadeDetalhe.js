document.addEventListener("DOMContentLoaded", function () {
    carregarDetalhesComunidade();
    carregarPosts(); // Chama a função para carregar posts
});

function carregarDetalhesComunidade() {
    const urlParams = new URLSearchParams(window.location.search);
    const nomeComunidade = urlParams.get('nome');

    // Simulação de comunidades em memória
    const comunidades = window.comunidades || []; // Assume que as comunidades estão armazenadas globalmente

    // Encontra a comunidade correspondente
    const comunidade = comunidades.find(c => c.ComunidadeNome === nomeComunidade);

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

function carregarPosts() {
    // Simulando a recuperação de posts da comunidade
    const posts = [
        { usuario: "Usuario1", conteudo: "Este é um post na comunidade!" },
        { usuario: "Usuario2", conteudo: "Outro post interessante aqui!" }
    ];

    const postsList = document.getElementById('posts');
    posts.forEach(post => {
        const postElement = document.createElement('div');
        postElement.className = 'post';
        postElement.innerHTML = `<strong>${post.usuario}:</strong> ${post.conteudo}`;
        postsList.appendChild(postElement);
    });
}

function addPost() {
    const content = document.getElementById('newPostContent').value;
    if (content.trim() === "") {
        alert("O conteúdo do post não pode estar vazio!");
        return;
    }

    // Adiciona o novo post à lista (pode ser armazenado na memória)
    const postsList = document.getElementById('posts');
    const postElement = document.createElement('div');
    postElement.className = 'post';
    postElement.innerHTML = `<strong>Você:</strong> ${content}`;
    postsList.appendChild(postElement);

    // Simulação de adição de publicação em memória
    const novaPublicacao = {
        conteudo: content,
        usuario: "Você", // Pode ser modificado para o usuário logado
        comunidadeNome: document.getElementById('communityName').innerText // Associando a publicação à comunidade
    };

    // Salva a nova publicação na memória
    window.publicacoes = window.publicacoes || [];
    window.publicacoes.push(novaPublicacao);

    // Limpa o campo de entrada
    document.getElementById('newPostContent').value = "";
}
