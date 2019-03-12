package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;

import java.util.List;

/**
 * 角色业务逻辑层接口
 * @author Administrator
 *
 */
public interface IRoleBiz extends IBaseBiz<Role>{
    /**
     * @Author whz
     * @Description //TODO 读取角色菜单
     *  uuid为角色名称
     * @Date 2019/2/11
     * @Param []
     * @return java.util.List<cn.itcast.erp.entity.Tree>
     **/
    public List<Tree> readRoleMenus(Long uuid);
    /**
     * @Author whz
     * @Description //TODO 更新角色权限设置
     * @Date 2019/2/11
     * @Param [uuid 角色编号, checkStr 勾选中的菜单ID字符串，以逗号分隔]
     * @return void
     **/
    public void updateRoleMenus(Long uuid,String checkStr);

}

