var getTokenAjax = function(act) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {

        if(xhr.readyState == 4) {
            if((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                if(JSON.parse(xhr.responseText).data === true) {
                    act.success(xhr.responseText, xhr.getResponseHeader("token"));
                }
                else {
                    act.success(xhr.responseText, null);
                }
               
            }
            else {
                act.err();
            }
        }
    }
    if(act.type == 'GET') {
        for(each in act.data) {
            act.url += act.url.indexOf('?') == -1 ? "?" : "&";
            act.url = act.url + encodeURIComponent(each) + "=" + encodeURIComponent(act.data[each]);
        }
        xhr.open(act.type, act.url, true);
        xhr.send();
    }
    else if(act.type == 'POST' && act.dataType == "str") {
        xhr.open(act.type, act.url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify(act.data));
    }
    else {
        xhr.open(act.type, act.url, true);
        if(act.token) {
            xhr.setRequestHeader("token", act.token);
        }
        console.log(act.data);
        xhr.send(act.data);
    }
    
}

function clickLogin(fun) {
    var _this = document.querySelector("#login-btn");
    _this.addEventListener("click", function() {
        
        _this.style.pointerEvents =  "none";
        _this.innerHTML = "登陆中...";
        var form = new FormData();
        var user = document.querySelector("#user").value;
        var password = document.querySelector("#pwd").value;
        if(user && password) {
            form.append("user_name", user);
            form.append("password", password);
            getTokenAjax({
                type: "POST",
                data: form,
                url: "/user/login",
                success: function(res, token) {
                    fun(res, token);
                },
                err: function() {
                    tips("请求失败");
                    _this.style.pointerEvents =  "all";
                    _this.innerHTML = "登陆";
                }
            });
        }
        else {
            var text = document.querySelector("#resTxt");
            text.innerHTML = "用户名或密码不能为空";
            text.className = "err";
            _this.style.pointerEvents =  "all";
            _this.innerHTML = "登陆";
        }
    });
}