var token = $("meta[name='_csrf']").attr("content")
$(document).ready(function () {
    const $ckUploadPath = "/image/upload?_csrf=" + token;
    CKEDITOR.replace('content', {
        filebrowserUploadUrl: $ckUploadPath,
        width: '1200px',
        height: '400px',
        resize_enabled: false
    })
})

document.addEventListener("DOMContentLoaded", function () {
    var writeForm = document.getElementById("writeForm");

    writeForm.addEventListener("submit", function (event) {
        var title = $("#title").val()
        var content = $("#content").val()

        if (title === "") {
            alert("제목을 입력해주세요.")
            event.preventDefault()
            return
        }

        if (content === "") {
            alert("내용을 입력해주세요.")
            event.preventDefault();
            return;
        }

    })
});
