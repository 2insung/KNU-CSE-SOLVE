var token = $("meta[name='_csrf']").attr("content")

function saveBoard() {
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

    if (type.length < 5 || type.length > 50) {
        alert("게시판 타입은 5~50자 내로 입력해주세요.")
        return
    }

    if (alias.length < 5 || alias.length > 50) {
        alert("게시판 이름은 4~20자 내로 입력해주세요.")
        return
    }

    if (description.length > 100) {
        alert("게시판 설명은 100자 내로 입력해주세요.")
        return
    }

    $.ajax(
        {
            url: "/api/save-board",
            type: "POST",
            data: JSON.stringify({
                boardAlias: alias,
                boardDescription: description,
                boardCategory: category
            }),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function (response) {
                window.location.href = "/all-board"
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