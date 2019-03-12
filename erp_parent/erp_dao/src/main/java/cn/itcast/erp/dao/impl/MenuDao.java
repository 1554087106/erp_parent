package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.entity.Menu;

import java.util.List;

/**
 * 菜单数据访问类
 * @author Administrator
 *
 */
public class MenuDao extends BaseDao<Menu> implements IMenuDao {

	/**
	 * @Author whz
	 * @Description //TODO 
	 * @Date 2019/2/12 
	 * @Param [menu1, menu2, param]
	 * @return org.hibernate.criterion.DetachedCriteria
	 **/
	public DetachedCriteria getDetachedCriteria(Menu menu1,Menu menu2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Menu.class);
		return dc;
	}

	@Override
	public List<Menu> getMenusByEmpuuid(Long uuid) {
		String hql="select m from Emp e join e.roles r join r.menus m where e.uuid =?";
		return (List<Menu>) this.getHibernateTemplate().find(hql,uuid);
	}
}
