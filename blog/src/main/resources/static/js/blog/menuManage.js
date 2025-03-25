$(document).ready(function() {

    const domain = $('body').data('domain');

    // 부모 메뉴 클릭
    $('#menuList').on('click', '> li > div > span', function() {
        const menuItem = $(this).closest('li');
        const menuName = menuItem.data('menuname');
        const menuNo = menuItem.data('menuno');
        const menuRef = menuItem.data('menuref');
        setMenuName(menuName, menuNo, menuRef);
    });

    // 자식 메뉴 클릭
    $('#menuList').on('click', 'ul > li > span', function() {
        const menuItem = $(this).closest('li');
        const menuName = menuItem.data('menuname');
        const menuNo = menuItem.data('menuno');
        const menuRef = menuItem.data('menuref');
        setMenuName(menuName, menuNo, menuRef);
    });

    // 삭제 버튼 (부모/자식 메뉴 모두)
    $('#menuList').on('click', '.delete-btn', function(e) {
        e.stopPropagation();
        const menuItem = $(this).closest('li');
        const menuNo = menuItem.data('menuno');

        if (confirm("메뉴를 삭제하시겠습니까?")) {
            $.ajax({
                url: `/${domain}/menu/${menuNo}`,
                type: 'DELETE'
            })
            .done(function(response) {
                    alert('삭제 성공');
                    menuItem.remove();
            })
            .fail(function(error) {
                console.error('Error:', error);
                alert('삭제 실패');
            });
        }
    });

    // 추가 버튼
    $('#add').on('click', function() {
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
            $.ajax({
                url: `/${domain}/menu`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(menuData)
            })
            .done(function(response) {
                alert('추가 성공');
                location.reload();
            })
            .fail(function(error) {
                console.error('Error:', error);
                alert('추가 실패');
            });
        }
    });

    // 그룹 추가 버튼
    $('#groupadd').on('click', function() {
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
                url: `/${domain}/menu`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(menuData)
            })
            .done(function(response) {
                alert('추가 성공');
                location.reload();
            })
            .fail(function(error) {
                console.error('Error:', error);
                alert('추가 실패');
            });
        }
    });

    // 수정 버튼 클릭 시
    $('#edit').on('click', function() {
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
                url: `/${domain}/menu`,
                type: 'Patch',
                contentType: 'application/json',
                data: JSON.stringify(menuData)
            })
            .done(function(response) {
                alert('수정 완료');
                location.reload();
            })
            .fail(function(error) {
                console.error('Error:', error);
                alert('수정 실패');
            });
        }
    });

    // 그룹 만들기 버튼
    $('#group').on('click', function() {
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
    $('#reset').on('click', function() {
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
