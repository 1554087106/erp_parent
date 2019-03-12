package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Menu;

import java.util.List;

/**
 * 菜单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IMenuBiz extends IBaseBiz<Menu>{

    /**
     * @Author whz
     * @Description //TODO 根据员工编号获取菜单
     * @Date 2019/2/12
     * @Param [uuid 员工编号]
     * @return java.util.List<cn.itcast.erp.entity.Menu>
     **/
    List<Menu> getMenuByEmpuuid(Long uuid);
    /**
     * @Author whz
     * @Description //TODO 菜单复制，但不复制子菜单
     * @Date 2019/2/12
     * @Param [src]
     * @return cn.itcast.erp.entity.Menu
     **/
    Menu cloneMenu(Menu src);
    /**
     * @Author whz
     * @Description //TODO 根据员工编号获取菜单
     * @Date 2019/2/12
     * @Param [uuid]
     * @return cn.itcast.erp.entity.Menu
     **/
    Menu readMenusByEmpuuid(Long uuid);
}

