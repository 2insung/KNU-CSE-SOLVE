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
        }
        else {
            menu.style.display = "none";
        }
    });
})