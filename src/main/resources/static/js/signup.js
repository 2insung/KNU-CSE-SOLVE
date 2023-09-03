var token = $("meta[name='_csrf']").attr("content")

function domInitialize(){
    $("#confirm").css("display", "none")
    $("#confirm").text("")
    $("#code").val(null)
    $("#notSubmitButton").css("display","block")
    $("#submitButton").css("display","none")
    $("#inputCodeDiv").css("display", "block")
    $("#inputUserInfo").css("display", "block")
    $("#confirmCode").css("display", "none")
}

function sendEmail(){
    var inputUsername = $("#username").val()
    var inputNickname = $("#nickname").val()
    var inputPassword = $("#password").val()
    var inputPasswordConfirm = $("#passwordConfirm").val()

    if(inputNickname === ""){
        $("#errorMessage").css("display", "block");
        $("#errorMessage").text("닉네임을 입력해주세요.")
        return
    }

    if(inputUsername === ""){
        $("#errorMessage").css("display", "block");
        $("#errorMessage").text("이메일을 입력해주세요.")
        return
    }

    if(inputPassword === ""){
        $("#errorMessage").css("display", "block");
        $("#errorMessage").text("비밀번호를 입력해주세요.")
        return;
    }

    if(inputPassword !== inputPasswordConfirm){
        $("#errorMessage").css("display", "block");
        $("#errorMessage").text("비밀번호를 올바르게 입력해주세요.")
        return
    }
    $("#confirmButton").text("이메일 전송 중..")
    $.ajax({
        url: "/check",
        type: "POST",
        data: {
            nickname : inputNickname,
            username : inputUsername
        },
        beforeSend: function(xhr) {
            // CSRF 토큰 값을 헤더에 추가
            xhr.setRequestHeader("X-CSRF-TOKEN", token);
        },
        success: function (data){
            if(data === "InvalidNickname"){
                $("#errorMessage").css("display", "block");
                $("#errorMessage").text("중복된 닉네임입니다.")
            }
            else if(data === "InvalidUsername"){
                $("#errorMessage").css("display", "block");
                $("#errorMessage").text("중복된 이메일입니다.")
            }
            else{
                $("#errorMessage").css("display", "none");
                $("#inputUserInfo").css("display", "none")
                $("#confirmCode").css("display", "block")
                $("#code").text("인증 번호가 발송됐습니다.")
                $("#codeUsername").text(inputUsername)
            }
        },
        error: function (error){
            $("#errorMessage").css("display", "block");
            $("#errorMessage").text("알 수 없는 에러입니다.")
        },
        complete: function (){
            $("#confirmButton").text("이메일 인증하기")
        }
    })
}




function checkCode() {
    var inputCode = $("#code").val()
    var inputEmail = $("#username").val()
    $.ajax(
        {
            url: "/checkCode",
            type: "POST",
            data: {
                    email: inputEmail,
                    code: inputCode
            },
            beforeSend: function(xhr) {
                // CSRF 토큰 값을 헤더에 추가
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (data){
                if (data){
                    $("#confirm").css("display", "block")
                    $("#confirm").text("인증 성공입니다.")
                    $("#submitButton").css("display","block")
                    $("#inputCodeDiv").css("display", "none")
                    $("#code").val(null)
                }
                else{
                    $("#confirm").css("display", "block")
                    $("#confirm").text("인증 실패입니다. 다시 시도해주세요.")
                }
            },
            error: function (error){
                $("#errorMessage").css("display", "block");
                $("#errorMessage").text("알 수 없는 에러입니다.")
            }
        }
    )
}