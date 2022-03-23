$("#document").ready(function () {
    getMessages();
});

let count = 0;
function openCloseInputBox() {
    count++;
    if(count % 2 != 0) {
        $("#input-box").show();
        console.log("열기완료!")
        $(".btn__active button").text("게시글 등록창 닫기");
    } else {
        $("#input-box").hide();
        console.log("닫기완료!")
        $(".btn__active button").text("게시글 등록창 열기");
    }

}

// 미리 작성된 영역 - 수정하지 않으셔도 됩니다.
// 사용자가 내용을 올바르게 입력하였는지 확인합니다.
function isValidContents(contents) {
    if (contents == "") {
        alert("내용을 입력해주세요");
        return false;
    }
    if (contents.trim().length > 140) {
        alert("공백 포함 140자 이하로 입력해주세요");
        return false;
    }
    return true;
}

// 메모를 생성합니다.
function writePost() {
    // 1. 작성한 메모를 불러옵니다.
    let author = $("#author").val();
    let title = $("#title").val();
    let contents = $("#contents").val();

    // 2. 작성한 메모가 올바른지 isValidContents 함수를 통해 확인합니다.
    if (isValidContents(title) == false) {
        return;
    } else if (isValidContents(author) == false) {
        return;
    } else if (isValidContents(contents) == false) {
        return;
    }
    // 3. 전달할 data JSON으로 만듭니다.
    let data = { author: author, title: title, contents: contents };

    // 5. POST /api/memos 에 data를 전달합니다.
    $.ajax({
        type: "POST",
        url: "/api/boards",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert("메시지가 성공적으로 작성되었습니다.");
            window.location.reload();
        },
    });
}
// 메모를 불러와서 보여줍니다.
function getMessages() {
    // 1. 기존 메모 내용을 지웁니다.
    $('#board-list-box').empty();
    // 2. 메모 목록을 불러와서 HTML로 붙입니다.
    $.ajax({
        type: 'GET',
        url: '/api/boards',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let id = message['id'];
                let author = message['author'];
                let title = message['title'];
                let contents = message['contents'];
                let modifiedAt = message['modifiedAt'];
                addHTML(id, author, title, contents, modifiedAt);
            }
        }
    })
}

// 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addHTML(id, author, title, contents, modifiedAt) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml = `<li class="list-group-item board__item list-${id}">
                <div>작성자 : ${author}</div>
                <div>글제목 : ${title}</div>
                <div>작성날짜 : ${modifiedAt}</div>
                <button onclick="moveDetail(${id})">detail</button>
            </li>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#board-list-box').append(tempHtml);
}

// 상세페이지로 이동하기
function moveDetail(id) {
    let list_id = id;

    let url = `/detail.html?id=${id}`;
    location.replace(url);
}
