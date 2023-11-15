var token = $("meta[name='_csrf']").attr("content")
function makeBoard() {
    var alias = $("#alias").val()
    var type = $("#type").val()
    var category = $("#category").val()
    var description = $("#description").val()

    if (alias === "") {
        alert("이름을 입력해주세요.")
        return
    }
    if (type === "") {
        alert("타입을 입력해주세요.")
        return
    }
    if (category === "") {
        alert("카테고리를 입력해주세요.")
        return
    }
    if (description === "") {
        alert("설명을 입력해주세요.")
        return
    }

    if (description.length > 100) {
        alert("100자 이하로 입력해주세요.")
        return
    }

    $.ajax(
        {
            url: "/api/save-board",
            type: "POST",
            data: JSON.stringify({
                type: type,
                alias: alias,
                description: description,
                category: category
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