$( () => {

    $('#addMenu').on('submit', (event) => {
        event.preventDefault();

        const ref = $('#ref').val();
        const name = $('#menuName').val();

        let depth =0;

        if (!name) {
            alert("메뉴명을 입력해주세요.");
            return;
        }

        if(ref !== '0'){
            depth = 1;
        }
        
        let data = {
            name: name,
            ref: ref,
            depth: depth
        };
        console.log(data);

        $.ajax({
            url: '/menu/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data)
        }).done(function () {
            alert("메뉴가 추가되었습니다.");
            location.reload();
        }).fail(function (error) {
                alert("메뉴 추가에 실패했습니다.");
        });
    });
    
});