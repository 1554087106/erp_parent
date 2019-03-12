package cn.itcast.erp.job;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.entity.StoreAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

/**
 * @ClassName MailJob
 * @Description TODD 后台定时检测库存预警
 * @Auther whz
 * @Date 2019/1/21 18:38
 * @Version 1.0
 **/

public class MailJob {
    //库存详情的Biz
    @Autowired
    private IStoredetailBiz storedetailBiz;
    /**
     * @Author whz
     * @Description //TODO 定时发送预警邮件
     * @Date 2019/1/22
     * @Param []
     * @return void
     **/
    public void sendStorealertMail(){
        //查询是否存在库存预警
        List<StoreAlert> storeAlertList = storedetailBiz.getStoreAlertList();
        if(null != storeAlertList && storeAlertList.size() > 0){
            try {
                //发送预警邮件
                storedetailBiz.sendStoreAlertMail();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

}