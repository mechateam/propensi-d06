// Dropdown
$('.sub-menu ul').hide();
$(".sub-menu a").click(function () {
    $(this).parent(".sub-menu").children("ul").slideToggle("100");
    $(this).find(".right").toggleClass("fa-caret-up fa-caret-down");
});

// Searchbar
$("#searchBar").on("input", function () {
    var n = $(this).val();
    $('tbody tr:ccontains("' + n + '")').show(),
        $('tbody tr:not(:ccontains("' + n + '"))').hide(),
        0 === $('tr:ccontains("' + n + '")').length
            ? $("#no_results").show()
            : $("#no_results").hide();
});

jQuery.expr[":"].ccontains = function (a, i, m) {
    return jQuery(a).text().toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
};

