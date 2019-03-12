package cn.itcast.erp.entity;

/**
 * @ClassName StoreAlert
 * @Description TODD 商品存储详情，用于展示商品所需数量与库存数量 对应视图 VIEW_STOREALERT
 * @Auther whz
 * @Date 2019/1/20 9:46
 * @Version 1.0
 **/
public class StoreAlert {

    private Long uuid;
    private String name;
    private Long storenum;
    private Long outnum;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStorenum() {
        return storenum;
    }

    public void setStorenum(Long storenum) {
        this.storenum = storenum;
    }

    public Long getOutnum() {
        return outnum;
    }

    public void setOutnum(Long outnum) {
        this.outnum = outnum;
    }
}