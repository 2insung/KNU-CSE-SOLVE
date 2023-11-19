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

function deletePost(boardType, postId, postAuthorId) {
    if (confirm("삭제하시겠습니까?")) {
        $.ajax(
            {
                url: "/api/delete-post",
                type: "DELETE",
                data: JSON.stringify({
                    boardType: boardType,
                    postId: postId,
                    postAuthorId: postAuthorId
                }),
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (response) {
                    alert("삭제되었습니다.")
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

function saveRootComment(postId) {
    if (confirm("등록하시겠습니까?")) {
        var body = $("#rootContent").val()

        if (body === "") {
            alert("내용을 입력해주세요.")
            return;
        }

        if (body > 500) {
            alert("댓글은 500자 내로 입력해주세요.")
            return;
        }

        $.ajax(
            {
                url: "/api/save-root-comment",
                type: "POST",
                data: JSON.stringify({
                    postId: postId,
                    commentBody: body
                }),
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (response) {
                    readComment(postId, response.pageNumber)
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


function saveChildComment(commentId, postId, currentPageNumber) {
    if (confirm("등록하시겠습니까?")) {
        var body = document.getElementById('childComment-' + commentId).value;
        if (body === "") {
            alert("내용을 입력해주세요.")
            return;
        }

        if (body > 500) {
            alert("댓글은 500자 내로 입력해주세요.")
            return;
        }

        $.ajax(
            {
                url: "/api/save-child-comment",
                type: "POST",
                data: JSON.stringify({
                    postId: postId,
                    parentCommentId: commentId,
                    currentPageNumber: currentPageNumber,
                    commentBody: body
                }),
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (response) {
                    readComment(postId, response.pageNumber)
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
}

function deleteComment(postId, commentId, commentAuthorId, currentPageNumber) {
    if (confirm("삭제하시겠습니까?")) {
        $.ajax(
            {
                url: "/api/delete-comment",
                type: "DELETE",
                data: JSON.stringify({
                    postId: postId,
                    commentId: commentId,
                    commentAuthorId: commentAuthorId,
                    currentPageNumber: currentPageNumber
                }),
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (response) {
                    alert("삭제되었습니다.")
                    readComment(postId, response.pageNumber)
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

function incPostRecommend(postId) {
    if (confirm("추천하시겠습니까?")) {
        $.ajax(
            {
                url: "/api/inc-post-recommend",
                type: "POST",
                data: JSON.stringify({
                    postId: postId
                }),
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (response) {
                    if (response.isSuccess) {
                        alert("추천되었습니다.")
                        $("#postRecommendCount").replaceWith(response.recommendCount)
                    }
                    else {
                        alert("이미 추천한 게시글입니다.")
                    }
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

function incCommentRecommend(commentId) {
    if (confirm("추천하시겠습니까?")) {
        $.ajax(
            {
                url: "/api/inc-comment-recommend",
                type: "POST",
                data: JSON.stringify({
                    commentId: commentId
                }),
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (response) {
                    if (response.isSuccess) {
                        alert("추천되었습니다.")
                        var commentCountElementId = "#commentRecommendCount-" + commentId
                        $(commentCountElementId).replaceWith(response.recommendCount)
                    }
                    else {
                        alert("이미 추천한 게시글입니다.")
                    }
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