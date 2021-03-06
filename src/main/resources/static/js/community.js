

//提交回复
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}

function comment2target(targetId,type,content) {
    if(!content){
        alert("请输入回复内容!");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success:function (response) {
            if(response.code==200){
                window.location.reload();
            }else {
                if (response.code==2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=d9578dd5d74eea4e62c3&redirect_uri=http://localhost:8011/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType:"json"
    });
    
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}


function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);

    if(comments.hasClass("in")){
        comments.removeClass("in");
        e.classList.remove("active");
    }else{
          var subCommentContainer = $("#comment-"+id);

          if(subCommentContainer.children().length!=1){
              comments.addClass("in");
              e.classList.add("active");
          }else{
              $.getJSON( "/comment/"+id, function( data ) {
            $.each( data.data.reverse(), function(index,comment) {
                var mediaLeftElement=$("<div/>",{
                    "class":"media-left"
                }).append($("<img/>", {
                    "class": "media-object picture img-rounded",
                    "src": comment.user.avatarUrl
                }));

                var mediaBodyElement=$("<div/>",{
                    "class":"media-body"
                }).append($("<span/>", {
                    "html": comment.user.name
                })).append($("<div/>", {
                    "html": comment.content
                })).append($("<span/>", {
                    "class":"pull-right",
                    "html":moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')
                }));

                var mediaElement=$("<div/>",{
                    "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12"
                }).append(mediaLeftElement).append(mediaBodyElement);

                var commentElement=$("<div/>",{
                    "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-style"
                }).append(mediaElement);

                subCommentContainer.prepend(commentElement);
            });
            //展开二级评论
        comments.addClass("in");
        e.classList.add("active");
        });
    }
    }

}

function selectTag(e) {
    var previous = $("#tag").val();
    var value = e.getAttribute("data-tag");
    if(previous.indexOf(value) == -1){
        if(previous){
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value);
        }
    }
}

function showSelectTag() {
     $("#showTag").show();
}