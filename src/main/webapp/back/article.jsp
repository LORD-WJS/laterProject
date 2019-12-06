<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>

    $(function () {
        $("#table").jqGrid(
            {
                url :"${pageContext.request.contextPath}/article/showByPage",
                datatype : "json",
                height : 400,
                colNames : [ 'id', '文章名',"图片","内容","上传时间","发布时间","状态","上师ID","操作" ],
                colModel : [
                    {name : 'id',},
                    {name : 'name',align:"center",editable:true,editrules:{required:true}},
                    {name : 'picpath',align:"center",formatter: function (value, option, rows) {
                            return "<img  style='width:100%;height:10s%;' " +
                                "src='" + rows.picpath + "'/>";
                        }},
                    {name : 'content', width:300,align:"center",editable:true,editoptions:{required:true}},
                    {name : 'createDate',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'publishDate',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'status',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'masterId',align:"center",editable:true,editoptions:{required:true}},
                    {
                        name: "id", align: "center",width:"300px" ,formatter: function (value, option, rows) {
                            var result = "";
                            result += "<button class=\"btn btn-info\" onclick=\"editRow('" + rows.id + "');\"><span class='glyphicon glyphicon-th-list'></span></button>&nbsp;&nbsp;";
                            result += "<button class=\"btn btn-danger\" onclick=\"delRow('" + rows.id + "');\">删除</button>&nbsp;&nbsp;";

                            return result;
                        }
                    },
                ],
                rowNum : 5,
                rowList : [5, 8, 10, 20 ],
                pager : '#page',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                multiselect : false,
                // 开启多级表格支持
                subGrid : false,//多级表格
                caption : "",//表格左上角的名
                autowidth:true,
                styleUI:"Bootstrap",
                editurl:"${pageContext.request.contextPath}/article/option",
            }).navGrid('#page', {edit: false, add: false, del: true, search: false,
            edittext:"编辑",addtext:"添加",deltext:"删除"});
    });
    //

    function editRow(id) {
        // 返回指定行的数据，返回数据类型为name:value，name为colModel中的名称，value为所在行的列的值，如果根据rowid找不到则返回空。在编辑模式下不能用此方法来获取数据，它得到的并不是编辑后的值
        var data = $("#table").jqGrid("getRowData",id);
        $("#name").val(data.name);
        //$("#status").val(data.status);
        $("#id").val(id);
        // KindEditor 中的赋值方法 参数1: kindeditor文本框 的id
        KindEditor.html("#editor_id",data.content);

        getMasterList();
        $("#kind").modal("show");
    }
    //打开添加模态框
    function addRow(data) {
        // 清除表单内数据
        $("#kindfrm")[0].reset();
        // kindeditor 提供的数据回显方法  通过"" 将内容设置为空串
        KindEditor.html("#editor_id", "");
        getMasterList();
        $("#kind").modal("show");
    }
    //删除指定行数据
    function delRow(id) {
        $.post("${pageContext.request.contextPath}/article/dropArticle",{id:id},function () {
            $("#table").trigger("reloadGrid");
        });
    }




</script>

<div class="col-sm-10">
    <div class="page-header">
        <h2><strong>文章管理</strong></h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a>文章列表</a></li>
        <li onclick="addRow()"><a>添加文章</a></li>
    </ul>
    <div class="panel">
        <table id="table"></table>
        <div style="height: 50px" id="page"></div>
    </div>

</div>

<div class="col-sm-10" style="text-align: center;">
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <audio src="" id="myaudio" controls class="col-sm-10 col-sm-offset-1" style="position:fixed;bottom:0;left:0;" ></audio>
    </div>
</div>

<!-- KindEditor模态框 -->
<div class="modal fade" id="kind" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 750px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">文章信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="kindfrm" method="post" class="form-horizontal">
                    <div class="form-group">
                        <input type="hidden" name="id" id="id" class="form-control">
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-5">
                            <input type="text" name="name" id="name" placeholder="请输入标题" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">上师ID</label>
                        <div class="col-sm-5">
                            <input type="text" name="masterId" id="masterId"  placeholder="请输入上师ID" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="status" id="status">
                                <option value="展示">展示</option>
                                <option value="不展示">不展示</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="form-group">
                            <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button"  class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" onclick="addArticle()" class="btn btn-primary">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>