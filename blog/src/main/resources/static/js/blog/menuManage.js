$( () => {
    $('#addMenu').on('click', () => {
        var ref = $('#ref').val();
        var name = $('#menuName').val();
        var depth =0;
        if (name == "") {
            alert("메뉴명을 입력해주세요.");
            return;
        }

        if(ref !== 0){
            depth = 1;
        }

        let data = {
            ref: ref,
            name: name,
            depth: depth
        };

        $.ajax({
            url: '/menu/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (data) {
                alert("메뉴가 추가되었습니다.");
                location.reload();
            },
            error: function (data) {
                alert("메뉴 추가에 실패했습니다.");
            }
        });
    });
});