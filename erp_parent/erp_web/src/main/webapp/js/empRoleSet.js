$(function () {
    $('#tree').tree({
        url:'emp_readEmpRoles?id='+0,//数据来源
        //动画效果
        animate:true,
        //是否显示复选框
        ckeckbox:true
    });
    $('#grid').datagrid({
        url:'emp_list',
        columns:[[
            {field:'uuid',title:'编号',width:100},
            {field:'name',title:'名称',width:100}
        ]],
        singleSelect:true,
        onClickRow:function (rowIndex, rowData) {
            $('#tree').tree({
                url:'emp_readEmpRoles?id='+rowData.uuid,//数据来源
                animate:true,//是否显示为动画效果
                checkbox:true//是否显示复选框
            });
        }
    });
    $('#btnSave').bind('click',function () {
        //得到所有勾选中的节点
        var nodes = $('#tree').tree('getChecked');
        //拼接每个节点ID，即为menuid
        var checkedStr = new Array();
        $.each(nodes,function (i,node) {
            checkedStr.push(node.id);
        });
        /*把数组转换成以逗号分割的字符串*/
        checkedStr = checkedStr.join(',');
        //提交数据
        var formdata = {};
        formdata.id = $('#grid').datagrid('getSelected').uuid;
        formdata.checkedStr = checkedStr;
        $.ajax({
            url:'emp_updateEmpRoles',
            data:formdata,
            type:'post',
            dataType:'json',
            success:function (rtn) {
                $.messager.alert('提示',rtn.message,'info');
            }
        });
    });
});