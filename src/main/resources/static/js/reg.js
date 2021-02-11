function clickReg(fun) {
    var reg = document.querySelector("#reg-btn");
    reg.addEventListener("click", function() {
        
        
        var text = document.querySelector("#resTxt");
        var user = document.querySelector("#user").value;
        var passwordone = document.querySelector("#passwordone").value;
        var passwordtwo = document.querySelector("#passwordtwo").value;
        if(!(passwordone === passwordtwo)) {
            
            text.innerHTML = "两次密码不相同";
            text.className = "err";
        }
        else if(passwordone.length < 6 || passwordone.length > 16) {
            text.innerHTML = "密码长度不能小于6或大于16";
            text.className = "err";
        }
        else if(user.length < 6 || user.length > 16) {
            text.innerHTML = "用户名长度不能小于6或大于16";
            text.className = "err";
        }
        else {
            reg.innerHTML = "注册中...";
            reg.style.pointerEvents =  "none";
            var form = new FormData();
            form.append("user_name", user);
            form.append("password", passwordone);
            ajax({
                type: "POST",
                data: form,
                dataType: "form",
                url: "/user/reg",
                token: localStorage.getItem("token"),
                success: function(res) {
                    fun(res);
                },
                err: function() {
                    reg.innerHTML = "注册";
                    tips("请求错误");
                    reg.style.pointerEvents =  "all";
                }
            });
        }
    });
}