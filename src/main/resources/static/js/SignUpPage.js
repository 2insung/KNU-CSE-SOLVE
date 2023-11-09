var token = $("meta[name='_csrf']").attr("content")

function showErrorMessage(message) {
    $("#errorMessage").text(message)
}

function initErrorMessage() {
    $("#errorMessage").text("")
}

function convertInputUserFormToInputCodeForm(username) {
    initErrorMessage()
    $("#inputUserInfo").css("display", "none")
    $("#confirmCode").css("display", "block")
    $("#codeUsername").text(username)
}

function sendEmail() {
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
        url: "/api/send-email",
        type: "POST",
        data: JSON.stringify({
            username: inputUsername,
            nickname: inputNickname,
        }),
        contentType: 'application/json',
        dataType: 'json',
        beforeSend: function (xhr) {
            $("#sendEmailButton").text("이메일 전송 중..")
            xhr.setRequestHeader("X-CSRF-TOKEN", token);
        },
        success: function (response) {
            if (response.isExistingUsername) {
                showErrorMessage("중복된 닉네임입니다.")
            }
            else if (response.isExistingNickname) {
                showErrorMessage("중복된 이메일입니다.")
            }
            else {
                convertInputUserFormToInputCodeForm(inputUsername)
            }
        },
        error: function (error) {
            showErrorMessage("알 수 없는 에러입니다.")
        },
        complete: function () {
            $("#sendEmailButton").text("이메일 인증하기")
        }
    })
}

function checkCode() {
    var inputUsername = $("#username").val()
    var inputCode = $("#code").val()

    $.ajax(
        {
            url: "/api/check-code",
            type: "POST",
            data: JSON.stringify({
                username: inputUsername,
                code: inputCode
            }),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (response) {
                if (response.result) {
                    $("#checkCodeResult").css("display", "block")
                    $("#checkCodeResult").text("인증 성공입니다.")
                    $("#submitButton").css("display", "block")
                    $("#inputCodeWrapper").css("display", "none")
                    $("#code").val(null)
                }
                else {
                    $("#checkCodeResult").css("display", "block")
                    $("#checkCodeResult").text("인증 실패입니다. 다시 시도해주세요.")
                }
            },
            error: function (error) {
                showErrorMessage("알 수 없는 에러입니다.")
            }
        }
    )
}

function signup() {
    var inputUsername = $("#username").val()
    var inputPassword = $("#password").val()
    var inputNickname = $("#nickname").val()

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
            $("#sendEmailButton").text("이메일 전송 중..")
            xhr.setRequestHeader("X-CSRF-TOKEN", token);
        },
        success: function (response) {
            if (response.result) {
                window.location.href = "/login"
            }
        },
        error: function (error) {
            showErrorMessage("알 수 없는 에러입니다.")
        }
    })
}