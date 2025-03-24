$(() => {
    //메뉴추가
    $('#addMenu').on('submit', (event) => {
        event.preventDefault();

        const ref = $('#ref').val();
        const name = $('#menuName').val();
        //ref 값이 0이 아니면 부모메뉴가 아님
        //ref값이 0이면 부모메뉴 설정으로
        if (ref !== '0') {
            let depth = 1;

            if (!name) {
                alert("메뉴명을 입력해주세요.");
                return;
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
        }
        //정신 맑을때 코드 수정해야함. '25.03.24 20:35 강승우
        // else{
        //     if(!name){
        //         alert("그룹명을 입력해주세요.");
        //         return;
        //     }
        //     let depth = 0;

        //     let data = {
        //         name: name,
        //         ref: ref,
        //         depth: depth
        //     };
        //     console.log(data);

        //     $.ajax({
        //         url: '/menu/add',
        //         type: 'POST',
        //         contentType: 'application/json',
        //         data: JSON.stringify(data)
        //     }).done(function () {
        //         alert("그룹이 추가되었습니다.");
        //         location.reload();
        //     }).fail(function (error) {
        //         alert("그룹 추가에 실패했습니다.");
        //     });
        // }
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