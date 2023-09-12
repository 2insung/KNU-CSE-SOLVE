var token = $("meta[name='_csrf']").attr("content")

window.onload = function() {
    // 쿼리 스트링에서 'code'와 'error' 값을 가져오기
    var code = new URLSearchParams(window.location.search).get('code');
    var error = new URLSearchParams(window.location.search).get('error');

    if (code && error) {
        var errorBox = document.getElementById('errorBox');
        var errorCode = document.getElementById('errorBoxCode');
        var errorMessage = document.getElementById('errorBoxMessage');

        errorCode.textContent = "code : " + code;
        errorMessage.textContent = decodeURIComponent(error);

        errorBox.style.display = 'block';

        setTimeout(function() {
            errorBox.style.display = 'none';
        }, 5000);
    }
}

function showErrorMessage(message){
    $("#errorMessage").text(message)
}

function initErrorMessage(){
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

    if(inputNickname.length > 10){
        showErrorMessage("닉네임은 10자 이하로 입력해주세요.")
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
        url: "/public/signup/send-email",
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
            url: "/public/signup/check-code",
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