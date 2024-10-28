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
});
