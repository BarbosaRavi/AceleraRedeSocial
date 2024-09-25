let usuarioLogado = {
    nome: "Usuário1" // Simulando um usuário logado; deve ser obtido após o login real
};

function addPost() {
    const postContent = document.getElementById("newPostContent").value;

    if (postContent.trim() === "") {
        alert("Você deve inserir um conteúdo para publicar!");
        return;
    }

    // Usando texto simples no corpo da requisição
    fetch("/publicacoes", {
        method: "POST",
        headers: {
            'Content-Type': 'text/plain', // Enviando como texto simples
        },
        body: postContent
    })
    .then(response => {
        if (response.ok) {
            // Após publicar, atualiza o feed
            document.getElementById("newPostContent").value = ""; // Limpa o textarea
            loadPosts(); // Recarrega as publicações
        } else {
            alert("Erro ao publicar a postagem.");
        }
    })
    .catch(error => {
        console.error("Erro:", error);
    });
}

function loadPosts() {
    fetch("/publicacoes")
    .then(response => response.text())
    .then(posts => {
        const postsContainer = document.getElementById("posts");
        postsContainer.innerHTML = ""; // Limpa as publicações existentes

        // A resposta deve ser um texto formatado (ex: "usuario1: Conteúdo da publicação\nusuario2: Outro conteúdo")
        const parsedPosts = posts.split("\n").filter(post => post.trim() !== ""); // Dividindo por linhas
        parsedPosts.forEach(post => {
            const [usuario, conteudo] = post.split(": "); // Supondo que o formato seja "usuario: conteudo"
            const postElement = document.createElement("div");
            postElement.className = "post";
            postElement.innerHTML = `
                <div class="post-header">
                    <span class="username">${usuario}</span> <span class="time">2h atrás</span>
                </div>
                <div class="post-content">
                    <p>${conteudo}</p>
                </div>
                <div class="post-actions">
                    <button onclick="likePost(this)">Curtir</button> <span class="likes">0 curtidas</span>
                </div>
            `;
            postsContainer.appendChild(postElement);
        });
    })
    .catch(error => {
        console.error("Erro:", error);
    });
}

// Carrega publicações ao iniciar a página
document.addEventListener("DOMContentLoaded", loadPosts);
