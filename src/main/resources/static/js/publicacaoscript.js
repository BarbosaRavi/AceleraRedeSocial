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

            postsData.push({ usuario, conteudo, tempoPassado, likes: likesCount, liked: false }); // Armazena dados do post
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
            `;
            postsContainer.prepend(postElement); // Insere o post no início da lista
        });
    })
    .catch(error => {
        console.error("Erro:", error);
    });
}

// Função para curtir ou descurtir uma publicação
function toggleLike(postIndex) {
    const post = postsData[postIndex];

    if (post.liked) {
        fetch(`/publicacoes/${postIndex}/descurtir`, {
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
        fetch(`/publicacoes/${postIndex}/curtir`, {
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
