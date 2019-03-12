package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Supplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 供应商业务逻辑层接口
 * @author Administrator
 *
 */
public interface ISupplierBiz extends IBaseBiz<Supplier>{
    /**
     * @Author whz
     * @Description //TODO 到出到excel文件
     * @Date 2019/1/22
     * @Param [os 输出流, t1 查询条件]
     * @return void
     **/
    public void export(OutputStream os,Supplier t1);
    /**
     * @Author whz
     * @Description //TODO 数据导入
     * @Date 2019/1/22
     * @Param [is]
     * @return void
     **/
    public void doImport(InputStream is) throws IOException;
}

