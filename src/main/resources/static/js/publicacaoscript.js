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
    // Faz uma requisição GET para obter todas as publicações
    fetch("/publicacoes")
    .then(response => response.text()) // Converte a resposta em texto
    .then(posts => {
        const postsContainer = document.getElementById("posts"); // Local onde as publicações serão inseridas
        postsContainer.innerHTML = ""; // Limpa as publicações existentes
        postsData = []; // Limpa os dados de publicações antigos

        // Divide o texto das publicações por linhas e remove linhas vazias
        const parsedPosts = posts.split("\n").filter(post => post.trim() !== "");
        
        // Itera sobre as publicações e organiza os dados
        parsedPosts.forEach((post, index) => {
            const [usuario, conteudoComData] = post.split(": ");
            const [conteudo, dataHora] = conteudoComData.split(" (");
            const postTime = new Date(dataHora.replace(")", "")); // Converte a string da data em objeto de data
            const timePassed = calcularTempoPassado(postTime); // Calcula o tempo passado desde a publicação
            const likes = conteudoComData.split(" - ")[1] ? parseInt(conteudoComData.split(" - ")[1].split(" ")[0]) : 0; // Obtém a contagem de likes

            postsData.push({ usuario, conteudo, dataHora, likes, liked: false }); // Armazena dados do post em um array
        });

        // Faz uma nova chamada para obter os likes de cada publicação
        Promise.all(postsData.map((_, index) => 
            fetch(`/publicacoes/${index}/likes`).then(response => response.json())
        )).then(likesLists => {
            // Itera sobre as publicações e atualiza o status de curtido
            likesLists.forEach((likesList, index) => {
                const liked = likesList.includes(usuarioLogado.nome); // Verifica se o usuário logado já curtiu o post
                postsData[index].liked = liked;

                // Cria o elemento HTML para exibir a publicação
                const postElement = document.createElement("div");
                postElement.classList.add("post"); // Adiciona a classe para estilização
                postElement.innerHTML = `
                    <div class="post-header">
                        <span class="username">${postsData[index].usuario}</span> <span class="time">${calcularTempoPassado(new Date(postsData[index].dataHora))}</span>
                    </div>
                    <div class="post-content">
                        <p>${postsData[index].conteudo}</p>
                    </div>
                    <div class="post-actions">
                        <button onclick="toggleLike(${index})">${postsData[index].liked ? "Descurtir" : "Curtir"}</button>
                        <span class="likes">${postsData[index].likes} curtidas</span>
                    </div>
                `;
                postsContainer.prepend(postElement); // Insere o post no início da lista
            });
        });
    })
    .catch(error => {
        console.error("Erro:", error); // Loga erros ao carregar as publicações
    });
}

// Função para curtir ou descurtir uma publicação
function toggleLike(postIndex) {
    const post = postsData[postIndex];

    if (post.liked) {
        // Se já curtiu, realiza a descurtida
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
            console.error("Erro ao descurtir a publicação:", error); // Loga o erro
            alert("Erro ao descurtir a publicação.");
        });
    } else {
        // Se não curtiu, realiza a curtida
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
            console.error("Erro ao curtir a publicação:", error); // Loga o erro
            alert("Erro ao curtir a publicação.");
        });
    }
}

// Função para calcular o tempo passado desde uma publicação
function calcularTempoPassado(postTime) {
    const now = new Date();
    const seconds = Math.floor((now - postTime) / 1000); // Calcula a diferença em segundos

    // Define o intervalo de tempo apropriado para exibição
    let interval = seconds / 31536000;
    if (interval > 1) return Math.floor(interval) + " anos atrás";
    interval = seconds / 2592000;
    if (interval > 1) return Math.floor(interval) + " meses atrás";
    interval = seconds / 86400;
    if (interval > 1) return Math.floor(interval) + " dias atrás";
    interval = seconds / 3600;
    if (interval > 1) return Math.floor(interval) + " horas atrás";
    interval = seconds / 60;
    if (interval > 1) return Math.floor(interval) + " minutos atrás";
    return seconds + " segundos atrás";
}

// Carrega as publicações automaticamente ao carregar a página
document.addEventListener("DOMContentLoaded", loadPosts);
