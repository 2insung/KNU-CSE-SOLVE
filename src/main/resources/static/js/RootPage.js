function checkAuth(){
    $.ajax({
        url: "/private/me",
        type: "GET",
        success: function (data){
            console.log(data)
        },
        error: function(xhr, status, error) {
            if (xhr.status === 401) {
                // Redirect URL을 헤더에서 가져와 리다이렉션 수행
                var redirectUrl = xhr.getResponseHeader("Redirect-URL");
                if (redirectUrl) {
                    window.location.href = redirectUrl;
                }
            }
        }
    })
}