package cn.itcast.erp.dao;

import cn.itcast.erp.entity.Menu;

import java.util.List;

/**
 * 菜单数据访问接口
 * @author Administrator
 *
 */
public interface IMenuDao extends IBaseDao<Menu>{

    /**
     * @Author whz
     * @Description //TODO 通过员工编号获取菜单
     * @Date 2019/2/12
     * @Param [uuid 员工编号]
     * @return java.util.List<cn.itcast.erp.entity.Menu>
     **/
    List<Menu> getMenusByEmpuuid(Long uuid);

}
