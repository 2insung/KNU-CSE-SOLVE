var token = $("meta[name='_csrf']").attr("content")

function showErrorMessage(message) {
    $("#errorMessage").text(message)
}

function signup() {
    var inputUsername = $("#username").val()
    var inputNickname = $("#nickname").val()
    var inputPassword = $("#password").val()
    var inputPasswordConfirm = $("#passwordConfirm").val()

    if (inputNickname === "") {
        showErrorMessage("닉네임을 입력해주세요.")
        return
    }

    if (inputNickname.length > 10) {
        showErrorMessage("닉네임은 10자 이하로 입력해주세요.")
        return
    }

    if (inputUsername === "") {
        showErrorMessage("이메일을 입력해주세요.")
        return
    }

    if (inputPassword === "") {
        showErrorMessage("비밀번호를 입력해주세요.")
        return;
    }

    if (inputPassword !== inputPasswordConfirm) {
        showErrorMessage("비밀번호를 올바르게 입력해주세요.")
        return
    }

    $.ajax({
        url: "/api/signup",
        type: "POST",
        data: JSON.stringify({
            username: inputUsername,
            password: inputPassword,
            nickname: inputNickname
        }),
        contentType: 'application/json',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("X-CSRF-TOKEN", token);
        },
        success: function (response) {
            if (response.result) {
                alert("회원가입이 완료되었습니다.")
                window.location.href = "/login"
            }
        },
        error: function (error) {
            if (error.status === 401 || error.status === 403) {
                var redirectUrl = error.getResponseHeader("Redirect-URL");
                if (redirectUrl) {
                    window.location.href = redirectUrl;
                }
            }
            else if (error.status === 404) {
                showErrorMessage(error.responseText)
            }
            else {
                showErrorMessage("알 수 없는 오류입니다.")
            }
        }
    })
}