document.addEventListener("DOMContentLoaded", function () {
    const fotoInput = document.getElementById('fotoPerfilInput');
    const profilePhoto = document.getElementById('profilePhoto');
    const bioInput = document.getElementById('bioInput');
    const botaoAdicionarAmigo = document.querySelector('.add-friend-button');

    // Verifica se há uma imagem de perfil específica do usuário armazenada no localStorage
    const usuarioId = document.querySelector('input[name="amigoId"]').value;
    const fotoPerfilArmazenada = localStorage.getItem(`fotoPerfil_${usuarioId}`);
    if (fotoPerfilArmazenada) {
        profilePhoto.src = fotoPerfilArmazenada;
    }

    // Ao selecionar um arquivo, atualiza a imagem de perfil e armazena no localStorage
    fotoInput.addEventListener('change', function() {
        if (this.files && this.files[0]) {
            const reader = new FileReader();

            reader.onload = function(e) {
                const novaFotoPerfil = e.target.result;
                profilePhoto.src = novaFotoPerfil;
               localStorage.setItem(`fotoPerfil_${usuarioId}`, novaFotoPerfil);

            };

            reader.readAsDataURL(this.files[0]);
        }
    });

    // Atualiza a bio automaticamente ao ser editada
    bioInput.addEventListener('input', function() {
        const novaBio = bioInput.value;
        
        // Envia a bio para o servidor
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/perfil/salvarBio", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send("bio=" + encodeURIComponent(novaBio));
    });

    // Função para adicionar amigo com feedback visual
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
            if (response.ok) {
                alert('Solicitação de amizade enviada com sucesso!');
                botaoAdicionarAmigo.style.backgroundColor = 'green';
                botaoAdicionarAmigo.textContent = 'Amigo Adicionado';
            } else {
                alert('Erro ao enviar a solicitação de amizade.');
                botaoAdicionarAmigo.style.backgroundColor = '';
                botaoAdicionarAmigo.textContent = 'Adicionar Amigo';
            }
        })
        .catch(error => {
            console.error('Erro ao enviar a solicitação:', error);
            alert('Erro ao enviar a solicitação de amizade.');
            botaoAdicionarAmigo.style.backgroundColor = '';
            botaoAdicionarAmigo.textContent = 'Adicionar Amigo';
        });
    }

    botaoAdicionarAmigo.addEventListener('click', adicionarAmigo);
});