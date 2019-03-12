package cn.itcast.erp.action;

import cn.itcast.erp.biz.IReportBiz;
import com.alibaba.fastjson.JSON;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author whz
 * @Description 报表的Action
 * @Date 2019/1/16
 * @Param
 * @return
 **/
public class ReportAction {
    //引入reportBiz依赖
    private IReportBiz reportBiz;
    //订单创建时间
    private Date startDate;
    //订单创建时间
    private Date endDate;
    //指定年份
    private int year;

    public IReportBiz getReportBiz() {
        return reportBiz;
    }

    public void setReportBiz(IReportBiz reportBiz) {
        this.reportBiz = reportBiz;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    /**
     * @Author whz
     * @Description //TODO 输出字符串到前端
     * @Date 2019/1/16
     * @Param [jsonString]
     * @return void
     **/
    public void write(String jsonString){
        try {
        //响应对象
        HttpServletResponse response = ServletActionContext.getResponse();
        //设置编码
        response.setContentType("text/html;charset=utf-8");
        //输出给页面
        response.getWriter().write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author whz
     * @Description //TODO销售统计报表
     * @Date 2019/1/16
     * @Param []
     * @return void
     **/
    public void orderReport(){
        List list = reportBiz.ordersReport(startDate, endDate);
        write(JSON.toJSONString(list));
    }

    /**
     * @Author whz
     * @Description //TODO 趋势表
     * @Date 2019/1/16
     * @Param []
     * @return void
     **/
    public void trendReport(){
        List list = reportBiz.getSumMoney(year);
        write(JSON.toJSONString(list));
    }


}
