package cn.itcast.erp.biz.impl;

import cn.itcast.erp.biz.IReportBiz;
import cn.itcast.erp.dao.IReportDao;

import java.util.*;

/**
 * @ClassName ReportBiz
 * @Description TODD
 * @Auther whz
 * @Date 2019/1/16 19:01
 * @Version 1.0
 **/
public class ReportBiz implements IReportBiz {

    private IReportDao reportDao;

    @Override
    public List ordersReport(Date startDate, Date endDate) {
        return reportDao.orderReport(startDate,endDate);
    }

    @Override
    public List<Map<String, Object>> getSumMoney(int year) {

        //对月份进行查缺补漏
        List<Map<String, Object>> yearData = reportDao.getSumMoney(year);
        //最终返回的数据
        ArrayList<Map<String, Object>> rtnData = new ArrayList<>();
        //
        HashMap<String, Map<String, Object>> yearDataMap = new HashMap<>();

        for(Map<String,Object> month : yearData){
            yearDataMap.put(month.get("name") + "",month);
        }

        //补全缺少的月份数据
        Map<String,Object> monthData = null;
        for(int i=1;i<=12;i++){
            monthData = yearDataMap.get(i + "");
            //z这个月没有数据
            if(monthData == null){
               //补回数据
                monthData = new HashMap<String,Object>();
                monthData.put("name",i+"月");
                monthData.put("y",0);
            }else {
                monthData.put("name",i+"月");
            }
            rtnData.add(monthData);
        }
        return rtnData;
    }

    public void setReportDao(IReportDao reportDao) {
        this.reportDao = reportDao;
    }
}