var currentPage = 0; // Variable para almacenar la página actual

function loadProducts() {
    $.ajax({
        url: '/getProducts',
        method: 'GET',
        data: {
            page: currentPage,
            pageSize: 10
        },
        success: function (data) {
            console.log('Received data:', data);
            if (data.totalElements > 0 && data.totalPages > currentPage) {
                console.log('SI - More products available');
                var productsContainer = $('#productsContainer');

                data.content.forEach(function (product) {
                    var productCard = $('<div class="col mb-5">' +
                                        '<div class="card h-100">' +
                                        '<div class="card-body p-4">' +
                                        '<div class="text-center">' +
                                        '<h5 class="fw-bolder">' + product.title + '</h5>' +
                                        product.price + '€' +
                                        '</div>' +
                                        '</div>' +
                                        '<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">' +
                                        '<div class="text-center"><a class="btn btn-outline-dark mt-auto" href="/product/' + product.id + '">Comprar</a></div>' +
                                        '</div>' +
                                        '</div>' +
                                        '</div>');

                    productsContainer.append(productCard);
                });

            } else {
                console.log('NO - No more products or error occurred');
                // Oculta el botón si no hay más páginas
                $('#loadMoreBtn').hide();
            }
        }
    });
}


// Manejar clic en el botón "Cargar más productos"
$('#loadMoreBtn').on('click', function () {
    currentPage++; // Incrementar la página antes de cargar más productos
    loadProducts();
});

// Cargar productos cuando la página se cargue inicialmente
$(document).ready(function () {
    loadProducts();
});