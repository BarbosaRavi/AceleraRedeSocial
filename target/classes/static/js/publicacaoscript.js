let usuarioLogado = {
    nome: "Usuário1" // Simulando um usuário logado; deve ser obtido após o login real
};

function addPost() {
    const postContent = document.getElementById("newPostContent").value;

    if (postContent.trim() === "") {
        alert("Você deve inserir um conteúdo para publicar!");
        return;
    }

    fetch("/publicacoes", {
        method: "POST",
        headers: {
            'Content-Type': 'text/plain', // Enviando como texto simples
        },
        body: postContent
    })
    .then(response => {
        if (response.ok) {
            document.getElementById("newPostContent").value = ""; // Limpa o textarea
            loadPosts(); // Recarrega as publicações
        } else {
            alert("Erro ao publicar a postagem. Código: " + response.status);
        }
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao conectar ao servidor.");
    });
}

function loadPosts() {
    fetch("/publicacoes")
    .then(response => response.text())
    .then(posts => {
        const postsContainer = document.getElementById("posts");
        postsContainer.innerHTML = ""; // Limpa as publicações existentes

        // Dividindo as publicações por nova linha
        const parsedPosts = posts.split("\n").filter(post => post.trim() !== ""); // Filtrando linhas vazias
        parsedPosts.forEach(post => {
            const [usuario, conteudoComData] = post.split(": ");
            const [conteudo, dataHora] = conteudoComData.split(" (");
            
            // Calculando o tempo passado
            const postTime = new Date(dataHora.replace(")", ""));
            const timePassed = calcularTempoPassado(postTime);

            const postElement = document.createElement("div");
            postElement.className = "post";
            postElement.innerHTML = `
                <div class="post-header">
                    <span class="username">${usuario}</span> <span class="time">${timePassed}</span>
                </div>
                <div class="post-content">
                    <p>${conteudo}</p>
                </div>
                <div class="post-actions">
                    <button onclick="likePost(this)">Curtir</button> <span class="likes">0 curtidas</span>
                </div>
            `;

            // Inserindo a nova postagem no topo
            postsContainer.prepend(postElement);
        });
    })
    .catch(error => {
        console.error("Erro:", error);
    });
}


// Função para calcular o tempo passado
function calcularTempoPassado(postTime) {
    const now = new Date();
    const seconds = Math.floor((now - postTime) / 1000);

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

// Carrega publicações ao iniciar a página
document.addEventListener("DOMContentLoaded", loadPosts);
