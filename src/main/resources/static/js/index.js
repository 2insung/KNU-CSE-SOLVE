function fetchData(){
    $.ajax({
        url: "/auth/me",
        type: "GET",
        beforeSend: function(xhr) {
            // CSRF 토큰 값을 헤더에 추가
            xhr.setRequestHeader("X-CSRF-TOKEN", $("#csrfToken").val());
        }
    }).done(function (data){
        $("#result").replaceWith(data);
    })
}