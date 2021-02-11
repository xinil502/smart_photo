function sus_get_album(res, page) {
    res = JSON.parse(res);
    if(res.status === 0) {
        var de = document.querySelectorAll(".img_and_text");
        for(var k = 0; k < de.length; k++) {
            document.querySelector(".user_img_content").removeChild(de[k]);
        }
        document.querySelector("#all_page").innerHTML = "" + Math.ceil(res.data.sum / 10);
        document.querySelector("#now_page").innerHTML = "" + page;
        for(var i = 0; i < res.data.page_data.length; i++) {
            var img_and_text = document.createElement("div");
            var img_text = document.createElement("div");
            var img_time = document.createElement("div");
            var img = document.createElement("div");
            
            img_and_text.className = "img_and_text";
            img_time.className = "img_time";
            img_text.className = "img_text";
            img.className = "img";
            for(var j = 0; j < res.data.page_data[i].img_url.length; j++) {
                var img_ele = document.createElement("img");
                img_ele.src = res.data.page_data[i].img_url[j];
                img_ele.alt = "出错了呢";
                img_ele.style.width = "100%";
                img.appendChild(img_ele);
            }
            img_text.innerHTML = res.data.page_data[i].text;
            img_time.innerHTML = res.data.page_data[i].time;
            img_and_text.appendChild(img_time);
            img_and_text.appendChild(img_text);
            img_and_text.appendChild(img);
            document.querySelector(".user_img_content").appendChild(img_and_text);
        }
    }
    else {
        tips(res.msg);
    }

}