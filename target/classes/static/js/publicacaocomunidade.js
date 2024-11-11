let postsData = []; // Array para armazenar as publicações e seus dados da comunidade

// Função para adicionar um novo post na comunidade
function addPost(comunidadeId) {
    const postContent = document.getElementById("newPostContent").value;

    if (postContent.trim() === "") {
        alert("Você deve inserir um conteúdo para publicar!");
        return;
    }

    // Envia a nova publicação para o servidor
    fetch(`/comunidades/${comunidadeId}/publicacoes`, {
        method: "POST",
        headers: {
            'Content-Type': 'text/plain', // Envia como texto simples
        },
        body: postContent
    })
    .then(response => {
        if (response.ok) {
            document.getElementById("newPostContent").value = ""; // Limpa o campo de texto
            loadPosts(comunidadeId); // Recarrega as publicações após enviar
        } else {
            response.text().then(errText => {
                alert(`Erro ao publicar a postagem: ${errText}`);
            });
        }
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao conectar ao servidor.");
    });
}

// Função para carregar as publicações da comunidade
function loadPosts(comunidadeId) {
    fetch(`/comunidades/${comunidadeId}/publicacoes`)
    .then(response => response.text()) // Recebe a resposta como texto simples
    .then(postsText => {
        const postsContainer = document.getElementById("posts");
        postsContainer.innerHTML = ""; // Limpa o container antes de renderizar novamente
        postsData = []; // Limpa os dados armazenados

        const posts = postsText.split("\n").filter(post => post.trim() !== ""); // Divide as publicações por linha
        
        posts.forEach((post, index) => {
            const [usuario, conteudoComData] = post.split(": ");
            const [conteudo, dataHora] = conteudoComData.split(" (");
            const tempoPassado = dataHora.split(")")[0];

            postsData.push({
                id: index + 1,
                usuario: usuario.trim(),
                conteudo: conteudo.trim(),
                tempoPassado: tempoPassado.trim(),
                likes: 0, // Inicializa as curtidas como 0
                liked: false, // Inicializa a publicação como não curtida
                comments: [] // Inicializa os comentários
            });

            const postElement = document.createElement("div");
            postElement.classList.add("post");

            postElement.innerHTML = `
                <div class="user-info">
                    <span class="username">${postsData[index].usuario}</span>
                    <span class="time">${postsData[index].tempoPassado}</span>
                </div>
                <div class="post-content">
                    <p>${postsData[index].conteudo}</p>
                </div>
                <div class="post-actions">
                    <button id="like-button-${index}" onclick="toggleLike(${index})">${postsData[index].liked ? "Descurtir" : "Curtir"}</button>
                    <span class="likes" id="likes-${index}">${postsData[index].likes} curtidas</span>
                    <button onclick="toggleComments(${index})">Comentários</button>
                </div>
                <div class="comments-container">
                    <div class="comments" id="comments-${index}" style="display:none;">
                        <div class="comments-list" id="comments-list-${index}"></div>
                        <textarea id="comment-${index}" placeholder="Adicione um comentário..."></textarea>
                        <button onclick="addComment(${index})">Comentar</button>
                    </div>
                </div>
            `;
            postsContainer.prepend(postElement);
        });
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao carregar as publicações.");
    });
}

// Função para curtir ou descurtir uma publicação
function toggleLike(postIndex) {
    const post = postsData[postIndex];
    const postId = post.id;

    const action = post.liked ? "descurtir" : "curtir"; // Define a ação com base no estado atual

    fetch(`/comunidades/${postId}/publicacoes/${action}`, {
        method: "POST"
    })
    .then(response => {
        if (response.ok) {
            post.likes += post.liked ? -1 : 1; // Incrementa ou decrementa
            post.liked = !post.liked; // Alterna o estado de "curtido"
            updateLikeUI(postIndex); // Atualiza a UI para refletir a alteração
        } else {
            response.text().then(errText => {
                alert(`Erro ao ${action} a publicação: ${errText}`);
            });
        }
    })
    .catch(error => {
        console.error("Erro ao atualizar a curtida:", error);
        alert("Erro ao conectar ao servidor.");
    });
}

// Função para atualizar o botão de curtidas na interface
function updateLikeUI(index) {
    const likeButton = document.getElementById(`like-button-${index}`);
    likeButton.textContent = postsData[index].liked ? "Descurtir" : "Curtir";
    const likesSpan = document.getElementById(`likes-${index}`);
    likesSpan.innerText = `${postsData[index].likes} curtidas`;
}

// Função para mostrar ou ocultar comentários
function toggleComments(index) {
    const commentsSection = document.getElementById(`comments-${index}`);
    commentsSection.style.display = commentsSection.style.display === "none" ? "block" : "none";
}

// Função para adicionar um comentário a um post
function addComment(index) {
    const commentInput = document.getElementById(`comment-${index}`);
    const commentText = commentInput.value.trim();

    if (commentText === "") {
        alert("O comentário não pode estar vazio!");
        return;
    }

    // Simulação de adição de comentário no array (ou enviar ao servidor)
    postsData[index].comments.push(commentText); 
    commentInput.value = ""; // Limpa o campo de comentário
    renderComments(index); // Atualiza a lista de comentários
}

// Função para renderizar os comentários de um post específico
function renderComments(index) {
    const commentsList = document.getElementById(`comments-list-${index}`);
    commentsList.innerHTML = ""; // Limpa os comentários antigos

    const comments = postsData[index].comments.slice(-5); // Exibe os últimos 5 comentários
    comments.forEach(comment => {
        const commentElement = document.createElement("p");
        commentElement.innerText = comment;
        commentsList.appendChild(commentElement);
    });

    // Limita a altura da lista de comentários e adiciona rolagem
    if (postsData[index].comments.length > 5) {
        commentsList.style.maxHeight = "100px";
        commentsList.style.overflowY = "scroll";
    }
}

// Carrega as publicações ao carregar a página
document.addEventListener("DOMContentLoaded", function() {
    const comunidadeId = 1; // Você pode pegar o ID da comunidade de alguma maneira
    loadPosts(comunidadeId); // Carrega as publicações da comunidade
});
