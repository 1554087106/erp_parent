package cn.itcast.erp.action;
import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.entity.Supplier;
import cn.itcast.erp.exception.ErpException;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 供应商Action 
 * @author Administrator
 *
 */
public class SupplierAction extends BaseAction<Supplier> {

	private ISupplierBiz supplierBiz;

	//读取xls文件需要的属性
	//上传文件
	private File file;
	//文件名
	private String fileFileName;
	//文件类型
	private String fileContentType;

	//自动补全，由easyui得combogrid自动发送过来的
	private String q;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public ISupplierBiz getSupplierBiz() {
		return supplierBiz;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void list(){
		//判断查询条件是否为空
		if(null == getT1()){
			//构造查询条件
			setT1(new Supplier());
		}
		getT1().setName(q);
		super.list();
	}

	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		super.setBaseBiz(this.supplierBiz);
	}

	/**
	 * @Author whz
	 * @Description //TODO 导出excel文件
	 * @Date 2019/1/22
	 * @Param []
	 * @return void
	 **/
	public void export(){
		String filename = "";
		//根据类型生成文件名
		if("1".equals(getT1().getType())){
			filename = "供应商.xls";
		}
		if("2".equals(getT1().getType())){
			filename = "客户.xls";
		}
		HttpServletResponse response = ServletActionContext.getResponse();

		try {
			//中文转码
			response.setHeader("Content-Disposition",
					"attachment;filename="+new String(filename.getBytes(),"ISO-8859-1"));
			try {
				//调用导出业务
				supplierBiz.export(response.getOutputStream(),getT1());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Author whz
	 * @Description //TODO 读入数据
	 * @Date 2019/1/22
	 * @Param []
	 * @return void
	 **/
	public void doImport(){
		//文件类型判断
		if(!"application/vnd.ms-excel".equals(fileContentType)){
			ajaxReturn(false,"上传文件必须为excel文件");
			return;
		}
		try {
			supplierBiz.doImport(new FileInputStream(file));
			ajaxReturn(true,"上传文件成功");
		} catch (ErpException e){
			ajaxReturn(false,e.getMessage());
		} catch (IOException e) {
			ajaxReturn(false,"文件上传失败");
			e.printStackTrace();
		}
	}

}
