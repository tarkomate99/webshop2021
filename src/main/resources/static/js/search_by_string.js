function addSeachString(){
    //console.log(document.getElementById("myInput").value);
    localStorage.setItem("searchString", JSON.stringify(document.getElementById("myInput").value));
}
