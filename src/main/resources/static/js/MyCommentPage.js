var token = $("meta[name='_csrf']").attr("content")


function deleteMyComment(commentId, userId, currentPage, postId) {
    $.ajax(
        {
            url: "/api/delete-my-comment",
            type: "DELETE",
            data: JSON.stringify({
                commentId: commentId,
                postId: postId,
                commentAuthorId: userId,
                currentPage: currentPage
            }),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (response) {
                alert("삭제되었습니다..")
                window.location.href = "/my/comment/" + response.userId + "?page=" + response.currentPage
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
        }
    )
}