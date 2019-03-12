package cn.itcast.erp.action;
import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 角色Action 
 * @author Administrator
 *
 */
public class RoleAction extends BaseAction<Role> {

	private IRoleBiz roleBiz;

	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		super.setBaseBiz(this.roleBiz);
	}

	//勾选中的菜单ID，用逗号隔开
	private String checkedStr;

	public String getCheckedStr() {
		return checkedStr;
	}

	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}

	public void readRoleMenus(){
		List<Tree> treeList = roleBiz.readRoleMenus(getId());
		write(JSON.toJSONString(treeList));
	}
	/**
	 * @Author whz
	 * @Description //TODO 更新角色权限
	 * @Date 2019/2/11
	 * @Param []
	 * @return void
	 **/
	public void updateRoleMenus(){
		try{
			roleBiz.updateRoleMenus(getId(),checkedStr);
			ajaxReturn(true,"更新成功");
		}catch (Exception e){
			ajaxReturn(false,"更新失败");
			e.printStackTrace();
		}

	}

}
