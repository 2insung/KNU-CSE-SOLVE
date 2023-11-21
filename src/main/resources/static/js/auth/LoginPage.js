var token = $("meta[name='_csrf']").attr("content")

document.addEventListener("DOMContentLoaded", function () {
    var loginForm = document.getElementById("loginForm");

    loginForm.addEventListener("submit", function (event) {
        var username = $("#username").val()
        var password = $("#password").val()

        if (username === "") {
            $("#errorMessage").text("이메일을 입력해주세요.")
            event.preventDefault()
            return
        }

        if (password === "") {
            $("#errorMessage").text("비밀번호를 입력해주세요.")
            event.preventDefault();
            return;
        }
    })
});
