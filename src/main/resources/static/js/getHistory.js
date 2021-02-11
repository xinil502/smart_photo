function getHistory(page, fun) {
    var form = new FormData();
    form.append("page", page);
    form.append("pagenum", 10);
    ajax({
        type: "POST",
        data: form,
        dataType: "form",
        url: "/img/user",
        token: localStorage.getItem("token"),
        success: function(res) {
            fun(res, page);
        },
        err: function() {
            tips("请求失败");
        }
    });
}