function clickPublic(fun) {
    var public = document.querySelector("#public");
    public.addEventListener("click", function() {
        var file = document.querySelector("#file");
        var upload = file.files;
        var size_flag = -1;
        for(var i = 0; i < upload.length; i++) {
            if(upload.size > 50000) {
                size_flag = i;
                break;
            }
        }
        if(size_flag === -1) { //图片大小限制字节数
            var form = new FormData();
            form.append('img', upload[0]);
            form.append("text", document.querySelector("#img_text").value);
            var date = new Date();
            var year = date.getFullYear();
            var mon = date.getMonth() + 1;
            var day = date.getDate();
            var hour = date.getHours();
            var min = date.getMinutes();
            var sec = date.getSeconds();
            var str1 = mon >= 10?mon:"0"+mon;
            var str2 = day >= 10?day:"0"+day;
            var str3 = hour >= 10?hour:"0"+hour;
            var str4 = min >= 10?min:"0"+min;
            var str5 = sec >= 10?sec:"0"+sec;

            form.append("time", "" + year + "-" + str1 + "-" + str2+ " " + str3 + ":" + str4 + ":" + str5);
            ajax({
                type: "POST",
                data: form,
                dataType: "form",
                url: "/img/upload",
                token: localStorage.getItem("token"),
                success: function(res) {
                    document.querySelector("#img_text").value = "";
                    document.querySelector("#fileName").value = "";
                    document.querySelector("#file").value = "";
                    fun(res);
                },
                err: function() {
                    tips("请求错误");
                }
            });
        } else {
            //提示用户图片大小过大
            tips("某张图片过大");
        }
        
    });
}