var array = [];
var span = document.getElementsByClassName("close")[0];
window.onload = function (){
    array = JSON.parse(localStorage.getItem('products'));
    document.getElementById("counter-label").innerHTML=array.length;
}
function openModal(){
    document.getElementById("myModal").style.display = "block";
    let prodlist = "";
    for(var item of array){
        let id = Number(item.id);
        prodlist += `
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
    document.getElementById("list-cart").innerHTML=prodlist;
}
function megrendel(idx){
    var item_count = document.getElementById("counter-label").innerText;
    document.getElementById("counter-label").innerHTML=--item_count;
    let storageProducts = JSON.parse(localStorage.getItem('products'));
    let del_products = storageProducts.filter(product => product.index !== idx);
    localStorage.setItem('products', JSON.stringify(del_products));
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
function closeModal() {
    document.getElementById("myModal").style.display = "none";
}
document.addEventListener('click', function (e) {
    if(e.target.className === 'modal'){
        document.getElementById("myModal").style.display = "none";
    }
}, false);