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


// 메모를 불러와서 보여줍니다.
function getMessages() {
    // 1. 기존 메모 내용을 지웁니다.
    $('#board-table-box').empty();
    // 2. 메모 목록을 불러와서 HTML로 붙입니다.
    $.ajax({
        type: 'GET',
        url: '/api/boards',
        data: {},
        success: function (response) {
            console.log(response);
            for (let i = 0; i < response.length; i++) {
                let title = response[i]['title'];
                let boardId = response[i]['id'];
                let contents = response[i]['contents'];
                let modifiedAt = response[i]['modifiedAt'];
                let username = response[i]['user']['username'];
                addHTML(title, contents, username, boardId, modifiedAt);
            }
        }
    })
}


// 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addHTML(title, contents, username, boardId, modifiedAt) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml = `<tr>
                        <td></td>
                        <td><a href="/api/boards/${boardId}">${title}</a></td>
                        <td>${username}</td>
                        <td>${modifiedAt}</td>
                    </tr>`;
    // 2. #tableBoard 에 HTML을 붙인다.
    $('#board-table-box').append(tempHtml);
}


// 상세페이지로 이동하기
function moveDetail(id) {
    let list_id = id;

    let url = `/detail.html?id=${id}`;
    location.replace(url);
}