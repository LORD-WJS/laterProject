
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        // 创建父级JqGrid表格
        $("#table").jqGrid(
            {
                url :"${pageContext.request.contextPath}/album/showByPage",
                datatype : "json",
                mtype: "post",
                height : 500,
                colNames : [ 'id', '专辑名','封面','星级','作者','诵者','章节数','简介','状态','创建时间' ],
                colModel : [
                    {name: "id", align: 'center',},
                    {name: 'title', align: 'center', editable: true,editrules:{required:true}},
                    {name : 'cover', edittype: "file",align:'center',editable:true,
                        editoptions: {enctype: "multipart/form-data"},
                        formatter: function (value, option, rows) {
                            return "<img  style='width:50%;height:10s%;' " +
                                "src='" + rows.cover + "'/>";
                        }},
                    {name : 'star',align: 'center', editable: true,},
                    {name : 'author',align: 'center', editable: true,},
                    {name : 'announcer',align: 'center', editable: true,},
                    {name : 'chapterNum',align: 'center', editable: true,},
                    {name : 'introduce',align: 'center', editable: true,},
                    {name : 'status',align: 'center', editable: true,edittype: "select",
                        editoptions: {value: "正常:正常;冻结:冻结"}},
                    {name : 'createDate',align: 'center', formatter: "date",
                        formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d'
                        }},
                ],
                rowNum : 8,
                rowList : [ 8, 10, 20, 30 ],
                pager : '#page',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                multiselect : false,
                // 开启多级表格支持
                subGrid : true,
                caption : "专辑列表",
                autowidth:true,
                styleUI:"Bootstrap",
                editurl: '${pageContext.request.contextPath}/album/edit',//设置编辑表单提交路径
                // 重写创建子表格方法
                subGridRowExpanded : function(subgrid_id, row_id) {
                    addTable(subgrid_id,row_id);
                },
                // 删除表格方法
                subGridRowColapsed : function(subgrid_id, row_id) {

                }
            }).navGrid('#page', {edit: true, add: true, del: true, search: false,
                edittext:"编辑",addtext:"添加",deltext:"删除"},

            {
                jqModal: true, closeAfterEdit: true, recreateForm: true, onInitializeForm: function (formid) {
                    $(formid).attr('method', 'POST');
                    $(formid).attr('action', '${pageContext.request.contextPath}/album/upload');
                    $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response) {
                    alert("修改函数");
                    var status = response.responseJSON.status;
                    var id = response.responseJSON.message;
                    alert("确认修改")
                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/album/upload",
                            fileElementId: "cover",
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
                    $(formid).attr('action', '${pageContext.request.contextPath}/album/upload');
                    $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response) {
                    var status = response.responseJSON.status;
                    var id = response.responseJSON.message;
                    //let id = response.responseJSON.data;
                    alert("确认添加")
                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/album/upload",
                            fileElementId: "cover",
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
    // subgrid_id 下方空间的id  row_id 当前行id数据
    function addTable(subgrid_id,row_id) {

        // 声明子表格|工具栏id
        var subgridTable = subgrid_id + "table";
        var subgridPage = subgrid_id + "page";
        // 根据下方空间id 创建表格及工具栏
        $("#"+subgrid_id).html("<table id='"+subgridTable+"'></table><div style='height: 50px' id='"+subgridPage+"'></div>")
        // 子表格JqGrid声明
        $("#"+subgridTable).jqGrid({
            url : "${pageContext.request.contextPath}/chapter/showByPage?albumId="+row_id,
            datatype : "json",
            mtype: "post",
            colNames : [ 'id', '章节名','音频','大小','时长','创建时间','专辑Id' ],
            colModel : [
                {name : "id",  index : "num",key : true,align: 'center',hidden:true},
                {name : "title",index : "item",align: 'center', editable: true,},
                {name : "audio", edittype: "file",align:'center',editable:true,width:500,
                    editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value, option, rows) {
                        return "<audio controls='controls'  style='width:50%;height:10s%;' " +
                            "src='" + rows.audio + "'/>";
                    }},
                {name : "audioSize",index : "item",align: 'center', },
                {name : "time",index : "item",align: 'center',},
                {name : "createDate",index : "item",align: 'center',formatter: "date",
                    formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d'}},
                {name : "albumId",index : "item",align: 'center',},
            ],
            rowNum : 5,
            pager : "#"+subgridPage,
            sortname : 'num',
            sortorder : "asc",
            height : '100%',
            styleUI:"Bootstrap",
            autowidth:true,
            editurl: '${pageContext.request.contextPath}/chapter/edit?albumId='+row_id,//设置编辑表单提交路径
            viewrecords: true,
        }).navGrid('#'+subgridPage, {edit: true, add: true, del: true, search: false,
                edittext:"编辑",addtext:"添加",deltext:"删除"},

            {
                jqModal: true, closeAfterEdit: true, recreateForm: true, onInitializeForm: function (formid) {
                    $(formid).attr('method', 'POST');
                    $(formid).attr('action', '${pageContext.request.contextPath}/chapter/upload');
                    $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response) {
                    alert("修改函数");
                    var status = response.responseJSON.status;
                    var id = response.responseJSON.message;
                    alert("确认修改")
                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/chapter/upload",
                            fileElementId: "audio",
                            data: {id: id},
                            type: "post",
                            success: function () {
                                $("#"+subgridTable).trigger("reloadGrid")
                            }
                        });
                        return "true";
                    }
                }
            },

            {
                jqModal: true, closeAfterAdd: true, recreateForm: true, onInitializeForm: function (formid) {
                    $(formid).attr('method', 'POST');
                    $(formid).attr('action', '${pageContext.request.contextPath}/chapter/upload');
                    $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response) {
                    var status = response.responseJSON.status;
                    var id = response.responseJSON.message;
                    //let id = response.responseJSON.data;
                    alert("确认添加")
                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/chapter/upload",
                            fileElementId: "audio",
                            data: {id: id},
                            type: "post",
                            success: function () {
                                $("#"+subgridTable).trigger("reloadGrid")
                            }
                        });
                        //需要返回数据才算ajaxfileupload函数执行结束，关闭模态框
                        return "true";
                    }
                }
            }
        );
    }

</script>
<table id="table"></table>
<div style="height: 50px" id="page"></div>
