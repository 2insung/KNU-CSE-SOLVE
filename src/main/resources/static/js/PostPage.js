var token = $("meta[name='_csrf']").attr("content")

function showForm(button) {
    var buttonElement = button.closest('li');
    var formToShow = buttonElement.querySelector('#childCommentForm');
    var activeForm = document.querySelector('#childCommentForm[style*="display: flex"]');

    document.querySelectorAll('#childCommentForm').forEach(function (form) {
        form.style.display = 'none';
        form.querySelector('textarea.comment-input').value = '';
    });

    if (activeForm !== formToShow) {
        formToShow.style.display = 'flex';
    }
}

function deletePost(boardType, postAuthor, postId) {
    $.ajax(
        {
            url: "/api/delete-post/" + postAuthor,
            type: "DELETE",
            data: JSON.stringify({
                boardType: boardType,
                postId: postId
            }),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (response) {
                window.location.href = "/board/" + response.boardType
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

function deleteComment(commentAuthor, commentId, postId, currentPageNumber) {
    $.ajax(
        {
            url: "/api/delete-comment/" + commentAuthor,
            type: "DELETE",
            data: JSON.stringify({
                postId: postId,
                commentId: commentId,
                currentPageNumber: currentPageNumber
            }),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (response) {
                readComment(postId, response.commentPageNumber)
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

function saveChildComment(commentId, postId, currentPageNumber) {
    var body = document.getElementById('childContent-' + commentId).value;
    if (body === "") {
        alert("내용을 입력해주세요.")
        return;
    }
    $.ajax(
        {
            url: "/api/save-comment",
            type: "POST",
            data: JSON.stringify({
                postId: postId,
                parentCommentId: commentId,
                currentPageNumber: currentPageNumber,
                body: body
            }),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (response) {
                readComment(postId, response.commentPageNumber)
            },
            error: function (error) {
                if (error.status === 401 || error.status === 403) {
                    var redirectUrl = error.getResponseHeader("Redirect-URL");
                    console.log(redirectUrl)
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

function saveRootComment(postId) {
    var body = $("#rootContent").val()
    if (body === "") {
        alert("내용을 입력해주세요.")
        return;
    }
    $.ajax(
        {
            url: "/api/save-comment",
            type: "POST",
            data: JSON.stringify({
                postId: postId,
                parentCommentId: null,
                currentPageNumber: null,
                body: body
            }),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (response) {
                readComment(postId, response.commentPageNumber)
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

function readComment(postId, pageNumber) {
    $.ajax(
        {
            url: "/read-comment/" + postId + "?page=" + pageNumber,
            type: "GET",
            success: function (response) {
                $("#commentFragment").replaceWith(response)
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