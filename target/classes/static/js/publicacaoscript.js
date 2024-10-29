let usuarioLogado = {}; // Objeto para armazenar o usuário logado

let postsData = []; // Array para armazenar as publicações e seus dados

// Função para adicionar um novo post
function addPost() {
    const postContent = document.getElementById("newPostContent").value; // Obtém o conteúdo do post do textarea

    if (postContent.trim() === "") { // Verifica se o conteúdo não está vazio
        alert("Você deve inserir um conteúdo para publicar!");
        return;
    }

    // Envia uma requisição POST para o backend com o conteúdo da publicação
    fetch("/publicacoes", {
        method: "POST",
        headers: {
            'Content-Type': 'text/plain',
        },
        body: postContent // O conteúdo do post é enviado como texto simples
    })
    .then(response => {
        if (response.ok) {
            document.getElementById("newPostContent").value = ""; // Limpa o campo de texto
            loadPosts(); // Recarrega as publicações para incluir a nova
        } else {
            alert("Erro ao publicar a postagem. Código: " + response.status); // Exibe erro se a requisição falhar
        }
    })
    .catch(error => {
        console.error("Erro:", error); // Loga o erro no console
        alert("Erro ao conectar ao servidor.");
    });
}

// Função para carregar as publicações do servidor
function loadPosts() {
    fetch("/publicacoes")
    .then(response => response.text()) // Converte a resposta em texto
    .then(posts => {
        const postsContainer = document.getElementById("posts"); // Local onde as publicações serão inseridas
        postsContainer.innerHTML = ""; // Limpa as publicações existentes
        postsData = []; // Limpa os dados de publicações antigos

        const parsedPosts = posts.split("\n").filter(post => post.trim() !== "");
        
        parsedPosts.forEach((post, index) => {
            const [usuario, conteudoComData] = post.split(": ");
            const [conteudo, dataHora] = conteudoComData.split(" (");
            const tempoPassado = dataHora.split(")")[0]; // Extraindo apenas o tempo
            const likesCount = parseInt(conteudoComData.split(" - ")[1]) || 0; // Contagem de curtidas

            // Aqui você deve adicionar a atribuição do ID correto da publicação
            postsData.push({ id: index + 1, usuario, conteudo, tempoPassado, likes: likesCount, liked: false, comments: [] }); // Armazena dados do post
        });

        postsData.forEach((postData, index) => {
            const postElement = document.createElement("div");
            postElement.classList.add("post");
            postElement.innerHTML = `
                <div class="post-header">
                    <span class="username">${postData.usuario}</span> 
                    <span class="time">${postData.tempoPassado}</span>
                </div>
                <div class="post-content">
                    <p>${postData.conteudo}</p>
                </div>
                <div class="post-actions">
                    <button onclick="toggleLike(${index})">${postData.liked ? "Descurtir" : "Curtir"}</button>
                    <span class="likes">${postData.likes} curtidas</span>
                </div>
                <div class="comments-container">
                    <button onclick="toggleComments(${index})">Mostrar Comentários</button>
                    <div class="comments" id="comments-${index}" style="display:none;">
                        <div class="comments-list"></div>
                        <textarea id="comment-${index}" placeholder="Adicione um comentário..."></textarea>
                        <button onclick="addComment(${index})">Comentar</button>
                    </div>
                </div>
            `;
            postsContainer.prepend(postElement); // Insere o post no início da lista
        });
    })
    .catch(error => {
        console.error("Erro:", error);
    });
}

// Função para mostrar ou ocultar os comentários
function toggleComments(postIndex) {
    const commentsDiv = document.getElementById(`comments-${postIndex}`);
    commentsDiv.style.display = commentsDiv.style.display === "none" ? "block" : "none"; // Alterna a exibição
}

// Função para adicionar um comentário
function addComment(postIndex) {
    const commentInput = document.getElementById(`comment-${postIndex}`);
    const commentText = commentInput.value.trim();

    if (commentText === "") {
        alert("Você deve inserir um comentário!");
        return;
    }

    // Adiciona o comentário ao array de comentários do post
    postsData[postIndex].comments.push(commentText);
    commentInput.value = ""; // Limpa o campo de texto

    renderComments(postIndex); // Re-renderiza os comentários
}

// Função para renderizar os comentários
function renderComments(postIndex) {
    const commentsListDiv = document.querySelector(`#comments-${postIndex} .comments-list`);
    commentsListDiv.innerHTML = ""; // Limpa os comentários existentes

    const comments = postsData[postIndex].comments.slice(-5); // Pega os últimos 5 comentários
    comments.forEach(comment => {
        const commentElement = document.createElement("div");
        commentElement.textContent = comment;
        commentsListDiv.appendChild(commentElement);
    });

    // Se houver mais de 5 comentários, adiciona a barra de rolagem
    if (postsData[postIndex].comments.length > 5) {
        commentsListDiv.style.maxHeight = "100px"; // Define a altura máxima
        commentsListDiv.style.overflowY = "scroll"; // Adiciona a barra de rolagem
    }
}

// Função para curtir ou descurtir uma publicação
function toggleLike(postIndex) {
    const post = postsData[postIndex];

    // Use o ID da publicação em vez do índice
    const postId = post.id; // Assumindo que você adicionou o ID nas publicações

    if (post.liked) {
        fetch(`/publicacoes/${postId}/descurtir`, {
            method: "POST"
        })
        .then(response => {
            if (response.ok) {
                post.likes--; // Decrementa a contagem de curtidas
                post.liked = false; // Marca como não curtido
            } else {
                alert("Erro ao descurtir a publicação.");
            }
            loadPosts(); // Recarrega as publicações para refletir as alterações
        })
        .catch(error => {
            console.error("Erro ao descurtir a publicação:", error);
            alert("Erro ao descurtir a publicação.");
        });
    } else {
        fetch(`/publicacoes/${postId}/curtir`, {
            method: "POST"
        })
        .then(response => {
            if (response.ok) {
                post.likes++; // Incrementa a contagem de curtidas
                post.liked = true; // Marca como curtido
            } else {
                alert("Erro ao curtir a publicação.");
            }
            loadPosts(); // Recarrega as publicações para refletir as alterações
        })
        .catch(error => {
            console.error("Erro ao curtir a publicação:", error);
            alert("Erro ao curtir a publicação.");
        });
    }
}

// Carrega as publicações automaticamente ao carregar a página
document.addEventListener("DOMContentLoaded", loadPosts);
