var token = $("meta[name='_csrf']").attr("content")

function deleteMyComment(postId, commentId, memberId, currentPageNumber) {
    if (confirm("삭제하시겠습니까?")) {
        $.ajax(
            {
                url: "/api/delete-my-comment",
                type: "DELETE",
                data: JSON.stringify({
                    postId: postId,
                    commentId: commentId,
                    commentAuthorId: memberId,
                    currentPageNumber: currentPageNumber
                }),
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (response) {
                    alert("삭제되었습니다.")
                    window.location.href = "/my/comment/" + response.memberId + "?page=" + response.pageNumber
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
}