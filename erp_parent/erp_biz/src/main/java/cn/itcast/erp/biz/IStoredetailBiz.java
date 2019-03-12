package cn.itcast.erp.biz;
import cn.itcast.erp.entity.StoreAlert;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.util.MailUtil;

import javax.mail.MessagingException;
import java.util.List;

/**
 * 仓库库存业务逻辑层接口
 * @author Administrator
 *
 */
public interface IStoredetailBiz extends IBaseBiz<Storedetail>{

    /**
     * @Author whz
     * @Description //TODO 库存预警列表
     * @Date 2019/1/20
     * @Param []
     * @return java.util.List<cn.itcast.erp.entity.StoreAlert>
     **/
    public List<StoreAlert> getStoreAlertList();




    /**
     * @Author whz
     * @Description //TODO 发送库存警告邮件
     * @Date 2019/1/21
     * @Param []
     * @return void
     **/
    public void sendStoreAlertMail()throws MessagingException;

}

