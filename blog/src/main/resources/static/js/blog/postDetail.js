$( () => {

    $("#deletePost").on("click",()=>{
        if(!confirm("정말 삭제하시겠습니까?")){
            return false;
        }
        const postNo = new URLSearchParams(window.location.search).get('no');
        //url에 있는 no 값을 가져옴.

        $.ajax({
            url: 'delete',
            type: 'POST',
            data: {no: postNo},
            success: (response) => {
                if(response === "N"){
                    alert("삭제되었습니다.");
                    window.location.href = "/blogList";
                }
            },
            error: (response) => {
                alert("삭제에 실패했습니다.");
            }
        })

    })

})
