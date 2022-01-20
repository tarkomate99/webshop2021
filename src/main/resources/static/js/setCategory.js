$( document ).ready(function() {
    tr = table.getElementsByTagName("tr");
    input = document.getElementById("kate");
    for (i = 1; i < tr.length; i++) {
        if (tr[i].getElementsByTagName("td")[7].innerText !== input.value) {
            tr[i].style.display = "none";
        } else {
            tr[i].style.display = "";
        }
        if (selecter.value==="Minden"){
            tr[i].style.display="";
        }
    }
});