document.addEventListener("DOMContentLoaded", function () {
    const fotoInput = document.querySelector('.foto-perfil');
    const profilePhoto = document.getElementById('profilePhoto');
    
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
});
