//const boardData = [[${boardData}]];
//    const parsedData = JSON.parse(boardData); // JSON 문자열을 객체로 파싱

    const saveEvent = async () => { // async 추가
      try {
        const data = await editor.save(); // await 추가
        if (data.blocks.length > 0) {
          // 비동기 통신으로 내용 저장
          const title = document.getElementById("title").value;

          console.log(data);
          console.log(JSON.stringify(data.blocks));
          editor.readOnly.toggle();

          const formData = new FormData();
          formData.append("title", title);
          formData.append("content", JSON.stringify(data.blocks));

          // axios 요청
          const response = await axios({
            method: 'POST',
            url: 'http://d.0neteam.co.kr:9000/board/upload',
            withCredentials: true,
            data: formData
          });
          console.log(response.data); // 응답 데이터를 출력
          return response.data;
        }
      } catch (error) {
        console.error(error);
      }
    };
    const editEvent = () => {
        editor.readOnly.toggle();
    }
    const clearEvent = () => {
        editor.clear();
    }
  const fileUpload = async (file) => {
    const formData = new FormData()
    formData.append("file", file)

    try {
      const response = await axios({
        method: 'POST',
        url: 'http://d.0neteam.co.kr:9000/file/upload',
        withCredentials: true,
        data: formData
      });
      return response.data;
    } catch (error) {
      console.error(error);
    }

    return new Promise((resolve, reject) => {
      resolve({
        success: 0,
        file: {url: ''}
      })
    });
  }
  const editor = new EditorJS({
    holderId : 'editorjs',
//    data: {blocks: parsedData },
    data: {blocks: [] },
    //readOnly: true, // 상세보기에서 사용하는 옵션 설정
    tools: {
           paragraph: {
           class: Paragraph,
           config: {placeholder: '내용을 입력하세요.'},
           inlineToolbar: true
         },
      header: {
        class: Header,
        inlineToolbar : true
      },
      quote: {
        class: Quote,
        inlineToolbar: true,
        config: {
          quotePlaceholder: 'Enter a quote',
          captionPlaceholder: 'Quote\'s author',
        },
        shortcut: 'CMD+SHIFT+O'
      },
      marker: {
        class: Marker,
        shortcut: 'CMD+SHIFT+M',
      },
      delimiter: Delimiter,
      table: {
        class: Table,
        inlineToolbar: true,
        shortcut: 'CMD+ALT+T'
      },
      list: {
        class: NestedList,
        inlineToolbar: true,
        config: {defaultStyle: 'unordered'},
        shortcut: 'CMD+SHIFT+L'
      },
      raw: RawTool,
      code: CodeTool,
      inlineCode: {
        class: InlineCode,
        shortcut: 'CMD+SHIFT+M',
      },
      checklist: {
        class: Checklist,
        inlineToolbar: true,
      },
      image: {
        class: ImageTool,
        config: {
          uploader: {
              uploadByFile(file){
                return fileUpload(file)
              }
          },
          field: 'image',
          types: 'image/*'
        }
      },
      attaches: {
        class: AttachesTool,
        config: {
          uploader: {
            uploadByFile(file) {
              return fileUpload(file)
            }
          }
        }
      },
    }
  });