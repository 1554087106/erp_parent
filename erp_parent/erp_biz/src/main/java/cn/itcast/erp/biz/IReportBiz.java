package cn.itcast.erp.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author whz
 * @Description 报表的Biz接口
 * @Date 2019/1/16
 * @Param
 * @return
 **/

public interface IReportBiz {
    /**
     * @Author whz
     * @Description //TODO 销售统计报表
     * @Date 2019/1/16
     * @Param [startDate, endDate]
     * @return List
     **/
    List ordersReport(Date startDate, Date endDate);

    /**
     * @Author whz
     * @Description //TODO 销售趋势图
     * @Date 2019/1/16
     * @Param [year]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/

    List<Map<String,Object>> getSumMoney(int year);
}
