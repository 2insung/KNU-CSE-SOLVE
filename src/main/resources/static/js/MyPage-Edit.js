var token = $("meta[name='_csrf']").attr("content")
var isValidNickname = true;

document.addEventListener('DOMContentLoaded', function () {
    const imageInput = document.getElementById('imageInput');
    if (imageInput) {
        imageInput.addEventListener('change', function (event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    document.getElementById("previewImage").src = e.target.result;
                }
                reader.readAsDataURL(file);
            }
        });
    }
});




document.addEventListener("DOMContentLoaded", function () {
    var editProfileForm = document.getElementById("editProfileForm");

    editProfileForm.addEventListener("submit", function (event) {
        var inputNickname = $("#nickname").val()
        var inputDescription = $("#description").val()

        if (inputNickname === "") {
            alert("닉네임을 입력해주세요");
            event.preventDefault()
            return;
        }

        if (inputNickname.length > 10) {
            alert("닉네임은 10자 이하만 가능합니다.");
            event.preventDefault()
            return;
        }

        if (inputNickname.length > 50) {
            alert("자기소개는 50자 이하만 가능합니다.");
            event.preventDefault()
            return;
        }

        if (isValidNickname === false) {
            alert("중복 검사를 해주세요.");
            event.preventDefault()
            return;
        }

    })
});


document.addEventListener("DOMContentLoaded", function () {
    let timeout;
    var prevNickname = $("#nickname").val();

    $("#nickname").change(function() {
        let currentNickname = $("#nickname").val();

        if(timeout){
            clearTimeout(timeout);
        }

        if (currentNickname !== prevNickname) {
            timeout = setTimeout(function() {
                $.ajax({
                    url: "/private/checkValidNickname",
                    type: "POST",
                    data: {
                        nickname: currentNickname
                    },
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader("X-CSRF-TOKEN", token);
                    },
                    success: function(data) {
                        if(data) {
                            $("#confirmVaildNickname").text("사용 가능한 닉네임입니다.");
                            isValidNickname = true;
                        } else {
                            $("#confirmVaildNickname").text("이미 사용 중인 닉네임입니다. 다른 닉네임을 선택하세요.");
                            isValidNickname = false;
                        }
                    },
                    error: function() {
                        if (xhr.status === 401) {
                            var redirectUrl = xhr.getResponseHeader("Redirect-URL");
                            if (redirectUrl) {
                                window.location.href = redirectUrl;
                            }
                        }
                        $("#confirmVaildNickname").text("닉네임 확인 중 오류가 발생했습니다.");
                    }
                });
            }, 500);
        }

    });

})

