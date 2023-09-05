var token = $("meta[name='_csrf']").attr("content")

function showErrorMessage(message){
    $("#errorMessage").css("display", "block")
    $("#errorMessage").text(message)
}

function initErrorMessage(){
    $("#errorMessage").css("display", "block")
    $("#errorMessage").text("")
}

function convertInputUserInfoToConfirmCode(username){
    initErrorMessage()
    $("#inputUserInfo").css("display", "none")
    $("#confirmCode").css("display", "block")
    $("#codeUsername").text(username)
}

function sendEmail(){
    var inputUsername = $("#username").val()
    var inputNickname = $("#nickname").val()
    var inputPassword = $("#password").val()
    var inputPasswordConfirm = $("#passwordConfirm").val()

    if(inputNickname === ""){
        showErrorMessage("닉네임을 입력해주세요.")
        return
    }

    if(inputUsername === ""){
        showErrorMessage("이메일을 입력해주세요.")
        return
    }

    if(inputPassword === ""){
        showErrorMessage("비밀번호를 입력해주세요.")
        return;
    }

    if(inputPassword !== inputPasswordConfirm){
        showErrorMessage("비밀번호를 올바르게 입력해주세요.")
        return
    }

    $.ajax({
        url: "/check",
        type: "POST",
        data: {
            nickname : inputNickname,
            username : inputUsername
        },
        beforeSend: function(xhr) {
            $("#sendEmailButton").text("이메일 전송 중..")
            // CSRF 토큰 값을 헤더에 추가
            xhr.setRequestHeader("X-CSRF-TOKEN", token);
        },
        success: function (data){
            if(data === "InvalidNickname"){
                showErrorMessage("중복된 닉네임입니다.")
            }
            else if(data === "InvalidUsername"){
                showErrorMessage("중복된 이메일입니다.")
            }
            else{
                convertInputUserInfoToConfirmCode(inputUsername)
            }
        },
        error: function (error){
            showErrorMessage("알 수 없는 에러입니다.")
        },
        complete: function (){
            $("#sendEmailButton").text("이메일 인증하기")
        }
    })
}

function checkCode() {
    var inputCode = $("#code").val()
    var inputUsername = $("#username").val()
    $.ajax(
        {
            url: "/checkCode",
            type: "POST",
            data: {
                    email: inputUsername,
                    code: inputCode
            },
            beforeSend: function(xhr) {
                // CSRF 토큰 값을 헤더에 추가
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (data){
                if (data){
                    $("#checkCodeResult").css("display", "block")
                    $("#checkCodeResult").text("인증 성공입니다.")
                    $("#submitButton").css("display","block")
                    $("#inputCodeWrapper").css("display", "none")
                    $("#code").val(null)
                }
                else{
                    $("#checkCodeResult").css("display", "block")
                    $("#checkCodeResult").text("인증 실패입니다. 다시 시도해주세요.")
                }
            },
            error: function (error){
                showErrorMessage("알 수 없는 에러입니다.")
            }
        }
    )
}