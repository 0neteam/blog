$(() => {

<<<<<<< HEAD
    // 부모 메뉴 클릭
    $('#menuList').on('click', '> li > div > span', function () {
        const menuItem = $(this).closest('li');
        const menuName = menuItem.data('menuname');
        const menuNo = menuItem.data('menuno');
        const menuRef = menuItem.data('menuref');
        setMenuName(menuName, menuNo, menuRef);
    });

    // 자식 메뉴 클릭
    $('#menuList').on('click', 'ul > li > span', function () {
        const menuItem = $(this).closest('li');
        const menuName = menuItem.data('menuname');
        const menuNo = menuItem.data('menuno');
        const menuRef = menuItem.data('menuref');
        setMenuName(menuName, menuNo, menuRef);
    });

    // 삭제 버튼 (부모/자식 메뉴 모두)
    $('#menuList').on('click', '.delete-btn', function (e) {
        e.stopPropagation();
        const menuItem = $(this).closest('li');
        const menuName = menuItem.data('menuname');
        const menuNo = menuItem.data('menuno');

        if (confirm("메뉴를 삭제하시겠습니까?")) {

            const data = {
                no: menuNo,
                name: menuName
            };

            $.ajax({
                url: `/menu/delete`,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data)
            })
                .done(function (response) {
                        alert('삭제 성공');
                        menuItem.remove();
                })
                .fail(function (error) {
                    console.error('Error:', error);
                    alert('삭제 실패');
                });
        }
    });

    // 추가 버튼
    $('#add').on('click', function () {
        const menuName = $('#name').val();
        const menuRef = $('#ref').val();
        let menuDepth = 1;
        if (!menuName) {
            alert('메뉴명을 입력해 주세요.');
            return;
        }
        const menuData = {
            name: menuName,
            ref: menuRef,
            depth: menuDepth
        };
        if (confirm("메뉴를 추가하시겠습니까?")) {
=======
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
>>>>>>> 9df0473219adfc8aee6e8c63b7718c6bff7cccfb
            $.ajax({
                url: `/menu/add`,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(menuData)
            })
                .done(function (response) {
                    alert('추가 성공');
                    location.reload();
                })
                .fail(function (error) {
                    console.error('Error:', error);
                    alert('추가 실패');
                });
        }
    });

    // 그룹 추가 버튼
    $('#groupadd').on('click', function () {
        const menuName = $('#name').val();
        const menuRef = 0;
        let menuDepth = 0;
        if (!menuName) {
            alert('그룹명을 입력해 주세요.');
            return;
        }
        const menuData = {
            name: menuName,
            ref: menuRef,
            depth: menuDepth
        };

        if (confirm("그룹을 추가하시겠습니까?")) {
            $.ajax({
                url: `/menu/add`,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(menuData)
            })
                .done(function (response) {
                    alert('추가 성공');
                    location.reload();
                })
                .fail(function (error) {
                    console.error('Error:', error);
                    alert('추가 실패');
                });
        }
    });

    // 수정 버튼 클릭 시
    $('#edit').on('click', function () {
        const menuNo = $('#no').val();
        const menuName = $('#name').val();
        const menuRef = $('#ref').val();
        let menuDepth = 0;
        if (menuRef != 0) {
            menuDepth = 1;
        }
        if (!menuName) {
            alert('메뉴명을 입력해 주세요.');
            return;
        }
        const menuData = {
            no: menuNo,
            name: menuName,
            ref: menuRef
        };

        if (confirm("메뉴를 수정하시겠습니까?")) {
            $.ajax({
                url: `/menu/edit`,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(menuData)
            })
                .done(function (response) {
                    alert('수정 완료');
                    location.reload();
                })
                .fail(function (error) {
                    console.error('Error:', error);
                    alert('수정 실패');
                });
        }
    });

    // 그룹 만들기 버튼
    $('#group').on('click', function () {
        $('#name').val('');
        $('#no').val('');
        $('#ref').val(0);
        $('#ref').hide();
        $('#reflabel').hide();
        $('#add').hide();
        $('#groupadd').show();
        $('#edit').hide();
        $('#name').attr('placeholder', '그룹명을 입력하세요');
    });

    // 취소 버튼
    $('#reset').on('click', function () {
        setToAddMode();
    });

    function setMenuName(menuName, menuNo, menuRef) {
        $('#name').val(menuName);
        $('#no').val(menuNo);
        $('#ref').val(menuRef);
        $('#reflabel').show();
        $('#ref').show();
        $('#groupadd').hide();
        $('#add').hide();
        $('#edit').show();
    }

    function setToAddMode() {
        $('#name').val('');
        $('#no').val('');
        $('#ref').val(0);
        $('#reflabel').show();
        $('#ref').show();
        $('#groupadd').hide();
        $('#add').show();
        $('#edit').hide();
    }
});