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
    if (confirm("수정하시겠습니까?")) {
        var fileInput = document.getElementById('imageInput');
        var file = fileInput.files[0];
        var nickname = $("#nickname").val();
        var description = $("#description").val();
        var department = $("#department").val();
        var grade = $("#grade").val()
        var admissionYear = $("#admissionYear").val()

        var formData = new FormData();
        if (file !== undefined) {
            formData.append("file", file);
        }

        if (description.length > 100) {
            alert("자기소개는 100자 내로 입력해주세요.")
            return
        }

        formData.append("memberId", memberId);
        formData.append("nickname", nickname);
        formData.append("description", description);
        formData.append("grade", grade);
        formData.append("admissionYear", admissionYear);
        formData.append("department", department);

        $.ajax({
            url: "/api/update-my",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
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
}