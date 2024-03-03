var thisPage = 0; // Variable para almacenar la página actual
var currentPage = 0;
function loadProducts() {
    $.ajax({
        url: '/getProducts',
        method: 'GET',
        data: {
            page: currentPage,
            pageSize: 8
        },
        success: function (htmlData) {
            var $htmlData = $(htmlData); // Convertimos htmlData en un objeto jQuery

            if ($htmlData.length > 0) {
                $('#productsContainer').append($htmlData);
                $('#loadMoreBtn').show(); // Mostrar el botón "Cargar más productos"

                if ($htmlData.length < 15) {
                    $('#loadMoreBtn').hide();
                    firstLoad = false;
                }
            } else {
                // Si no hay más productos, ocultar el botón "Cargar más productos"
            }
        },
        error: function () {
            console.log('Error occurred while loading products');
        }
    });
}






function loadProductsByCategory(categoryId) {
    currentPage = 0; // Reiniciar la página cuando se cambia de categoría
    $.ajax({
        url: '/product/category/' + categoryId,
        method: 'GET',
        data: {
            page: currentPage,
            pageSize: 8
        },
        success: function (htmlData) {
            console.log('Received HTML data:', htmlData);
            if (htmlData.trim() !== '' ) {
                $('#productsContainer').html(htmlData);
            } else {
                // Si no hay más productos, deshabilitar el botón y mostrar un mensaje
                $('#loadMoreBtn').prop('disabled', true).text('No hay más productos');
            }
        },
        error: function () {
            console.log('Error occurred while loading products by category');
        }
    });
}


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
            pageSize: 8
        },
        success: function (data) {
            // Limpiar el contenedor de productos antes de agregar los nuevos resultados
            $('#productsContainer').empty();
            $('#loadMoreBtn').hide();

            if (data.totalElements > 0 && query != "") {
                var productsContainer = $('#productsContainer');

                data.content.forEach(function (product) {
                    var productCard = $('<div class="col mb-5">' +
                        '<div class="card h-100">' +
                        // Product image
                        (product.image ? '<img class="card-img-top" src="/product/' + product.id + '/image" alt="La imagen no carga" />' : '') +
                        // Product details
                        '<div class="card-body p-4">' +
                        '<div class="text-center">' +
                        // Product name
                        '<h5 class="fw-bolder">' + product.title + '</h5>' +
                        // Product price
                        product.price + '€' +
                        '</div>' +
                        '</div>' +
                        // Product actions
                        '<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">' +
                        '<div class="text-center"><a class="btn btn-outline-dark mt-auto" href="/product/' + product.id + '">Comprar</a></div>' +
                        '</div>' +
                        '</div>' +
                        '</div>');

                    productsContainer.append(productCard);
                });

            } else {
                $('#loadMoreBtn').hide();
            }
        },
        error: function () {
            console.log('Error during search');
        }
    })
}// Manejar clic en el botón "Cargar más productos"
$('#loadMoreBtn').on('click', function () {
    currentPage++; // Incrementar la página antes de cargar más productos
    loadProducts();
});

// Manejar clic en el botón "Search"
$('#buttonSearch').on('click', function () {
    currentPage = 0; // Reiniciar la página cuando se realiza una nueva búsqueda
    var query = $('#searchInput').val(); // Obtener el valor del campo de búsqueda
    searchProducts(query);
});

// Manejar la búsqueda cuando se presiona la tecla "Enter" en el campo de "Search"
$('#searchInput').on('keydown', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // Evitar el comportamiento de formulario predeterminado
        currentPage = 0; // Reiniciar la página cuando se realiza una nueva búsqueda
        var query = $('#searchInput').val(); // Obtener el valor del campo de búsqueda
        searchProducts(query); // Realizar la búsqueda de productos
    }
});

// Manejar el clic en un enlace de categoría
$('.category-link').on('click', function (event) {
    event.preventDefault(); // Evitar el comportamiento de enlace predeterminado
    currentPage = 0; // Reiniciar la página cuando se cambia de categoría
    var categoryId = $(this).data('category-id'); // Obtener el ID de la categoría del atributo de datos del enlace
    loadProductsByCategory(categoryId); // Cargar productos por categoría
});

// Cargar productos cuando la página se cargue inicialmente
$(document).ready(function () {
    loadProducts();
});
