package cn.itcast.erp.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 角色实体类
 * @author Administrator *
 */
public class Role {	
	private Long uuid;//编号
	private String name;//名称
	//在序列化时忽略该字段
	@JSONField(serialize = false)
	private List<Menu> menus;//权限菜单

	@JSONField(serialize = false)
	private List<Emp> empList;

	public List<Emp> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Emp> empList) {
		this.empList = empList;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getName() {		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
