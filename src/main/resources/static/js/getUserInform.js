function getUserInform(fun) {
    ajax({
        type: "POST",
        data: null,
        dataType: "form",
        url: "/user/inform/getall",
        token: localStorage.getItem("token"),
        success: function(response) {
            fun(response);
        },
        err: function() {
            tips("请求失败");
        }
    });
}