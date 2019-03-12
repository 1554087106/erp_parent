package cn.itcast.erp.biz.impl;
import cn.itcast.erp.biz.IReportBiz;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
import org.apache.shiro.crypto.hash.Md5Hash;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.exception.ErpException;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private int hashIterations = 2;
	
	private IEmpDao empDao;
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		super.setBaseDao(this.empDao);
	}
	private IRoleDao roleDao;

	private Jedis jedis;

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * 用户登陆
	 * @param username
	 * @param pwd
	 * @return
	 */
	public Emp findByUsernameAndPwd(String username, String pwd){
		//查询前先加密
		pwd = encrypt(pwd, username);
		System.out.println(pwd);
		return empDao.findByUsernameAndPwd(username, pwd);
	}

	/**
	 * 修改密码
	 */
	public void updatePwd(Long uuid, String oldPwd, String newPwd) {
		//取出员工信息
		Emp emp = empDao.get(uuid);
		//加密旧密码
		String encrypted = encrypt(oldPwd, emp.getUsername());
		//旧密码是否正确的匹配
		if(!encrypted.equals(emp.getPwd())){
			//抛出 自定义异常
			throw new ErpException("旧密码不正确");
		}		
		empDao.updatePwd(uuid, encrypt(newPwd,emp.getUsername()));
	}
	
	/**
	 * 新增员工
	 */
	public void add(Emp emp){
		//String pwd = emp.getPwd();
		// source: 原密码
		// salt:   盐 =》扰乱码
		// hashIterations: 散列次数，加密次数
		//Md5Hash md5 = new Md5Hash(pwd, emp.getUsername(), hashIterations);
		//取出加密后的密码
		//设置初始密码
		String newPwd = encrypt(emp.getUsername(), emp.getUsername());
		//System.out.println(newPwd);
		//设置成加密后的密码
		emp.setPwd(newPwd);
		//保存到数据库中
		super.add(emp);
	}
	
	/**
	 * 重置密码
	 */
	public void updatePwd_reset(Long uuid, String newPwd){
		//取出员工信息
		Emp emp = empDao.get(uuid);
		empDao.updatePwd(uuid, encrypt(newPwd,emp.getUsername()));
	}

	@Override
	public List<Tree> readEmpRoles(Long uuid) {
		List<Tree> treeList = new ArrayList<>();
		//获取用户
		Emp emp = empDao.get(uuid);
		//得到用户下的角色表
		List<Role> empRoles = emp.getRoles();
		//获取所有的角色
		List<Role> roleList = roleDao.getList(null, null, null);

		Tree t1=null;
		for(Role role:roleList){
			t1 = new Tree();
			t1.setId(String.valueOf(role.getUuid()));
			t1.setText(role.getName());
			if(empRoles.contains(role)){
				//如果该用户包含该角色，则让它选中
				t1.setChecked(true);
			}
			treeList.add(t1);
		}
		return treeList;
	}

	@Override
	public void updateEmpRoles(Long uuid, String checkedStr) {
		//获取需要操作的用户
		Emp emp = empDao.get(uuid);
		//清空用户角色
		emp.setRoles(new ArrayList<Role>());
		//得到勾选的角色ID
		String[] ids = checkedStr.split(",");
		Role role=null;
		for(String id:ids){
			//通过ID获取角色
			role = roleDao.get(Long.valueOf(id));
			//给用户添加角色
			emp.getRoles().add(role);
		}
		try {
			//清除缓存，使得再次访问资源时，会重新加载
			jedis.del("menuList_" + uuid );
		}catch (Exception e){
			e.printStackTrace();
		}

	}


	/**
	 * 加密
	 * @param source
	 * @param salt
	 * @return
	 */
	private String encrypt(String source, String salt){
		Md5Hash md5 = new Md5Hash(source, salt, hashIterations);
		return md5.toString();
	}

}
