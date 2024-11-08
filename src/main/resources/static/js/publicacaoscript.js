let usuarioLogado = {}; // Objeto para armazenar os dados do usuário logado, ainda não utilizado no código atual.
let postsData = []; // Array para armazenar as publicações e seus dados, como usuário, conteúdo, tempo, curtidas e comentários.

// Função para adicionar um novo post
function addPost() {
    const postContent = document.getElementById("newPostContent").value; // Obtém o conteúdo do post inserido pelo usuário.

    if (postContent.trim() === "") { // Verifica se o conteúdo do post está vazio.
        alert("Você deve inserir um conteúdo para publicar!"); // Exibe um alerta caso o conteúdo esteja vazio.
        return; // Interrompe a execução da função se o conteúdo estiver vazio.
    }

    // Faz uma requisição POST para adicionar o novo post ao servidor.
    fetch("/publicacoes", {
        method: "POST",
        headers: {
            'Content-Type': 'text/plain', // Define o tipo de conteúdo como texto simples.
        },
        body: postContent // Envia o conteúdo do post no corpo da requisição.
    })
    .then(response => {
        if (response.ok) { // Verifica se a resposta foi bem-sucedida.
            document.getElementById("newPostContent").value = ""; // Limpa o campo de inserção de conteúdo.
            loadPosts(); // Carrega novamente as publicações após o post ser adicionado.
        } else {
            alert("Erro ao publicar a postagem. Código: " + response.status); // Exibe um alerta caso ocorra erro ao publicar.
        }
    })
    .catch(error => {
        console.error("Erro:", error); // Loga o erro no console.
        alert("Erro ao conectar ao servidor."); // Exibe um alerta caso haja erro na requisição.
    });
}

// Função para carregar as publicações do servidor
function loadPosts() {
    // Obtém a foto de perfil armazenada no localStorage. Se não houver, usa uma foto padrão.
    const fotoPerfilArmazenada = localStorage.getItem('fotoPerfil') || 'https://cdn-icons-png.flaticon.com/512/17/17004.png'; // Foto padrão, caso não tenha sido definida

    // Faz uma requisição para obter as publicações do servidor
    fetch("/publicacoes")
    .then(response => response.text()) // Espera a resposta do servidor como texto
    .then(posts => {
        // Acessa o container onde as publicações serão exibidas
        const postsContainer = document.getElementById("posts");
        postsContainer.innerHTML = ""; // Limpa o conteúdo do container antes de adicionar novos posts
        postsData = []; // Limpa o array de dados das publicações

        // Divide as publicações em linhas e filtra as linhas vazias
        const parsedPosts = posts.split("\n").filter(post => post.trim() !== "");

        // Itera sobre as publicações
        parsedPosts.forEach((post, index) => {
            // Divide a publicação em partes (usuário e conteúdo com data/hora)
            const [usuario, conteudoComData] = post.split(": ");
            const [conteudo, dataHora] = conteudoComData.split(" (");
            const tempoPassado = dataHora.split(")")[0]; // Obtém o tempo passado desde a postagem

            // Adiciona os dados da publicação ao array postsData
            postsData.push({
                id: index + 1, // Atribui um ID único para cada post
                usuario, // Nome do usuário que fez a publicação
                conteudo, // Conteúdo da publicação
                tempoPassado, // Tempo decorrido desde a publicação
                likes: parseInt(conteudoComData.split(" - ")[1]) || 0, // Obtém a quantidade de curtidas, caso existam
                liked: false, // Define que o post ainda não foi curtido
                comments: [] // Inicializa um array vazio para os comentários
            });

            // Cria um novo elemento de post (div)
            const postElement = document.createElement("div");
            postElement.classList.add("post"); // Adiciona a classe "post" ao elemento

            // Adiciona o HTML do post ao novo elemento
            postElement.innerHTML = `
                <!-- Informações do usuário e tempo -->
                <div class="user-info">
                    <!-- Foto de perfil -->
                    <img src="${fotoPerfilArmazenada}" alt="Imagem de Perfil" class="profile-image" data-usuario="${usuario}
                    
      
                    <!-- Tempo passado desde a publicação -->
                    <span class="time">${tempoPassado}</span>
                </div>

                <!-- Conteúdo da publicação -->
                <div class="post-content">
                    <p>${conteudo}</p>
                </div>

                <!-- Ações do post (Curtir e Curtidas) -->
                <div class="post-actions">
                    <button onclick="toggleLike(${index})">
                        ${postsData[index].liked ? "Descurtir" : "Curtir"}
                    </button>
                    <span class="likes" id="likes-${index}">${postsData[index].likes} curtidas</span>
                </div>

                <!-- Seção de Comentários -->
                <div class="comments-container">
                    <button onclick="toggleComments(${index})">Mostrar Comentários</button>
                    <div class="comments" id="comments-${index}" style="display:none;">
                        <div class="comments-list" id="comments-list-${index}"></div>
                        <textarea id="comment-${index}" placeholder="Adicione um comentário..."></textarea>
                        <button onclick="addComment(${index})">Comentar</button>
                    </div>
                </div>
            `;

            // Adiciona um evento de clique na foto de perfil da publicação
            postElement.querySelector('.profile-image').addEventListener('click', function() {
                const usuario = this.getAttribute('data-usuario'); // Obtém o nome do usuário da foto de perfil
                window.location.href = `/perfil/${usuario}`; // Redireciona para o perfil do usuário
            });

            // Adiciona o novo post ao container de posts na página (em ordem cronológica)
            postsContainer.prepend(postElement); // Insere o novo post no início da lista
        });
    })
    .catch(error => {
        console.error("Erro:", error); // Exibe um erro no console caso algo dê errado com a requisição
    });
}


