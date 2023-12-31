var token = $("meta[name='_csrf']").attr("content")
$(document).ready(function () {
    const $ckUploadPath = "/image/upload?_csrf=" + token;
    CKEDITOR.replace('content', {
        filebrowserUploadUrl: $ckUploadPath,
        width: '1100px',
        height: '500px',
        resize_enabled: false
    })
})

function savePost(boardId) {
    if (confirm("등록하시겠습니까?")) {
        var title = $("#title").val()
        var content = CKEDITOR.instances.content.getData();
        var isNotice = $("#isNotice").is(":checked")

        if (title === "") {
            alert("제목을 입력해주세요.")
            return
        }

        if (content === "") {
            alert("내용을 입력해주세요.")
            return;
        }

        if (title.length > 50) {
            alert("게시글 제목은 50자 내로 입력해주세요.")
            return;
        }

        $.ajax(
            {
                url: "/api/save-post",
                type: "POST",
                data: JSON.stringify({
                    boardId: boardId,
                    postTitle: title,
                    postBody: content,
                    postIsNotice: isNotice
                }),
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (response) {
                    window.location.href = "/board/" + response.boardId
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
}

function updatePost(postId, postAuthorId) {
    if (confirm("수정하시겠습니까?")) {
        var title = $("#title").val()
        var content = CKEDITOR.instances.content.getData();
        var isNotice = $("#isNotice").is(":checked")

        if (title === "") {
            alert("제목을 입력해주세요.")
            return
        }

        if (content === "") {
            alert("내용을 입력해주세요.")
            return;
        }

        if (title.length > 50) {
            alert("게시글 제목은 50자 내로 입력해주세요.")
            return;
        }

        $.ajax(
            {
                url: "/api/update-post",
                type: "PATCH",
                data: JSON.stringify({
                    postId: postId,
                    postAuthorId: postAuthorId,
                    postTitle: title,
                    postBody: content,
                    postIsNotice: isNotice
                }),
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (response) {
                    window.location.href = "/post/" + response.postId
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
}
