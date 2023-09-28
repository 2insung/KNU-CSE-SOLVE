document.addEventListener("DOMContentLoaded", function () {
    $(document).ready(function() {
        $('.commentWrapper').click(function() {
            let replyForm = $(this).next('.commentFormParent');

            // 이미 해당 대댓글 폼이 보이는 상태라면
            if(replyForm.is(':visible')) {
                // 해당 대댓글 폼을 숨깁니다.
                replyForm.hide();
            } else {
                // 아니라면, 모든 대댓글 폼을 숨기고
                $('.commentFormParent').hide();

                // 클릭한 댓글 바로 아래의 대댓글 폼만 보이게 합니다.
                replyForm.show();
            }
        });
    });
});
