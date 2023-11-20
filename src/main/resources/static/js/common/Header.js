function toggleDropdown() {
    var dropdownContent = document.getElementById("dropDownMenu");
    if (dropdownContent.style.display === "block") {
        dropdownContent.style.display = "none";
    }
    else {
        dropdownContent.style.display = "block";
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const menu = document.getElementById('boardMenu');
    const hamburger = document.getElementById('hamburger');
    hamburger.addEventListener('click', () => {
        if (menu.style.display === "none" || menu.style.display === "") {
            menu.style.display = "block";
            showBoardList()
        }
        else {
            menu.style.display = "none";
        }
    });
})

function showBoardList() {
    $.ajax({
        url: "/board-menu",
        type: "GET",
        success: function (data) {
            $("#boardMenuList").replaceWith(data)
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
    });
}