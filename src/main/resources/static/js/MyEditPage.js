var token = $("meta[name='_csrf']").attr("content")

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

function submitHandler(memberId) {
    var fileInput = document.getElementById('imageInput');
    var file = fileInput.files[0]; // 사용자가 선택한 파일
    var nickname = $("#nickname").val();
    var description = $("#description").val();
    var department = $("#department").val();
    var grade = $("#grade").val()
    var admissionYear = $("#admissionYear").val()

    var formData = new FormData();
    if(file !== undefined){
        formData.append("file", file);
    }
    formData.append("memberId", memberId); // 기존 닉네임 추가
    formData.append("nickname", nickname); // 닉네임 추가
    formData.append("description", description); // 설명 추가
    formData.append("grade", grade); // 학년 추가
    formData.append("admissionYear", admissionYear); // 입학년도 추가
    formData.append("department", department); // 학과 추가

    $.ajax({
        url: "/api/update-my",
        type: "POST",
        data: formData,
        processData: false, // FormData를 사용할 때 필요
        contentType: false, // FormData를 사용할 때 필요
        beforeSend: function (xhr) {
            xhr.setRequestHeader("X-CSRF-TOKEN", token);
        },
        success: function (response) {
            alert("수정했습니다.")
            window.location.href = "/my/" + response.memberId
        },
        error: function (error) {
            if (error.status === 401 || error.status === 403) {
                var redirectUrl = error.getResponseHeader("Redirect-URL");
                if (redirectUrl) {
                    window.location.href = redirectUrl;
                }
            }
            else {
                alert(error.responseText)
            }
        }
    });
}