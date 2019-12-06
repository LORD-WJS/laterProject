
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#table").jqGrid(
            {
                url :"${pageContext.request.contextPath}/master/showByPage",
                datatype : "json",
                mtype: "post",
                height : 500,
                colNames : [ 'id', '上师名','头像','法名','状态','创建时间' ],
                colModel : [
                    {name: "id", align: 'center',},
                    {name: 'realname', align: 'center', editable: true,editrules:{required:true}},
                    {name : 'face', edittype: "file",align:'center',editable:true,
                        editoptions: {enctype: "multipart/form-data"},
                        formatter: function (value, option, rows) {
                            return "<img  style='width:50%;height:10s%;' " +
                                "src='" + rows.face + "'/>";
                        }},
                    {name : 'nickName',align: 'center', editable: true,},
                    {name : 'status',align: 'center', editable: true,edittype: "select",
                        editoptions: {value: "正常:正常;冻结:冻结"}},
                    {name : 'createDate',align: 'center', },
                ],
                rowNum : 8,
                rowList : [ 8, 10, 20, 30 ],
                pager : '#page',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                multiselect : false,
                // 开启多级表格支持
                subGrid : false,
                caption : "专辑列表",
                autowidth:true,
                styleUI:"Bootstrap",
                editurl: '${pageContext.request.contextPath}/master/edit',//设置编辑表单提交路径
            }).navGrid('#page', {edit: true, add: true, del: true, search: false,
                edittext:"编辑",addtext:"添加",deltext:"删除"},

            {
                jqModal: true, closeAfterEdit: true, recreateForm: true, onInitializeForm: function (formid) {
                    $(formid).attr('method', 'POST');
                    $(formid).attr('action', '${pageContext.request.contextPath}/master/upload');
                    $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response) {
                    alert("修改函数");
                    var status = response.responseJSON.status;
                    var id = response.responseJSON.message;
                    alert("确认修改")
                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/master/upload",
                            fileElementId: "face",
                            data: {id: id},
                            type: "post",
                            success: function () {
                                $("#table").trigger("reloadGrid")
                            }
                        });
                        return "true";
                    }
                }
            },

            {
                jqModal: true, closeAfterAdd: true, recreateForm: true, onInitializeForm: function (formid) {
                    $(formid).attr('method', 'POST');
                    $(formid).attr('action', '${pageContext.request.contextPath}/master/upload');
                    $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response) {
                    var status = response.responseJSON.status;
                    var id = response.responseJSON.message;
                    //let id = response.responseJSON.data;
                    alert("确认添加")
                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/master/upload",
                            fileElementId: "face",
                            data: {id: id},
                            type: "post",
                            success: function () {
                                $("#table").trigger("reloadGrid")
                            }
                        });
                        //需要返回数据才算ajaxfileupload函数执行结束，关闭模态框
                        return "true";
                    }
                }
            }
        );
    })

</script>
<table id="table"></table>
<div style="height: 50px" id="page"></div>
