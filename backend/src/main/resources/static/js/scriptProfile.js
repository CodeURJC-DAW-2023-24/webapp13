/*
document.addEventListener("DOMContentLoaded", function () {
    openTab('Products'); // Cambia la pestaña por defecto al cargar la página

    // Define el ID del usuario actual (ajusta esto según tu aplicación)
    var userID = 1; // Reemplaza con el ID correcto del usuario actual

    // Obtén los datos del controlador
    fetch('/user/' + userID + '/benefits')
        .then(response => response.json())
        .then(data => {
            // Dibuja el gráfico con Chart.js
            var ctx = document.getElementById('myChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Ingresos', 'Gastos'],
                    datasets: [{
                        label: 'Amount',
                        data: [data.ingresos, data.gastos],
                        backgroundColor: [
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(255, 99, 132, 0.2)'
                        ],
                        borderColor: [
                            'rgba(75, 192, 192, 1)',
                            'rgba(255, 99, 132, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        });
});
*/

function openTab(tabName) {
    var i;
    var x = document.getElementsByClassName("tab");
    for (i = 0; i < x.length; i++) {
      x[i].style.display = "none";  
    }
    document.getElementById(tabName).style.display = "block";  
}