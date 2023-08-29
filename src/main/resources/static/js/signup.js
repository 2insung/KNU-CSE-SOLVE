var token = $("meta[name='_csrf']").attr("content")

function sendEmail(){
    var inputValue = $("#username").val()
    $.ajax({
        url: `/email?username=${inputValue}`,
        type: "GET",

        success: function (data){
            $("#code").text("인증 번호가 발송됐습니다.")
        },
        error: function (error){
            alert("인증번호 발송에 실패했습니다.");
        }

    })
}

function checkCode() {
    var inputCode = $("#inputCode").val()
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
                    $("#confirm").text("인증 성공입니다..")
                }
                else{
                    $("#confirm").text("다시 시도해라")
                }
            },
            error: function (error){
                alert("뭔가 이상함");
            }
        }
    )
}