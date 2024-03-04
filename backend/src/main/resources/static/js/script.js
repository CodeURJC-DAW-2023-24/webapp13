var thisPage = 0; // Stores actual page
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
            var $htmlData = $(htmlData); // htmlData to jQuery

            if ($htmlData.length > 0) {
                $('#productsContainer').append($htmlData);
                $('#loadMoreBtn').show();

                if ($htmlData.length < 15) {
                    $('#loadMoreBtn').hide();
                }
            }
        },
        error: function () {
            console.log('Error occurred while loading products');
        }
    });
}


function loadProductsByCategory(categoryId) {
    currentPage = 0; // Reset page if category changes
    $.ajax({
        url: '/product/category/' + categoryId,
        method: 'GET',
        data: {
            page: currentPage,
            pageSize: 8
        },
        success: function (htmlData) {
            console.log('Received HTML data:', htmlData);
            var $htmlData = $(htmlData);
            if (htmlData.trim() !== '' ) {
                $('#productsContainer').html(htmlData);

                if ($htmlData.length < 15) {
                    $('#loadMoreBtn').hide();
                }
            } else {
                $('#loadMoreBtn').prop('disabled', true).text('No more products');
            }
        },
        error: function () {
            console.log('Error occurred while loading products by category');
        }
    });
}


$('form').on('submit', function (e) {
    e.preventDefault();

    var query = $('input[name="query"]').val(); // Gets search value

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

            } else if (query == ""){
                loadProducts();
            }
            else {
                $('#loadMoreBtn').hide();
            }
        },
        error: function () {
            console.log('Error during search');
        }
    })
}

$('#loadMoreBtn').on('click', function () {
    currentPage++;
    loadProducts();
});

$('#buttonSearch').on('click', function () {
    currentPage = 0; // Reset page if there is a new search
    var query = $('#searchInput').val();
    searchProducts(query);
});

$('#searchInput').on('keydown', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        currentPage = 0;
        var query = $('#searchInput').val();
        searchProducts(query);
    }
});

$('.category-link').on('click', function (event) {
    event.preventDefault();
    currentPage = 0;
    var categoryId = $(this).data('category-id');
    loadProductsByCategory(categoryId);
});

$(document).ready(function () {
    loadProducts();

});
