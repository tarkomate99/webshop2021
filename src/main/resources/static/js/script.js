let array = [];
let index=-1;
var price;
$(document).ready(function (){
    $('.btnSelect').on('click', function (){
        var currentRow = $(this).closest("tr");
        var col1 = currentRow.find("td:eq(0)").text();
        var col2 = currentRow.find("td:eq(3)").text();
        var col3 = currentRow.find("td:eq(4)").text();
        var col4 = currentRow.find("td:eq(5)").text();
        var col5 = currentRow.find("td:eq(6)").text();
        var col6 = currentRow.find("td:eq(7)").text();
        index++;
        array.push({"id":col1, "prod_name": col2, "price": col3, "rating" : col5, "category": col6, "index":index});
    });
});
function item_ad() {
    var item_count = document.getElementById("counter-label").innerText;
    document.getElementById("counter-label").innerHTML=++item_count;
}
// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
function openModal() {
    document.getElementById("myModal").style.display = "block";
    localStorage.setItem('products', JSON.stringify(array));
    let list_element="";
    for (var item of array){
        console.log(typeof item.id)
        console.log(item.id)
        let id = Number(item.id);
        list_element+= `
                        <tr scope="row">
                            <td>${item.prod_name}</td>
                            <td id="price">${item.price}</td>
                            <td>${item.rating}</td>
                            <td>${item.category}</td>
                            <td><a href="/orders/new/${id}" target="_blank" onclick="megrendel(${item.index})" class="btn btn-success">Megrendelés</a></td>
                            <td><a href="#" onclick="refreshTable(${item.index})" class="btn btn-danger">Törlés</a></td>
                        </tr>
            `;
    }
    document.getElementById("list-cart").innerHTML=list_element;
}

// When the user clicks on <span> (x), close the modal
function closeModal() {
    document.getElementById("myModal").style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
document.addEventListener('click', function (e) {
    if(e.target.className === 'modal'){
        document.getElementById("myModal").style.display = "none";
    }
}, false);
function megrendel(idx){
    var item_count = document.getElementById("counter-label").innerText;
    document.getElementById("counter-label").innerHTML=--item_count;
    let storageProducts = JSON.parse(localStorage.getItem('products'));
    let del_products = storageProducts.filter(product => product.index !== idx);
    price=document.querySelectorAll("#price");
    localStorage.setItem('products', JSON.stringify(del_products));
    for(var i=0;i<price.length;i++){
        var prod_price=price[i].innerHTML.slice(0,price[i].innerHTML.indexOf("Ft"));
        val_price = prod_price;
        price[i].innerHTML=val_price+" Ft";
    }
    localStorage.setItem("prod_price", JSON.stringify(Math.round(val_price)));
    if (idx > -1) {
        array.splice(idx, 1);
    }
    if (document.getElementById("counter-label").innerText==-1){
        array=[];
        location.reload();
        location.reload();
    }
    document.getElementById("myModal").style.display = "none";
}
function refreshTable(idx){
    var item_count = document.getElementById("counter-label").innerText;
    let storageProducts = JSON.parse(localStorage.getItem('products'));
    let del_products = storageProducts.filter(product => product.index !== idx);
    localStorage.setItem('products', JSON.stringify(del_products));
    if (idx > -1) {
        array.splice(idx, 1);
    }
    if (item_count==1){
        array=[];
        document.getElementById("myModal").style.display = "none";
        location.reload();
    }
    let list_element="";
    for (var item of array){
        console.log(typeof item.id)
        console.log(item.id)
        let id = Number(item.id);
        list_element+= `
                        <tr scope="row">
                            <td>${item.prod_name}</td>
                            <td>${item.price}</td>
                            <td>${item.rating}</td>
                            <td>${item.category}</td>
                            <td><a href="/orders/new/${id}" target="_blank" onclick="megrendel(${item.index})" class="btn btn-success">Megrendelés</a></td>
                            <td><a href="#" onclick="refreshTable(${item.index})" class="btn btn-danger">Törlés</a></td>
                        </tr>
            `;
    }
    document.getElementById("list-cart").innerHTML=list_element;
    document.getElementById("counter-label").innerHTML=--item_count;

}