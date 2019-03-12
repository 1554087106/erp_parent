package cn.itcast.erp.biz;
import java.util.List;

import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;

/**
 * 员工业务逻辑层接口
 * @author Administrator
 *
 */
public interface IEmpBiz extends IBaseBiz<Emp>{

	/**
	 * 用户登陆
	 * @param username
	 * @param pwd
	 * @return
	 */
	Emp findByUsernameAndPwd(String username, String pwd);
	
	/**
	 * 修改密码
	 */
	void updatePwd(Long uuid, String oldPwd, String newPwd);
	
	/**
	 * 重置密码
	 */
	void updatePwd_reset(Long uuid, String newPwd);

	/**
	 * @Author whz
	 * @Description //TODO 读取用户角色
	 * @Date 2019/2/12
	 * @Param [uuid 用户(员工)的编号]
	 * @return java.util.List<Tree>
	 **/
	List<Tree> readEmpRoles(Long uuid);
	/**
	 * @Author whz
	 * @Description //TODO 更新用户角色
	 * @Date 2019/2/12
	 * @Param [uuid 用户编号 , checkedStr 用户角色(串)]
	 * @return void
	 **/
	void updateEmpRoles(Long uuid,String checkedStr);
}

