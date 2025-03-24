$(() => {
    //메뉴추가
    $('#addMenu').on('submit', (event) => {
        event.preventDefault();

        const ref = $('#ref').val();
        const name = $('#menuName').val();

        let depth = 0;

        if (!name) {
            alert("메뉴명을 입력해주세요.");
            return;
        }

        if (ref !== '0') {
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

    //메뉴삭제
    $('#deleteMenu').on('click', (event) => {
        if (confirm("정말 삭제하시겠습니까?")) {
            var array = [];
            $('.menuCB').each(function (index, item) {
                if (item.checked) {
                    const menuNo = $(item).closest('li').find('.menuNo').val();
                    console.log(menuNo);
                    //체크되어있는 체크박스와 가장 가까운 p태그속에 있는 텍스트 가져오기
                    const menuName = $(item).closest('li').find('.menuName').text();
                    console.log(menuName);

                    const data = {
                        no: menuNo,
                        name: menuName
                    };
                    array.push(data);
                }
            });
            // var JsonArray = JSON.stringify(array);
            console.log("보내질 json 배열 값: " , array);
            $.ajax({
                url: "/menu/delete",
                type: "POST",
                contentType: 'application/json',
                data: JSON.stringify(array)
            }).done(function () {
                alert("메뉴 삭제 완료.");
                location.reload();
            }).fail(function (error) {
                alert("메뉴 삭제에 실패했습니다.");
            });

        }

    });

});