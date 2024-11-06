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
    fetch("/publicacoes") // Faz uma requisição GET para obter todas as publicações.
    .then(response => response.text()) // Converte a resposta para texto.
    .then(posts => {
        const postsContainer = document.getElementById("posts"); // Obtém o contêiner onde as publicações serão exibidas.
        postsContainer.innerHTML = ""; // Limpa o contêiner de publicações antes de adicionar novas.
        postsData = []; // Limpa os dados armazenados das publicações.
 
        const parsedPosts = posts.split("\n").filter(post => post.trim() !== ""); // Divide as publicações por linha e filtra linhas vazias.
        const userPhoto = "https://cdn-icons-png.flaticon.com/512/17/17004.png"; // URL para a foto padrão de perfil do usuário.
 
        // Itera sobre as publicações e as processa para exibir corretamente na interface.
        parsedPosts.forEach((post, index) => {
            const [usuario, conteudoComData] = post.split(": "); // Divide o post em usuário e conteúdo com data.
            const [conteudo, dataHora] = conteudoComData.split(" ("); // Separa o conteúdo da data e hora.
            const tempoPassado = dataHora.split(")")[0]; // Extrai o tempo passado desde a publicação.
            const likesCount = parseInt(conteudoComData.split(" - ")[1]) || 0; // Obtém o número de curtidas, se disponível.
 
            postsData.push({
                id: index + 1, // Adiciona um ID único para cada post.
                usuario,
                conteudo,
                tempoPassado,
                likes: likesCount,
                liked: false, // Inicializa o status de curtida como 'false'.
                comments: [] // Inicializa a lista de comentários vazia.
            });
        });
 
        // Exibe as publicações carregadas na interface.
        postsData.forEach((postData, index) => {
            const postElement = document.createElement("div");
            postElement.classList.add("post"); // Adiciona a classe CSS para cada post.
            postElement.innerHTML = `
                <div class="user-info">
                    <img src="${userPhoto}" alt="Imagem de Perfil" class="profile-image">
                    <span class="username">${postData.usuario}</span>
                    <span class="time">${postData.tempoPassado}</span>
                </div>
                <div class="post-content">
                    <p>${postData.conteudo}</p>
                </div>
                <div class="post-actions">
                    <button onclick="toggleLike(${index})">${postData.liked ? "Descurtir" : "Curtir"}</button>
                    <span class="likes" id="likes-${index}">${postData.likes} curtidas</span>
                </div>
                <div class="comments-container">
                    <button onclick="toggleComments(${index})">Mostrar Comentários</button>
                    <div class="comments" id="comments-${index}" style="display:none;">
                        <div class="comments-list" id="comments-list-${index}"></div>
                        <textarea id="comment-${index}" placeholder="Adicione um comentário..."></textarea>
                        <button onclick="addComment(${index})">Comentar</button>
                    </div>
                </div>
            `;
            postsContainer.prepend(postElement); // Adiciona o post na parte superior da lista de posts.
        });
    })
    .catch(error => {
        console.error("Erro:", error); // Loga o erro no console.
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
