package cn.itcast.erp.biz.impl;
import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色业务逻辑类
 * @author Administrator
 *
 */
public class RoleBiz extends BaseBiz<Role> implements IRoleBiz {

	//角色的DAO
	private IRoleDao roleDao;
	//菜单的DAO
	private IMenuDao menuDao;
	//Jedis
	private Jedis jedis;

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		super.setBaseDao(this.roleDao);
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public List<Tree> readRoleMenus(Long uuid) {
		List<Tree> treeList = new ArrayList<>();
		Menu root = menuDao.get("0");
		//获取角色对应的权限菜单列表
		Role role = roleDao.get(uuid);
		List<Menu> roleMenus = role.getMenus();
		//		.getMenus();
		//构建tree集合数据
		Tree t1 = null;
		Tree t2 = null;
		//把菜单转换成树形结构数据
		for(Menu m1 : root.getMenus() ){
			//一级菜单
			t1 = new Tree();
			t1.setId(m1.getMENUID());
			t1.setText(m1.getMenuname());
			for (Menu m2 : m1.getMenus()){
				//二级菜单
				t2 = new Tree();
				t2.setId(m2.getMENUID());
				t2.setText(m2.getMenuname());
				if (roleMenus.contains(m2)){
					t2.setChecked(true);
				}
				t1.getChildren().add(t2);
			}
			treeList.add(t1);
		}
		return treeList;
	}

	@Override
	public void updateRoleMenus(Long uuid, String checkStr) {
		Role role = roleDao.get(uuid);
		//清空角色下的菜单权限
		role.setMenus(new ArrayList<Menu>());
		//获取菜单ID数组
		String[] ids = checkStr.split(",");

		Menu menu=null;
		for(String id:ids){
			menu = menuDao.get(id);
			//往中间表添加数据
			role.getMenus().add(menu);
		}
		try {
			for(Emp emp : role.getEmpList()){
				jedis.del("menuList_" + emp.getUuid());
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
