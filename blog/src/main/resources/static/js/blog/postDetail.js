$( () => {

    $("#deletePost").on("click",()=>{
        if(!confirm("정말 삭제하시겠습니까?")){
            return false;
        }
        const postNo = new URLSearchParams(window.location.search).get('no');
        //url에 있는 no 값을 가져옴.

        $.ajax({
            url: '/deletePost',
            type: 'POST',
            data: {no: postNo},
            success: (response) => {
                if(response === "N"){ //서버 응답값이 1일경우 성공처리
                    alert("삭제되었습니다.");
                    window.location.href = "/blogList";
                }else{
                    alert("삭제 실패");
                }
            }
        })

    })

})
