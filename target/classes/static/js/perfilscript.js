document.addEventListener("DOMContentLoaded", function () {
    const fotoInput = document.querySelector('.foto-perfil');
    const profilePhoto = document.getElementById('profilePhoto');

    // Ao selecionar um arquivo, atualiza a imagem de perfil
    fotoInput.addEventListener('change', function() {
        if (this.files && this.files[0]) {
            const reader = new FileReader();

            reader.onload = function(e) {
                const novaFotoPerfil = e.target.result;
                profilePhoto.src = novaFotoPerfil; // Define a nova imagem como src
            }

            reader.readAsDataURL(this.files[0]); // Converte o arquivo para Data URL
        }
    });
});
