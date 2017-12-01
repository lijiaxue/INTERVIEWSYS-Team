function _dopost(url){
        $.ajax({
          type: 'POST',
          url: url,
          dataType:'json',
          success:function(data){
          if(data.status==1){
               layer.msg("删除成功");
               setInterval(location.reload(),200);
          }else{
               layer.msg("删除失败");
          }


          }
        });
}
function _delete(url){
        alert(url);
        layer.confirm("您确定要删除吗？", function (index) {
            _dopost(url);
        });
}

function edit(url){
        layer.open({
            type: 2,
            title: '编辑',
            area : ['800px' , '620px'],
            scrollbar: false,//禁止浏览器滚动
            content: url,
            yes:function(index){

            }
            });
}