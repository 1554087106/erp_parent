package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Role;

public class RoleDao extends BaseDao<Role> implements IRoleDao {

	/**
	 * @Author whz
	 * @Description //TODO 构建查询条件
	 * @Date 2019/2/14
	 * @Param [role1, role2, param]
	 * @return org.hibernate.criterion.DetachedCriteria
	 **/
	public DetachedCriteria getDetachedCriteria(Role role1,Role role2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Role.class);
		if(role1!=null){
			if(null != role1.getName() && role1.getName().trim().length()>0){
				dc.add(Restrictions.like("name", role1.getName(), MatchMode.ANYWHERE));
			}

		}
		return dc;
	}

}
