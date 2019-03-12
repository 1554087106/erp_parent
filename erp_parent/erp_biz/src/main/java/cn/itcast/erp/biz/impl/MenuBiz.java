package cn.itcast.erp.biz.impl;
import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.entity.Menu;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单业务逻辑类
 * @author Administrator
 *
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

	private IMenuDao menuDao;
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		super.setBaseDao(this.menuDao);
	}
	private Jedis jedis;

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	@Override
	//加入redis提高系统性能
	public List<Menu> getMenuByEmpuuid(Long uuid) {
		//从Redis中获取菜单列表
		String menuListJson = jedis.get("menuList_" + uuid);
		List<Menu> menuList = null;
		if(null == menuListJson ){
			menuList = menuDao.getMenusByEmpuuid(uuid);
			jedis.set("menuList_" + uuid , JSON.toJSONString(menuList));
			System.out.println("从数据库中获取菜单列表");
		}else {
			menuList = JSON.parseArray(menuListJson,Menu.class);
			System.out.println("从Redis中获取菜单列表");
		}
		return menuList;
	}

	@Override
	public Menu cloneMenu(Menu src) {
		Menu _new=new Menu();
		_new.setIcon(src.getIcon());
		_new.setMENUID(src.getMENUID());
		_new.setMenuname(src.getMenuname());
		_new.setUrl(src.getUrl());
		_new.setMenus(new ArrayList<Menu>());
		return _new;
	}

	@Override
	public Menu readMenusByEmpuuid(Long uuid) {
		List<Menu> menuList = menuDao.getMenusByEmpuuid(uuid);
		Menu menu = menuDao.get("0");
		//复制根菜单
		Menu tMenu = cloneMenu(menu);
		//复制一级菜单
		Menu _m1 = null;
		Menu _m2 = null;
		for(Menu m1 : menu.getMenus()){
			_m1 = cloneMenu(m1);
			//复制二级菜单
			for(Menu m2: m1.getMenus()){
				//如果用户包含该菜单
				if(menuList.contains(m2)){
					_m2 = cloneMenu(m2);
					_m1.getMenus().add(_m2);
				}
			}
			if(_m1.getMenus().size() > 0 ){
				//如果一级菜单下有二级菜单，则把一级菜单添加到根菜单下
				tMenu.getMenus().add(_m1);
			}
		}
		return tMenu;
	}
}
