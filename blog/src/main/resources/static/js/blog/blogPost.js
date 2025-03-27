$(document).ready(function() {
    // 메뉴 관리 관련 코드 (단, #menuList 요소가 존재하는지 확인)
    if ($('#menuList').length > 0) {
        const domain = $('body').data('domain');
        $('#menuList').on('click', '> li > div > span', function() {
            const menuItem = $(this).closest('li');
            const menuName = menuItem.data('menuname');
            const menuNo = menuItem.data('menuno');
            const menuRef = menuItem.data('menuref');
            console.log(menuRef);
            setMenuName(menuName, menuNo, menuRef);
        });

        // 자식 메뉴 클릭
        $('#menuList').on('click', 'ul > li > span', function() {
            const menuItem = $(this).closest('li');
            const menuName = menuItem.data('menuname');
            const menuNo = menuItem.data('menuno');
            const menuRef = menuItem.data('menuref');
            console.log(menuRef);
            setMenuName(menuName, menuNo, menuRef);
        });

        // 삭제 버튼
        $('#menuList').on('click', '.delete-btn', function(e) {
            e.stopPropagation();
            const menuItem = $(this).closest('li');
            const menuNo = menuItem.data('menuno');
            if (confirm("이 메뉴를 삭제하시겠습니까?")) {
                $.ajax({
                    url: `/${domain}/menu/delete`,
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({ no: menuNo })
                })
                .done(function(response) {
                    if (response === 'success') {
                        alert('삭제 성공');
                        menuItem.remove();
                    } else {
                        alert('삭제 실패');
                    }
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
            let menuDepth = 0;
            if (menuRef != 0) { menuDepth = 1; }
            if (!menuName) {
                alert('메뉴명을 입력해 주세요.');
                return;
            }
            const menuData = { name: menuName, ref: menuRef, depth: menuDepth };
            console.log(menuData);
            $.ajax({
                url: `/${domain}/menu/create`,
                type: 'POST',
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
        });

        // 수정 버튼
        $('#edit').on('click', function() {
            const menuNo = $('#no').val();
            const menuName = $('#name').val();
            const menuRef = $('#ref').val();
            let menuDepth = 0;
            if (menuRef != 0) { menuDepth = 1; }
            if (!menuName) {
                alert('메뉴명을 입력해 주세요.');
                return;
            }
            const menuData = { no: menuNo, name: menuName, ref: menuRef };
            console.log(menuData);
            $.ajax({
                url: `/${domain}/menu/edit`,
                type: 'POST',
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
        });

        // 취소 버튼
        $('#reset').on('click', function() {
            setToAddMode();
        });

        function setMenuName(menuName, menuNo, menuRef) {
            $('#name').val(menuName);
            $('#no').val(menuNo);
            $('#ref').val(menuRef);
            $('#add').hide();
            $('#edit').show();
        }

        function setToAddMode() {
            $('#name').val('');
            $('#no').val('');
            $('#ref').val(0);
            $('#add').show();
            $('#edit').hide();
        }
    }

    // EditorJS 초기화
    const editor = new EditorJS({
        holder: 'editorjs',
        data: { blocks: [] },
        tools: {
            paragraph: {
                class: Paragraph,
                config: { placeholder: '내용을 입력하세요.' },
                inlineToolbar: true
            },
            header: { class: Header, inlineToolbar: true },
            image: {
                class: ImageTool,
                config: { uploader: { uploadByFile: uploadFile } }
            },
            attaches: {
                class: AttachesTool,
                config: { uploader: { uploadByFile: uploadFile } }
            },
            checklist: Checklist,
            code: CodeTool,
            raw: RawTool,
            marker: Marker,
            quote: Quote,
            table: Table,
            list: NestedList
        }
    });

    // 폼 제출 이벤트
    $("#postForm").on("submit", async function(e) {
        e.preventDefault();
        try {
            const savedData = await editor.save();
            $("#content").val(JSON.stringify(savedData.blocks));
            this.submit();
        } catch (err) {
            console.error("EditorJS save error:", err);
        }
    });

    // 파일 업로드 함수
    async function uploadFile(file) {
        const formData = new FormData();
        formData.append("file", file);
        try {
            const { data } = await axios.post("/file/upload", formData, { withCredentials: true });
            return { success: data.success, file: { url: data.file.url } };
        } catch (error) {
            console.error("File upload error:", error);
            return { success: 0, file: { url: "" } };
        }
    }

    // 버튼 이벤트
    window.saveEvent = function() {
        $("#postForm").trigger("submit");
    };
    window.editEvent = function() {
        editor.readOnly.toggle();
    };
    window.clearEvent = function() {
        editor.clear();
    };
});
