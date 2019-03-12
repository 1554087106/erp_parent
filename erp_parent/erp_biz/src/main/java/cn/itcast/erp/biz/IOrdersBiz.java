package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Orders;

import java.io.OutputStream;

/**
 * 订单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrdersBiz extends IBaseBiz<Orders>{

	/**
	 * 审核
	 * @param uuid 订单编号
	 * @param empUuid 审核员
	 */
	void doCheck(Long uuid, Long empUuid);
	
	/**
	 * 确认
	 * @param uuid 订单编号
	 * @param empUuid 采购员
	 */
	void doStart(Long uuid, Long empUuid);

	/**
	 * @Author whz
	 * @Description //TODO 导出订单excel文件
	 * @Date 2019/1/23
	 * @Param [os 输出流, uuid 订单编号]
	 * @return void
	 **/
	/*void export(OutputStream os, Long uuid);*/
}

