var welcome_span;
var user_name;
window.onload = function (){
    welcome_span=document.getElementById("welcome");
    //console.log(sessionStorage.getItem("user_email"));
    if(sessionStorage.getItem("user_email")!==null){
        welcome_span.innerText="Üdvözöllek az oldalon "+sessionStorage.getItem("user_email")+" !";
    }else{
        welcome_span.style.display="none";
    }
}
function setWelcome(){
    user_name=document.getElementById("username").value;
    sessionStorage.setItem("user_email", user_name);
}

function deleteWelcome(){
    sessionStorage.removeItem("user_email");

}
