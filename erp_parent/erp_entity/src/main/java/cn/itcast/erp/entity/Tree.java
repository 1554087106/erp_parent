package cn.itcast.erp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Tree
 * @Description TODD 构建easyui tree 内容
 * @Auther whz
 * @Date 2019/2/8 15:09
 * @Version 1.0
 **/
public class Tree {
    //菜单ID
    private String id;
    //菜单名称
    private String text;
    //该节点是否为选中
    private boolean checked;
    //一个节点数组声明了若干节点，下级菜单
    private List<Tree> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Tree> getChildren() {
        if( null == children ){
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

}