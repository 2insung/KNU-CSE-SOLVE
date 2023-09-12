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

