package cn.itcast.erp.dao;

import cn.itcast.erp.entity.StoreAlert;
import cn.itcast.erp.entity.Storedetail;

import java.util.List;

/**
 * 仓库库存数据访问接口
 * @author Administrator
 *
 */
public interface IStoredetailDao extends IBaseDao<Storedetail>{
    /**
     * @Author whz
     * @Description //TODO 获取对应商品需求数量与库存数量，并获取需求 > 库存的商品
     * @Date 2019/1/20
     * @Param []
     * @return java.util.List<cn.itcast.erp.entity.Storedetail>
     **/
    public List<StoreAlert> getStoreAlertList();

}
