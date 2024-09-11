document.addEventListener('DOMContentLoaded', function() {
    const input = document.querySelector('#nascimento');

    input.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');  // Remove tudo que não for número

        if (value.length >= 2) {
            value = value.slice(0, 2) + '/' + value.slice(2);  // Adiciona a primeira barra depois do dia
        }

        if (value.length >= 5) {
            value = value.slice(0, 5) + '/' + value.slice(5, 9);  // Adiciona a segunda barra depois do mês
        }

        e.target.value = value;  // Atualiza o valor do campo de entrada
    });
});
