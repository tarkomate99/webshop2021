let table;
let input;
let filter;
let tr;
let td;
let i;
let txtValue;
let selecter;
let categoria;
let kate;
$(document).ready(function () {
    table = document.getElementById("myTable");
    selecter = document.getElementById("kategoria");
    kate= document.getElementById("kate");
    onSearch();
    categoria=location.href.split('/')[4];
    if(categoria==='videokartya'){
        selecter.value='Videókártya';
        kate.value='Videókártya';

    }else if(categoria==='monitor'){
        selecter.value='Monitor';
        kate.value='Monitor';

    }else if(categoria==='processzor'){
        selecter.value='Processzor';
        kate.value='Processzor';

    }else{
        selecter.value='Minden';
        kate.value='Minden';

    }
})
window.onload = function (){
    console.log(JSON.parse(localStorage.getItem('searchString')))
    var text = JSON.parse(localStorage.getItem('searchString'));
    document.getElementById("myInput").value=text;
}

function onSearch() {
    tr = table.getElementsByTagName("tr");
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    for (i = 1; i < tr.length; i++) {
        var name = tr[i].getElementsByTagName("td")[1].innerText;
        var td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            txtValue = td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }

    }
}

function searchByCategory() {
    tr = table.getElementsByTagName("tr");
    for (i = 1; i < tr.length; i++) {
        if (tr[i].getElementsByTagName("td")[7].innerText !== selecter.value) {
            tr[i].style.display = "none";
        } else {
            tr[i].style.display = "";
        }
        if (selecter.value==="Minden"){
            tr[i].style.display="";
        }
    }
}


