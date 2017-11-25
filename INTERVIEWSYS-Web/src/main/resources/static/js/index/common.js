function _dopost(url,id){
    $.ajax({
        url:url,
        type:"post",
        success:function(){

        }
    })
}
function _delete(url){
        alert("123");
        layer.confirm("您确定要删除吗？", function (index) {
            _dopost(url);
        });
}