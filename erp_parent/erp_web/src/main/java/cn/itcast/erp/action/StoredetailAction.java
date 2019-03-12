package cn.itcast.erp.action;
import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.entity.StoreAlert;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.exception.ErpException;
import com.alibaba.fastjson.JSON;

import javax.mail.MessagingException;
import java.util.List;

/**
 * 仓库库存Action 
 * @author Administrator
 *
 */
public class StoredetailAction extends BaseAction<Storedetail> {

	private IStoredetailBiz storedetailBiz;

	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
		super.setBaseBiz(this.storedetailBiz);
	}

	//获取需要预警的商品，并写到页面上去
	public void storeAlertList(){
		List<StoreAlert> storeAlertList = storedetailBiz.getStoreAlertList();
		String jsonString = JSON.toJSONString(storeAlertList);
		write(jsonString);
	}
	//发送库存警告邮件
	public void sendStorealertMail(){
		try {
			storedetailBiz.sendStoreAlertMail();
			ajaxReturn(true,"预警邮件发送成功");
		} catch (MessagingException e) {
			ajaxReturn(false,"预警邮件构建失败");
			e.printStackTrace();
		} catch (ErpException e){
			ajaxReturn(false,e.getMessage());
			e.getStackTrace();
		} catch (Exception e){
			ajaxReturn(false,"预警邮件发送失败");
			e.getStackTrace();
		}
	}


}
