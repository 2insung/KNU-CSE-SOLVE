var token = $("meta[name='_csrf']").attr("content")

function toggleDropdown() {
    var dropdownContents = document.getElementsByClassName("drop-down-menu");
    for (var i = 0; i < dropdownContents.length; i++) {
        var dropdownContent = dropdownContents[i];
        if (dropdownContent.style.display === "block") {
            dropdownContent.style.display = "none";
        }
        else {
            dropdownContent.style.display = "block";
        }
    }
}
