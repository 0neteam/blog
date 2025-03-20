$( () => {
    
    $('#addMenu').on('click', () => {

        // 입력값 디버깅
        console.log("ref element:", $('#ref'));
        console.log("menuName element:", $('#menuName'));

        const ref = $('#ref').val();
        const name = $('#menuName').val();


        console.log("ref value:", ref);
        console.log("name value:", name);


        let depth =0;

        if (!name) {
            alert("메뉴명을 입력해주세요.");
            return;
        }

        if(ref != '0'){
            depth = 1;
        }
        
        const data = {
            name: name,
            ref: ref,
            depth: depth
        };
        console.log(data);

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