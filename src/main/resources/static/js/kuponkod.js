var message;
var price;
var val_price;
var btn;
function activateKupon(){
    kupon_input=document.getElementById("kupon").value;
    message=document.getElementById("message");
    price=document.querySelectorAll("#price");
    btn=document.getElementById("kpn");
    if (kupon_input.toUpperCase()==="WEBSHOP2021"){
        message.innerText="Sikeres kupon beváltás!";
        message.className="badge badge-success";
        for(var i=0;i<price.length;i++){
            // price[i].innerHTML=price[i].innerHTML*0.2;
            var prod_price = price[i].innerHTML.slice(0,price[i].innerHTML.indexOf("Ft"));
            val_price = prod_price*0.9;
            price[i].innerHTML=val_price+" Ft";
        }
    }else{
        message.innerText="Hibás kupon kód!";
        message.className="badge badge-danger";
        for(var i=0;i<price.length;i++){
            var prod_price=price[i].innerHTML.slice(0,price[i].innerHTML.indexOf("Ft"));
            val_price = prod_price;
            price[i].innerHTML=val_price+" Ft";
        }
    }
    localStorage.setItem("prod_price", JSON.stringify(Math.round(val_price)));
    btn.disabled=true;
}
window.onload = function (){
    var prod_price = JSON.parse(localStorage.getItem("prod_price"));
    if(prod_price!=null){
        document.getElementById("price_prod").value=prod_price;
    }
}
function removePrice(){
    localStorage.removeItem("prod_price");
}
