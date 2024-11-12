let usuarioLogado = {}; // Objeto para armazenar o usuário logado
let postsData = []; // Array para armazenar as publicações e seus dados

// Função para adicionar um novo post
function addPost() {
    const postContent = document.getElementById("newPostContent").value;

    if (postContent.trim() === "") {
        alert("Você deve inserir um conteúdo para publicar!");
        return;
    }

    fetch("/publicacoes", {
        method: "POST",
        headers: {
            'Content-Type': 'text/plain',
        },
        body: postContent
    })
    .then(response => {
        if (response.ok) {
            document.getElementById("newPostContent").value = "";
            loadPosts();
        } else {
            alert("Erro ao publicar a postagem. Código: " + response.status);
        }
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao conectar ao servidor.");
    });
}

// Função para carregar as publicações do servidor
function loadPosts() {
    fetch("/publicacoes")
    .then(response => response.text())
    .then(posts => {
        const postsContainer = document.getElementById("posts");
        postsContainer.innerHTML = "";
        postsData = [];

        const parsedPosts = posts.split("\n").filter(post => post.trim() !== "");
        
        parsedPosts.forEach((post, index) => {
            const [usuario, conteudoComData] = post.split(": ");
            const [conteudo, dataHora] = conteudoComData.split(" (");
            const tempoPassado = dataHora.split(")")[0];
            const likesCount = parseInt(conteudoComData.split(" - ")[1]) || 0;

            postsData.push({
                id: index + 1,
                usuario,
                conteudo,
                tempoPassado,
                likes: likesCount,
                liked: false,
                comments: []
            });
        });

        const fotoPerfil = localStorage.getItem('fotoPerfil') || 'https://cdn-icons-png.flaticon.com/512/17/17004.png';

        postsData.forEach((postData, index) => {
            const postElement = document.createElement("div");
            postElement.classList.add("post");

            // Adicionando a imagem de perfil ao lado do nome do autor
            postElement.innerHTML = `
                <div class="post-header">
                    <div class="author-info">
                        <img src="${fotoPerfil}" alt="Foto do Autor" class="profile-img">
                        <span class="username">${postData.usuario}</span>
                    </div>
                    <span class="time">${postData.tempoPassado}</span>
                </div>
                <div class="post-content">
                    <p>${postData.conteudo}</p>
                </div>
                <div class="post-actions">
                    <button id="like-button-${index}" onclick="toggleLike(${index})">${postData.liked ? "Descurtir" : "Curtir"}</button>
                    <span class="likes" id="likes-${index}">${postData.likes} curtidas</span>
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
    });
}

// Função para curtir ou descurtir uma publicação
// Função para curtir ou descurtir uma publicação
function toggleLike(postIndex) {
    const post = postsData[postIndex];
    const postId = post.id;

    if (post.liked) {
        fetch(`/publicacoes/${postId}/descurtir`, { // Colocamos as aspas corretamente
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
        fetch(`/publicacoes/${postId}/curtir`, { // Colocamos as aspas corretamente
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




// Função para atualizar o botão e a contagem de curtidas na interface
function updateLikeUI(index) {
    const likeButton = document.getElementById(`like-button-${index}`);
    likeButton.textContent = postsData[index].liked ? "Descurtir" : "Curtir";
    const likesSpan = document.getElementById(`likes-${index}`);
    likesSpan.innerText = `${postsData[index].likes} curtidas`;
}

// Função para mostrar/ocultar comentários
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

    postsData[index].comments.push(commentText);
    commentInput.value = "";
    renderComments(index);
}

// Função para renderizar comentários de um post específico
function renderComments(index) {
    const commentsList = document.getElementById(`comments-list-${index}`);
    commentsList.innerHTML = "";

    const comments = postsData[index].comments.slice(-5); // Últimos 5 comentários
    comments.forEach(comment => {
        const commentElement = document.createElement("p");
        commentElement.innerText = comment;
        commentsList.appendChild(commentElement);
    });

    // Limita a altura dos comentários e adiciona rolagem
    if (postsData[index].comments.length > 5) {
        commentsList.style.maxHeight = "100px";
        commentsList.style.overflowY = "scroll";
    }
}

// Carrega as publicações automaticamente ao carregar a página
document.addEventListener("DOMContentLoaded", loadPosts);

document.addEventListener("DOMContentLoaded", function () {
    // Verifica se a foto de perfil e o nome do usuário estão armazenados no localStorage
    const fotoPerfil = localStorage.getItem('fotoPerfil');
    const usuarioNome = localStorage.getItem('usuarioNome') || 'Usuário Anônimo'; // Fallback caso não tenha nome

    console.log('Foto de Perfil:', fotoPerfil); // Debug: Verifica se a foto foi encontrada no localStorage
    console.log('Nome do Usuário:', usuarioNome); // Debug: Verifica o nome do usuário

    // Se a foto de perfil estiver armazenada, ela será aplicada
    if (fotoPerfil) {
        document.getElementById('profilePhotoMain').src = fotoPerfil; // Atualiza a foto de perfil principal
    }

    // Atualiza o nome do usuário na página
    document.getElementById('user-name').innerText = usuarioNome;
    document.getElementById('user-name-sidebar').innerText = usuarioNome;
});

// Função para atualizar a foto de perfil no localStorage
function updateProfilePhoto(file) {
    const reader = new FileReader();
    reader.onloadend = function() {
        const photoUrl = reader.result; // Obtém a URL da imagem carregada
        localStorage.setItem('fotoPerfil', photoUrl); // Salva a foto no localStorage
        document.getElementById('profilePhotoMain').src = photoUrl; // Atualiza a foto de perfil na página
    };
    reader.readAsDataURL(file); // Lê a imagem como URL de dados
}

// Exemplo de uso do input para atualizar a foto de perfil
// <input type="file" onchange="updateProfilePhoto(this.files[0])">

