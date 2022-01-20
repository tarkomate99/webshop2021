$(document).ready(function (){
    $(document).on('click', '.showProd', function (){
        var image = $(this).closest('tr').find('#prod_img').text();
        var name = $(this).closest('tr').find('#prod_name').text();
        var description = $(this).closest('tr').find('#prod_desc').text();
        console.log(description)
        $('#modaldata tbody #adatok').html("");
        // $('#modaldata tbody #kep').html(`<td>${image}</td>`);
        $('#modaldata tbody').html(`<tr><td colspan="2" style="text-align: center"><h3>${name}</h3></td></tr>`+`<tr><td><img src="${image}" alt="image" style="max-height: 400px"></td><td>${description}</td></tr>`);
        document.getElementById("prod_modal").style.display = "block";
    });
});
function closeProdModal(){
    document.getElementById("prod_modal").style.display = "";
}
document.addEventListener('click', function (e) {
    if(e.target.className === 'modal'){
        document.getElementById("prod_modal").style.display = "none";
    }
}, false);

