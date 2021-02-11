function clickModPass(fun) {
    var mod = document.querySelector("#mod-btn");
    mod.addEventListener("click", function() {
        var text = document.querySelector("#resTxt");
        var passwordone = document.querySelector("#passwordone").value;
        var passwordtwo = document.querySelector("#passwordtwo").value;
        if(!(passwordone === passwordtwo)) {
            text.innerHTML = "两次密码不相同";
            text.className = "err";
        }
        else if(passwordone.length < 6) {
            text.innerHTML = "密码长度不能小于6";
            text.className = "err";
        }
        else {
            mod.style.pointerEvents = "none";
            mod.value = "修改中...";
            var form = new FormData();
            form.append("pwd", passwordone);
            ajax({
                type: "POST",
                data: form,
                dataType: "form",
                url: "/user/inform/updatepwd",
                token: localStorage.getItem("token"),
                success: function(res) {
                    fun(res);
                },
                err: function() {
                    tips("请求错误");
                    mod.style.pointerEvents = "all";
                    mod.value = "确认修改";
                }
            });
        }
    });
}