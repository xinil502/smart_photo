function modUserInform(fun) {
    var submit_btn = document.querySelector(".submit");
    submit_btn.addEventListener("click", function() {
        var form = new FormData();
        var mon = document.querySelector("#mon").value;
        var day = document.querySelector("#day").value;
        var str1 = parseInt(mon)<10?"0"+mon:mon;
        var str2 =  parseInt(day)<10?"0"+day:day;
        var file = document.querySelector("#file").files[0];
        form.append("gender", document.querySelector("#set_sex").value);
        form.append("birth", document.querySelector("#year").value + "-" + str1 + "-" + str2);
        form.append("age", document.querySelector("#set-age").value);
        form.append("introduce", document.querySelector("#self_introduce").value);
        form.append("portrait", file);
        ajax({
            type: "POST",
            data: form,
            dataType: "form",
            url: "/user/inform/updateall",
            token: localStorage.getItem("token"),
            success: function(response) {
                if(JSON.parse(response).status === 0) {
                    fun(response);
                }
                else {
                    tips(JSON.parse(response).msg);
                }
            },
            err: function() {
                tips("请求失败")
            }
        });
    });
    
}