// Função para curtir/descurtir um post
function toggleLike(index) {
    const post = postsData[index]; // Obtém o post correspondente ao índice.
    post.liked = !post.liked; // Alterna o estado de curtido/descurtido.
    post.likes += post.liked ? 1 : -1; // Incrementa ou decrementa o número de curtidas.

    // Atualiza a interface com o novo estado do botão de curtida e a contagem de curtidas.
    const likeButton = document.querySelector(`#likes-${index}`).previousElementSibling;
    likeButton.textContent = post.liked ? "Descurtir" : "Curtir";

    const likesSpan = document.getElementById(`likes-${index}`);
    likesSpan.innerText = `${post.likes} curtidas`;
}

// Função para mostrar/ocultar comentários
function toggleComments(index) {
    const commentsSection = document.getElementById(`comments-${index}`); // Obtém a seção de comentários do post.
    commentsSection.style.display = commentsSection.style.display === "none" ? "block" : "none"; // Alterna a visibilidade da seção de comentários.
}

// Função para adicionar um comentário a um post
function addComment(index) {
    const commentInput = document.getElementById(`comment-${index}`); // Obtém o campo de texto para o novo comentário.
    const commentText = commentInput.value.trim(); // Obtém o texto do comentário, removendo espaços em branco extras.

    if (commentText === "") { // Verifica se o comentário está vazio.
        alert("O comentário não pode estar vazio!"); // Exibe um alerta caso o comentário esteja vazio.
        return; // Interrompe a execução da função se o comentário for vazio.
    }

    postsData[index].comments.push(commentText); // Adiciona o comentário à lista de comentários do post.
    commentInput.value = ""; // Limpa o campo de texto após adicionar o comentário.
    renderComments(index); // Renderiza os comentários atualizados.
}

document.addEventListener("DOMContentLoaded", function () {
    const profilePhotoMain = document.getElementById('profilePhotoMain');

    // Verifica se há uma foto de perfil armazenada no localStorage
    const fotoPerfilArmazenada = localStorage.getItem('fotoPerfil');
    if (fotoPerfilArmazenada) {
        profilePhotoMain.src = fotoPerfilArmazenada; // Define a foto armazenada como src
    }
});


// Função para renderizar comentários de um post específico
function renderComments(index) {
    const commentsList = document.getElementById(`comments-list-${index}`); // Obtém o elemento que exibirá os comentários.
    commentsList.innerHTML = ""; // Limpa a lista de comentários antes de renderizar os novos.

    // Itera sobre os comentários e os exibe na interface.
    postsData[index].comments.forEach(comment => {
        const commentElement = document.createElement("p");
        commentElement.innerText = comment; // Cria um elemento <p> para cada comentário.
        commentsList.appendChild(commentElement); // Adiciona o comentário à lista.
    });
}

// Carrega as publicações automaticamente ao carregar a página
document.addEventListener("DOMContentLoaded", loadPosts); // Quando a página for carregada, chama a função para carregar as publicações.
