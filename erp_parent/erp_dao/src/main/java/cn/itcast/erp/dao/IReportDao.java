package cn.itcast.erp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author whz
 * @Description //TODO 报表数据访问接口
 * @Date 2019/1/16
 * @Param
 * @return
 **/
public interface IReportDao {
    /**
     * @Author whz
     * @Description //TODO销售统计报表
     * @Date 2019/1/16
     * @Param [startDate, endDate]
     * @return java.util.List
     **/
    List orderReport(Date startDate,Date endDate);
    /**
     * @Author whz
     * @Description //TODO 销售统计图
     * @Date 2019/1/16
     * @Param [year]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getSumMoney(int year);
}
