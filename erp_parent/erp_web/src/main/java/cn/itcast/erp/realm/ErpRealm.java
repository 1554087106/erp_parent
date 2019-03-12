package cn.itcast.erp.realm;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * @Author whz
 * @Description //TODO 自定义realm realm实质上是一个安全相关的DAO，它封装了数据源的连接细节
 *  *                   可以直接继承AuthorizingRealm实现 doGetAuthorizationInfo(获取授权信息)
 *  *                    doGetAuthenticationInfo(获取认证信息)
 * @Date 2019/2/19
 * @Param
 * @return
 **/
public class ErpRealm extends AuthorizingRealm {

    private IEmpBiz empBiz;

    public void setEmpBiz(IEmpBiz empBiz) {
        this.empBiz = empBiz;
    }

    private IMenuBiz menuBiz;

    public void setMenuBiz(IMenuBiz menuBiz) {
        this.menuBiz = menuBiz;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //System.out.println("授权~~~~~~~~~~~~~~~~~~~");
        Emp emp = (Emp) principals.getPrimaryPrincipal();
        //获取登录用户所对应的菜单权限集合
        Long empUuid = emp.getUuid();
        List<Menu> menus = menuBiz.getMenuByEmpuuid(empUuid);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(Menu menu:menus){
            info.addStringPermission(menu.getMenuname());
        }
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //System.out.println("认证~~~~~~~~~~~~~~~~~~~")
        //通过令牌得到用户名和密码
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        //得到密码
        StringBuffer stringBuffer=new StringBuffer();
        for (char c : upt.getPassword()) {
            stringBuffer.append(c);
        }
        ;
        String pwd=stringBuffer.toString();
        Emp emp = empBiz.findByUsernameAndPwd(upt.getUsername(), pwd);
        if(null != emp ){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(emp, pwd, getName());
            return info;
        }

        return null;
    }
}