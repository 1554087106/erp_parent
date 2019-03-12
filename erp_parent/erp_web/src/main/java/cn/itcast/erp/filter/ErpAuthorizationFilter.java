package cn.itcast.erp.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @ClassName ErpAuthorizationFilter
 * @Description TODD 自定义授权过滤器
 *                      用户仅具有某项功能所有权限中的一个时，
 *                      用户就可以访问
 * @Auther whz
 * @Date 2019/2/26 20:38
 * @Version 1.0
 **/
public class ErpAuthorizationFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //1.获取主题
        Subject subject = getSubject(request, response);
        //2.得到配置文件中权限列表
        String[] perms= (String[]) mappedValue;
        //3.如果为空或长度为0，则放行
        if(null == perms || perms.length == 0){
            return true;
        }
        //4.权限检查
        for(String p : perms){
            //只要有一个包含该权限即放行
            if(subject.isPermitted(p)){
                return true;
            }
        }
        return false;
    }
}