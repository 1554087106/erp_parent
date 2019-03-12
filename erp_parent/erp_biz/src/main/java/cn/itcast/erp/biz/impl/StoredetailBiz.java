package cn.itcast.erp.biz.impl;
import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.entity.StoreAlert;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.exception.ErpException;
import cn.itcast.erp.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 仓库库存业务逻辑类
 * @author Administrator
 *
 */
@Service("storedetailBiz")
public class StoredetailBiz extends BaseBiz<Storedetail> implements IStoredetailBiz {

	//库存详情的DAO
	@Autowired
	private IStoredetailDao storedetailDao;
	//发送邮件工具类
	@Autowired
	private MailUtil mailUtil;
	//收信人
	@Value("1554087106@qq.com")
	private String to;
	//邮件主题
	@Value("库存预警_时间:[time]")
	private String subject;
	//邮件正文
	@Value("有[count]中商品已经库存不足，请登录蓝云ERP查看")
	private String text;

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
		super.setBaseDao(this.storedetailDao);
	}

	@Override
	public List<StoreAlert> getStoreAlertList() {
		return storedetailDao.getStoreAlertList();
	}

	@Override
	public void sendStoreAlertMail() throws MessagingException {
		//获取需要预警的商品
		List<StoreAlert> storeAlertList = storedetailDao.getStoreAlertList();
		//判断是否有需要预警的商品
		if( null != storeAlertList && storeAlertList.size() >0 ){
			StringBuffer sb=new StringBuffer();
			for (StoreAlert storeAlert:storeAlertList){
				sb.append(storeAlert.getName()+",");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			//发送邮件
			mailUtil.sendMail(to,subject.replace("[time]",
					sdf.format(new Date())),text.replace("[count]",String.valueOf(sb.toString())));
		}else {
			throw new ErpException("没有需要预警的商品!");
		}
	}
}
