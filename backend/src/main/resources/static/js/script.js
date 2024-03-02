var currentPage = 0;
var thisPage = 0; // Variable para almacenar la página actual

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
                                        '<img class="card-img-top img-fluid" src="data:image/png;base64,' + product.imageFileBase64 + '" alt="Product Image" />' +
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

$('form').on('submit', function (e) {
    e.preventDefault(); // Evitar que el formulario se envíe de forma predeterminada

    var query = $('input[name="query"]').val(); // Obtener el valor del campo de búsqueda

    // Realizar la búsqueda
    searchProducts(query);
});

function searchProducts(query) {
    $.ajax({
        url: '/search',
        method: 'GET',
        data: {
            query: query,
            page: thisPage,
            pageSize: 10
        },
        success: function (data) {
            console.log(data);
            // Limpiar el contenedor de productos antes de agregar los nuevos resultados
            $('#productsContainer').empty();
            $('#loadMoreBtn').hide();
            console.log(query);

            if (data.totalElements > 0 && query != "") {
                console.log('SI - More products available');
                var productsContainer = $('#productsContainer');

                data.content.forEach(function (product) {
                    var productCard = $('<div class="col mb-5">' +
                                        '<div class="card h-100">' +
                                        '<div class="card-body p-4">' +
                                        '<div class="text-center">' +
                                        '<img class="card-img-top img-fluid" src="data:image/png;base64,' + product.imageFileBase64 + '" alt="Product Image" />' +
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
                loadProducts();
                $('#loadMoreBtn').show();
            }
        },
        error: function () {
            console.log('Error during search');
        }
    })
}