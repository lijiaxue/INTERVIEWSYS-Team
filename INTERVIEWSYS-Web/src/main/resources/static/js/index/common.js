
function checkeds(){
    $("input[name='ckb']:checkbox").click(function() {
    var str = "";
    $("input[name='ckb']:checkbox").each(function() {
    if($(this).is(":checked"))
    {
    str += $(this).attr("value")+"#";
    }
    });
    if(str != null && str.length > 1)
    {
    str = str.substring(0,str.length-1);
    }
    return str;
    });
}

function _dopost(url,datas){
        $.ajax({
          type: 'POST',
          url: url,
          data:{id:""+datas+""} ,
          dataType:'json',
          success:function(data){
          if(data.status==1){
               layer.msg("删除成功");
               setTimeout(function(){location.reload();},1000);
          }else{
               layer.msg("删除失败");
          }


          }
        });
}



function _delete(url){
        layer.confirm("您确定要删除吗？", function (index) {
            _dopost(url,0);
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
function bathDel(url){
        layer.confirm("您确定要删除选中？", function (index) {
            _dopost(url,str);
        });
}

