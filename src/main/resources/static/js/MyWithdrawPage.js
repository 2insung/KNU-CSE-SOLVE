var token = $("meta[name='_csrf']").attr("content")

function withdraw(userId) {
    $.ajax(
        {
            url: "/api/withdraw",
            type: "PATCH",
            data: JSON.stringify({
                userId: userId,
            }),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (response) {
                if (response.result) {
                    alert("정상적으로 비활성화되었습니다.\n 다음 로그인부터 계정이 바활성화됩니다.\n 재가입을 원할 시 동일한 정보로 회원가입하세요.")
                    window.location.href = "/"
                }
                else {
                    alert("이미 처리된 요청입니다.")
                }
            },
            error: function (error) {
                if (error.status === 401 || error.status === 403) {
                    var redirectUrl = xhr.getResponseHeader("Redirect-URL");
                    if (redirectUrl) {
                        window.location.href = redirectUrl;
                    }
                }
                else {
                    alert(error.responseText)
                }
            }
        }
    )
}