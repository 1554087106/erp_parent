package cn.itcast.erp.biz.impl;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.exception.ErpException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;

/**
 * 订单业务逻辑类
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;
	private IEmpDao empDao;
	private ISupplierDao supplierDao;
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		super.setBaseDao(this.ordersDao);
	}
	
	public void add(Orders orders){
		//获取主题
		Subject subject =
				SecurityUtils.getSubject();
		if(Orders.TYPE_IN.equals(orders.getType())){
			//如果是采购订单，则判断是否有采购申请权限
			if(subject.isPermitted("采购订单申请")){
				throw new ErpException("当前用户没有采购订单申请权限");
			}
		}else if(Orders.TYPE_OUT.equals(orders.getType())){
			//如果是销售订单，则判断是否有销售订单录入的权限
			if(subject.isPermitted("销售订单录入")){
				throw new ErpException("当前用户没有销售订单录入权限");
			}
		}else {
			throw new ErpException("非法参数!");
		}
		//1. 设置订单的状态
		orders.setState(Orders.STATE_CREATE);
		//2. 订单的类型
		orders.setType(Orders.TYPE_IN);
		//3. 下单时间
		orders.setCreatetime(new Date());

		// 合计金额
		double total = 0;
		
		for(Orderdetail detail : orders.getOrderDetails()){
			//累计金额
			total += detail.getMoney();
			//明细的状态
			detail.setState(Orderdetail.STATE_NOT_IN);
			//跟订单的关系
			detail.setOrders(orders);
		}
		//设置订单总金额
		orders.setTotalmoney(total);
		
		//保存到DB
		ordersDao.add(orders);
	}
	

	
	/**
	 * 审核
	 * @param uuid 订单编号
	 * @param empUuid 审核员
	 */
	//Shiro细颗粒授权控制--->方法级别
	//需要开启Shiro注解
	@RequiresPermissions("采购订单审核")
	public void doCheck(Long uuid, Long empUuid){
		
		//获取订单，进入持久化状态
		Orders orders = ordersDao.get(uuid);
		//订单的状态
		if(!Orders.STATE_CREATE.equals(orders.getState())){
			throw new ErpException("亲！该订单已经审核过了");
		}
		//1. 修改订单的状态
		orders.setState(Orders.STATE_CHECK);
		//2. 审核的时间
		orders.setChecktime(new Date());
		//3. 审核人
		orders.setChecker(empUuid);
	}
	
	/**
	 * 确认
	 * @param uuid 订单编号
	 * @param empUuid 采购员
	 */
	@RequiresPermissions("采购订单确认")
	public void doStart(Long uuid, Long empUuid){
		//获取订单，进入持久化状态
		Orders orders = ordersDao.get(uuid);
		//订单的状态
		if(!Orders.STATE_CHECK.equals(orders.getState())){
			throw new ErpException("亲！该订单已经确认过了");
		}
		//1. 修改订单的状态
		orders.setState(Orders.STATE_START);
		//2. 确认的时间
		orders.setStarttime(new Date());
		//3. 确认人
		orders.setStarter(empUuid);
	}


	
	/**
	 * 获取供应商名称
	 * @param uuid 供应商编号
	 * @param supplierNameMap 缓存供应商编号与供应商的名称 
	 * @return 返回供应商名称
	 */
	private String getSupplierName(Long uuid, Map<Long, String> supplierNameMap){
		if(null == uuid){
			return null;
		}
		String supplierName = supplierNameMap.get(uuid);
		if(null == supplierName){
			//如果没有找供应商的名称，则进行数据库查询
			supplierName = supplierDao.get(uuid).getName();
			//存入缓存中
			supplierNameMap.put(uuid, supplierName);
		}
		return supplierName;
	}
	
}
