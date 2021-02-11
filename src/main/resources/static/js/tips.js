function tips(msg) {
    document.querySelector("#tips").innerHTML = msg;
    document.querySelector(".tips").style.display = "block";
}

function clickOk(fun) {
    document.querySelector("#ok").addEventListener("click", function() {
        document.querySelector(".tips").style.display = "none";
        fun();
    });
}