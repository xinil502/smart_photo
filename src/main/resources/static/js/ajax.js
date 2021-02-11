// {
//     type,
//     data,
//     dataType,
//     url,
//     sus(),
//     err(),
//     token
// }
var ajax = function(act) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {

        if(xhr.readyState == 4) {
            if((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                act.success(xhr.responseText);
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