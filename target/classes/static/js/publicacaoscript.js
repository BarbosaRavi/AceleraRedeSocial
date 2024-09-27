let usuarioLogado = {
    nome: "Usuário1" // Simulando um usuário logado; deve ser obtido após o login real
};

let postsData = []; // Para armazenar as publicações e suas curtidas

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
        postsData = []; // Limpa dados de publicações antigas

        const parsedPosts = posts.split("\n").filter(post => post.trim() !== "");
        parsedPosts.forEach((post, index) => {
            const [usuario, conteudoComData] = post.split(": ");
            const [conteudo, dataHora] = conteudoComData.split(" (");

            // Calculando o tempo passado
            const postTime = new Date(dataHora.replace(")", ""));
            const timePassed = calcularTempoPassado(postTime);

            const likes = conteudoComData.split(" - ")[1] ? parseInt(conteudoComData.split(" - ")[1].split(" ")[0]) : 0;

            postsData.push({ usuario, conteudo, dataHora, likes, liked: false }); // Armazenar dados do post

            // Verifica se o usuário já curtiu
            fetch(`/publicacoes/${index}/likes`)
                .then(response => response.json())
                .then(likesList => {
                    const liked = likesList.includes(usuarioLogado.nome); // Verifica se o usuário já curtiu
                    postsData[index].liked = liked; // Atualiza a propriedade liked

                    const postElement = document.createElement("div");
                    postElement.classList.add("post"); // Adiciona a classe para estilização
                    postElement.innerHTML = `
                        <div class="post-header">
                            <span class="username">${usuario}</span> <span class="time">${timePassed}</span>
                        </div>
                        <div class="post-content">
                            <p>${conteudo}</p>
                        </div>
                        <div class="post-actions">
                            <button onclick="toggleLike(${index})">${liked ? "Descurtir" : "Curtir"}</button>
                            <span class="likes">${likes} curtidas</span>
                        </div>
                    `;
                    postsContainer.prepend(postElement);
                });
        });
    })
    .catch(error => {
        console.error("Erro:", error);
    });
}

function toggleLike(postIndex) {
    const post = postsData[postIndex];

    if (post.liked) {
        // Descurtir
        fetch(`/publicacoes/${postIndex}/descurtir`, {
            method: "POST"
        })
        .then(response => {
            if (response.ok) {
                post.likes--; // Decrementa as curtidas
                post.liked = false; // Marca como não curtido
            } else {
                alert("Erro ao descurtir a publicação.");
            }
            loadPosts(); // Recarrega as publicações
        })
        .catch(error => {
            console.error("Erro ao descurtir a publicação:", error);
            alert("Erro ao descurtir a publicação.");
        });
    } else {
        // Curtir
        fetch(`/publicacoes/${postIndex}/curtir`, {
            method: "POST"
        })
        .then(response => {
            if (response.ok) {
                post.likes++; // Incrementa as curtidas
                post.liked = true; // Marca como curtido
            } else {
                alert("Erro ao curtir a publicação.");
            }
            loadPosts(); // Recarrega as publicações
        })
        .catch(error => {
            console.error("Erro ao curtir a publicação:", error);
            alert("Erro ao curtir a publicação.");
        });
    }
}

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
