<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
    <script>

        $(function () {
            $('#tt').jqGrid({
                url: '${pageContext.request.contextPath}/banner/showBanners',
                datatype: 'json',
                styleUI: 'Bootstrap',
                mtype: "post",
                //cellEdit:true,
                colNames: ['编号', '标题','图片名', '图片',"链接", '创建时间', '描述', '状态'],
                collEdit: true,
                colModel: [
                {name: "id", align: 'center',hidden:true},
                {name: 'title', align: 'center', editable: true,editrules:{required:true}},
                {name: 'name', align: 'center', editable: false,},
                {name: 'cover',
                    //index: 'cover',
                    edittype: "file",
                    align:'center',
                    editable:true,
                    editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value, option, rows) {
                        return "<img  style='width:50%;height:10s%;' " +
                            "src='" + rows.cover + "'/>";
                    }
                },
                {name:'href',align: 'center',editable:true},
                {
                    name: 'createDate',
                    align: 'center',
                    formatter: "date",
                    formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d'//editable: true,
                   }
                },
                {name: 'introduce', align: 'center', editable: true},
                {
                    name: 'status', align: 'center', editable: true, edittype: "select",
                    editoptions: {value: "正常:正常;冻结:冻结"}
                },

                //格式化参考：http://www.cnblogs.com/hxling/archive/2010/10/10/1847334.html
            ],
            pager: '#pager',
                autowidth: true,
                height: '40%',
                rowNum: 3,
                rowList: [2, 3, 4, 5],
                caption: "轮播图详细信息",
                multiselect:true,
                editurl: '${pageContext.request.contextPath}/banner/edit',//设置编辑表单提交路径
                viewrecords: true,
            //recreateForm: true确保每添加或编辑表单是重新创建。
        }).navGrid('#pager', {edit: true, add: true, del: true, search: false,
                    edittext:"编辑",addtext:"添加",deltext:"删除"},

                {
                    jqModal: true, closeAfterEdit: true, recreateForm: true, onInitializeForm: function (formid) {
                        $(formid).attr('method', 'POST');
                        $(formid).attr('action', '${pageContext.request.contextPath}/banner/upload');
                        $(formid).attr('enctype', 'multipart/form-data');
                    },
                    afterSubmit: function (response) {
                        alert("修改函数");
                        var status = response.responseJSON.status;
                        var id = response.responseJSON.message;
                        alert("确认修改")
                        if (status) {
                            $.ajaxFileUpload({
                                url: "${pageContext.request.contextPath}/banner/upload",
                                fileElementId: "cover",
                                data: {id: id},
                                type: "post",
                                success: function () {
                                    $("#tt").trigger("reloadGrid")
                                }
                            });
                            return "true";
                        }
                    }
                },

                {
                    jqModal: true, closeAfterAdd: true, recreateForm: true, onInitializeForm: function (formid) {
                        $(formid).attr('method', 'POST');
                        $(formid).attr('action', '${pageContext.request.contextPath}/banner/upload');
                        $(formid).attr('enctype', 'multipart/form-data');
                    },
                    afterSubmit: function (response) {
                        var status = response.responseJSON.status;
                        var id = response.responseJSON.message;
                        //let id = response.responseJSON.data;
                        alert("确认添加")
                        if (status) {
                            $.ajaxFileUpload({
                                url: "${pageContext.request.contextPath}/banner/upload",
                                fileElementId: "cover",
                                data: {id: id},
                                type: "post",
                                success: function () {
                                    $("#tt").trigger("reloadGrid")
                                }
                            });
                        //需要返回数据才算ajaxfileupload函数执行结束，关闭模态框
                        return "true";;
                        }
                    }
                }
            );
        })

    </script>


<table id="tt"></table>
<div id="pager" style="height: 30px"></div>

