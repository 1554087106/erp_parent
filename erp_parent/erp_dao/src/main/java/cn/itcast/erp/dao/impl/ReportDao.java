package cn.itcast.erp.dao.impl;

import cn.itcast.erp.dao.IReportDao;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ReportDao
 * @Description TODD
 * @Auther whz
 * @Date 2019/1/16 19:46
 * @Version 1.0
 **/
public class ReportDao extends HibernateDaoSupport implements IReportDao {
    @Override
    public List orderReport(Date startDate, Date endDate) {
        String hql = "select new Map(gt.name as name,sum(ol.money) as y) "
                + "from Goodstype gt, Goods gs, Orderdetail ol, Orders o "
                + "where "
                + "gs.goodstype=gt and ol.orders = o "
                + "and ol.goodsuuid=gs.uuid and o.type='2' ";

        List<Date> dateList = new ArrayList<>();
        if(null != startDate){
            hql += "and o.createtime >= ?";
            dateList.add(startDate);
        }
        if(null != endDate){
            hql += "and o.createtime <= ?";
            dateList.add(endDate);
        }
        hql += "group by gt.name";
        //指定转换类型
        Date[] param = new Date[0];

        //转成新的数组
        Date[] params = dateList.toArray(param);

        return getHibernateTemplate().find(hql,params);
    }

    @Override
    public List<Map<String, Object>> getSumMoney(int year) {
        String hql = "select new Map(month(o.createtime) as name,sum(ol.money) as y)"
                + "from Orderdetail ol, Orders o "
                + "where ol.orders=o "
                + "and o.type='2' and year(o.createtime)=? "
                + "group by month(o.createtime)";
        return (List<Map<String, Object>>) getHibernateTemplate().find(hql,year);
    }
}