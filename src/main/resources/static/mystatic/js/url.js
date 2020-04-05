function getOriginURL() {
    var url = '';
    try {
        url = window.top.document.referrer;
    } catch (e) {
        if (window.parent) {
            try {
                url = window.parent.document.referrer;
            } catch (m) {
                url = "";
            }
        }
    }
    if (url === "")
        url = document.referrer;

    return url
}


function getTopURL() {
    var url = '';
    try {
        url = window.top.location;
    } catch (e) {
        if (window.parent) {
            try {
                url = window.parent.location;
            } catch (m) {
                url = "";
            }
        }
    }
    if (url === "")
        url = document.location;
    return url;
}


function getQueryVariable(variable, url) {
    var query = url.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }

    return '';
}



