$( document ).ready(function() {
    var text = JSON.parse(localStorage.getItem("searchString"));
    document.getElementById("myInput").value=text;

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
});
function deleteString(){
    localStorage.removeItem("searchString");
}