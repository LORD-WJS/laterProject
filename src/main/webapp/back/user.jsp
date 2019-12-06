
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $("#table").jqGrid(
            {
                url :"${pageContext.request.contextPath}/user/showByPage",
                datatype : "json",
                mtype: "post",
                height : 500,
                colNames : [ 'id', '法名','姓名','密码','手机号','头像','性别','地址','签名','创建时间','上次登录时间','状态','操作' ],
                colModel : [
                    {name: "id", align: 'center',},
                    {name: 'username', align: 'center', editable: true,editrules:{required:true}},
                    {name: 'name', align: 'center', editable: true,editrules:{required:true}},
                    {name : 'password',align: 'center', editable: true,},
                    {name : 'phone',align: 'center', editable: true,},
                    {name : 'face', edittype: "file",align:'center',editable:true,
                        editoptions: {enctype: "multipart/form-data"},
                        formatter: function (value, option, rows) {
                            return "<img  style='width:50%;height:10s%;' " +
                                "src='" + rows.face + "'/>";
                        }},
                    {name : 'sex',align: 'center', editable: true,edittype: "select",
                        editoptions: {value: "男:男;女:女"}},
                    {name : 'location',align: 'center', editable: true,},
                    {name : 'signature',align: 'center', editable: true,},
                    {name : 'createDate',align: 'center', formatter: "date",
                        formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d'
                        }},
                    {name : 'lastLoginTime',align: 'center', formatter: "date",
                        formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d'
                        }},
                    {name : 'status',align: 'center', editable: true,edittype: "select",
                        editoptions: {value: "正常:正常;冻结:冻结"}},
                    {
                        name: "id", align: "center",formatter: function (value, option, rows) {
                            var result = "";
                            result += "<button class=\"btn btn-info\" onclick=\"editRow('" + rows.id + "');\"><span class='glyphicon glyphicon-th-list'></span></button>&nbsp;&nbsp;";
                            return result;
                        }
                    },
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
                caption : "用户列表",
                autowidth:true,
                styleUI:"Bootstrap",
                editurl: '${pageContext.request.contextPath}/user/edit',//设置编辑表单提交路径
                // 重写创建子表格方法
                subGridRowExpanded : function(subgrid_id, row_id) {
                    addTable(subgrid_id,row_id);
                },
                // 删除表格方法
                subGridRowColapsed : function(subgrid_id, row_id) {

                }
            }).navGrid('#page', {edit: false, add: true, del: false, search: false,
                edittext:"编辑",addtext:"添加",deltext:"删除"},

            {
                jqModal: true, closeAfterEdit: true, recreateForm: true, onInitializeForm: function (formid) {
                    $(formid).attr('method', 'POST');
                    $(formid).attr('action', '${pageContext.request.contextPath}/user/upload');
                    $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response) {
                    alert("修改函数");
                    var status = response.responseJSON.status;
                    var id = response.responseJSON.message;
                    alert("确认修改")
                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/user/upload",
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
                    $(formid).attr('action', '${pageContext.request.contextPath}/user/upload');
                    $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response) {
                    var status = response.responseJSON.status;
                    var id = response.responseJSON.message;
                    //let id = response.responseJSON.data;
                    alert("确认添加")
                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/user/upload",
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
    function editRow(id) {
        // 返回指定行的数据，返回数据类型为name:value，name为colModel中的名称，value为所在行的列的值，如果根据rowid找不到则返回空。在编辑模式下不能用此方法来获取数据，它得到的并不是编辑后的值
        var data = $("#table").jqGrid("getRowData",id);
        var status=data.status;
        if(status=="正常") status="冻结";
        else status="正常";
        $.post("${pageContext.request.contextPath}/user/edit",{id:id,status:status,oper:"edit"},function (result) {
            $("#table").trigger("reloadGrid")},"json");
    }
</script>
<table id="table"></table>
<div style="height: 50px" id="page"></div>
