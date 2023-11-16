var token = $("meta[name='_csrf']").attr("content")

function pwEdit(userId){
    var currentPassword = $("#currentPassword").val()
    var changePassword = $("#changePassword").val()
    var confirmPassword = $("#confirmPassword").val()

    if(currentPassword === ""){
        alert("현재 비밀번호를 입력해주세요.")
        return
    }

    if(changePassword === ""){
        alert("변경할 비밀번호를 입력해주세요.")
        return
    }

    if(confirmPassword === ""){
        alert("변경할 비밀번호를 재확인해주세요.")
        return
    }

    if(changePassword !== confirmPassword){
        alert("변경할 비밀번호를 재확인해주세요.")
        return
    }

    $.ajax(
        {
            url: "/api/update-my-password",
            type: "PATCH",
            data: JSON.stringify({
                userId : userId,
                currentPassword: currentPassword,
                changePassword: changePassword
            }),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (response) {
                alert("정상적으로 변경되었습니다.")
                window.location.href = "/my/" + response.userId
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