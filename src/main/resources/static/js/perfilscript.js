document.addEventListener("DOMContentLoaded", function () {
    const fotoInput = document.querySelector('.foto-perfil');
    const profilePhoto = document.getElementById('profilePhoto');
    const bioInput = document.getElementById('bioInput');

    // Verifica se há uma imagem de perfil armazenada no localStorage
    const fotoPerfilArmazenada = localStorage.getItem('fotoPerfil');
    if (fotoPerfilArmazenada) {
        profilePhoto.src = fotoPerfilArmazenada; // Define a imagem armazenada como src
    }

    // Ao selecionar um arquivo, atualiza a imagem de perfil
    fotoInput.addEventListener('change', function() {
        if (this.files && this.files[0]) {
            const reader = new FileReader();

            reader.onload = function(e) {
                const novaFotoPerfil = e.target.result;
                profilePhoto.src = novaFotoPerfil; // Define a nova imagem como src
                localStorage.setItem('fotoPerfil', novaFotoPerfil); // Armazena a nova imagem no localStorage
            }

            reader.readAsDataURL(this.files[0]); // Converte o arquivo para Data URL
        }
    });

    // Atualiza a bio automaticamente ao ser editada
    bioInput.addEventListener('input', function() {
        const novaBio = bioInput.value;

        // Envia a bio para o servidor via XMLHttpRequest
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/perfil/salvarBio", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send("bio=" + encodeURIComponent(novaBio)); // Envia a bio como texto simples
    });
	
    function adicionarAmigo() {
        const form = document.getElementById("formAdicionarAmigo");
        const formData = new FormData(form);
        
        fetch(form.action, {
            method: 'POST',
            body: formData,
            headers: {
                'Accept': 'application/json',
            },
        })
        .then(response => {
            const botaoAdicionarAmigo = document.querySelector('.add-friend-button');
            if (response.ok) {
                alert('Solicitação de amizade enviada com sucesso!');
                // Exibe feedback visual
                botaoAdicionarAmigo.style.backgroundColor = 'green'; // Muda a cor do botão para verde
                botaoAdicionarAmigo.textContent = 'Amigo Adicionado'; // Muda o texto do botão
            } else {
                alert('Erro ao enviar a solicitação de amizade.');
                // Reverte para o estado original em caso de erro
                botaoAdicionarAmigo.style.backgroundColor = ''; // Reseta a cor do botão
                botaoAdicionarAmigo.textContent = 'Adicionar Amigo'; // Restaura o texto original
            }
        })
        .catch(error => {
            console.error('Erro ao enviar a solicitação:', error);
            alert('Erro ao enviar a solicitação de amizade.');
            // Reverte para o estado original em caso de erro
            const botaoAdicionarAmigo = document.querySelector('.add-friend-button');
            botaoAdicionarAmigo.style.backgroundColor = ''; // Reseta a cor do botão
            botaoAdicionarAmigo.textContent = 'Adicionar Amigo'; // Restaura o texto original
        });
    }

    // Atribui a função de adicionar amigo ao botão
    const botaoAdicionarAmigo = document.querySelector('.add-friend-button');
    botaoAdicionarAmigo.addEventListener('click', adicionarAmigo);
});
