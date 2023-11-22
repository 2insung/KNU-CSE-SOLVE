var token = $("meta[name='_csrf']").attr("content")

function deleteMyScrap(postId, memberId, currentPageNumber) {
    if (confirm("스크랩을 취소하시겠습니까?")) {
        $.ajax(
            {
                url: "/api/delete-my-scrap",
                type: "DELETE",
                data: JSON.stringify({
                    postId: postId,
                    memberId: memberId,
                    currentPageNumber: currentPageNumber
                }),
                contentType: 'application/json',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", token);
                },
                success: function (response) {
                    alert("취소되었습니다.")
                    window.location.href = "/my/scrap/" + response.memberId + "?page=" + response.pageNumber
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