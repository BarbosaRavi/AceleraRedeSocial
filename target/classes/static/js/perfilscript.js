document.addEventListener("DOMContentLoaded", function () {
    const fotoInput = document.querySelector('.foto-perfil');
    const profilePhoto = document.getElementById('profilePhoto');

    // Ao selecionar um arquivo, atualiza a imagem de perfil
    fotoInput.addEventListener('change', function() {
        if (this.files && this.files[0]) {
            // Verifica se o arquivo é uma imagem
            if (!this.files[0].type.startsWith('image/')) {
                alert('Por favor, selecione um arquivo de imagem.');
                this.value = ''; // Limpa o input
                return;
            }

            const reader = new FileReader();

            reader.onload = function(e) {
                const novaFotoPerfil = e.target.result;
                profilePhoto.src = novaFotoPerfil; // Define a nova imagem como src
            }

            reader.readAsDataURL(this.files[0]); // Converte o arquivo para Data URL
        }
    });
});
