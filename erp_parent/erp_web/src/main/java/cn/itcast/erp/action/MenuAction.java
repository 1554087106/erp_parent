package cn.itcast.erp.action;
import cn.itcast.erp.entity.Emp;
import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.entity.Menu;

import java.util.List;

/**
 * 菜单Action 
 * @author Administrator
 *
 */
public class MenuAction extends BaseAction<Menu> {

	private IMenuBiz menuBiz;

	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		super.setBaseBiz(this.menuBiz);
	}
	
	/**
	 * 获取菜单数据
	 */
	public void getMenuTree(){
		//通过获取主菜单，自关联就会带其下所有的菜单
		Menu menu = menuBiz.readMenusByEmpuuid(getLoginUser().getUuid());
		//Menu menu = menuBiz.get("0");
		write(JSON.toJSONString(menu));
	}
	/**
	 * @Author whz
	 * @Description //TODO 根据员工编号获取对应菜单
	 * @Date 2019/2/12
	 * @Param []
	 * @return void
	 **/
	public void getMenusByEmpuuid(){
		Emp emp = getLoginUser();
		List<Menu> menuList = menuBiz.getMenuByEmpuuid(emp.getUuid());
		write(JSON.toJSONString(menuList));
	}

}
