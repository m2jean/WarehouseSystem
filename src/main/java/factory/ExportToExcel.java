package factory;

import importBL.ImportLineItem;
import inventoryBL.SnapshotItem;

import java.io.File;
import java.util.ArrayList;

import productBL.ProductItem;
import productBL.ProductLineItem;
import saleBL.SaleLineItem;
import saleBL.SaleLog;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import enums.ResultMessage;
import VO.CheckInVO;
import VO.CheckOutVO;
import VO.CircumstanceVO;
import VO.CostVO;
import VO.ExportVO;
import VO.ImportVO;
import VO.ListInVO;
import VO.ListOutVO;
import VO.OverflowVO;
import VO.PresentVO;
import VO.ReturnVO;
import VO.SaleVO;
import VO.SnapshotVO;

public class ExportToExcel {
	
	public ResultMessage toExcel(ImportVO vo, WritableWorkbook book,int n){
		try{
			WritableSheet sheet=book.createSheet(Integer.toString(n), n);
			jxl.write.Number numberLabel;
			Label label;
			label=new Label(0, 1, "单据编号");
			sheet.addCell(label);
			label=new Label(1, 1, vo.getID());
			sheet.addCell(label);
			label=new Label(3, 1, "日期");
			sheet.addCell(label);
			label=new Label(4, 1, vo.getDate());
			sheet.addCell(label);
			label=new Label(0, 2, "供货商");
			sheet.addCell(label);
			label=new Label(1, 2, vo.getCustomer());
			sheet.addCell(label);
			label=new Label(3, 2, "仓库");
			sheet.addCell(label);
			label=new Label(4, 2, vo.getWarehouse());
			sheet.addCell(label);
			label=new Label(0, 3, "业务员");
			sheet.addCell(label);
			label=new Label(1, 3, vo.getOperator());
			sheet.addCell(label);
			label=new Label(0, 5, "ID");
			sheet.addCell(label);
			label=new Label(1, 5, "名称");
			sheet.addCell(label);
			label=new Label(2, 5, "型号");
			sheet.addCell(label);
			label=new Label(3, 5, "单价");
			sheet.addCell(label);
			label=new Label(4, 5, "数量");
			sheet.addCell(label);
			label=new Label(5, 5, "小结");
			sheet.addCell(label);
			
			ArrayList<ImportLineItem> itemList=vo.getProductList();
			int line=6;
			for(int i=0;i<itemList.size();i++){
				line=i+6;
				ImportLineItem item=itemList.get(i);
				label=new Label(0, line, item.getProductID());
				sheet.addCell(label);
				label=new Label(1, line, item.getName());
				sheet.addCell(label);
				label=new Label(2, line, item.getModel());
				sheet.addCell(label);
				numberLabel=new jxl.write.Number(3, line, item.getPrice());
				sheet.addCell(numberLabel);
				numberLabel=new jxl.write.Number(4, line, item.getNumber());
				sheet.addCell(numberLabel);
				numberLabel=new jxl.write.Number(5, line, item.getPrice()*item.getNumber());
				sheet.addCell(numberLabel);
			}
			
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage toExcel(ExportVO vo, WritableWorkbook book,int n){
		try{
			WritableSheet sheet=book.createSheet(Integer.toString(n), n);
			jxl.write.Number numberLabel;
			Label label;
			label=new Label(0, 1, "单据编号");
			sheet.addCell(label);
			label=new Label(1, 1, vo.getID());
			sheet.addCell(label);
			label=new Label(3, 1, "日期");
			sheet.addCell(label);
			label=new Label(4, 1, vo.getDate());
			sheet.addCell(label);
			label=new Label(0, 2, "供货商");
			sheet.addCell(label);
			label=new Label(1, 2, vo.getCustomer());
			sheet.addCell(label);
			label=new Label(3, 2, "仓库");
			sheet.addCell(label);
			label=new Label(4, 2, vo.getWarehouse());
			sheet.addCell(label);
			label=new Label(0, 3, "业务员");
			sheet.addCell(label);
			label=new Label(1, 3, vo.getOperator());
			sheet.addCell(label);
			label=new Label(3, 3, "进货单");
			sheet.addCell(label);
			label=new Label(4, 3, vo.getImportID());
			sheet.addCell(label);
			label=new Label(0, 5, "ID");
			sheet.addCell(label);
			label=new Label(1, 5, "名称");
			sheet.addCell(label);
			label=new Label(2, 5, "型号");
			sheet.addCell(label);
			label=new Label(3, 5, "单价");
			sheet.addCell(label);
			label=new Label(4, 5, "数量");
			sheet.addCell(label);
			label=new Label(5, 5, "小结");
			sheet.addCell(label);
			
			ArrayList<ImportLineItem> itemList=vo.getProductList();
			int line=6;
			for(int i=0;i<itemList.size();i++){
				line=i+6;
				ImportLineItem item=itemList.get(i);
				label=new Label(0, line, item.getProductID());
				sheet.addCell(label);
				label=new Label(1, line, item.getName());
				sheet.addCell(label);
				label=new Label(2, line, item.getModel());
				sheet.addCell(label);
				numberLabel=new jxl.write.Number(3, line, item.getPrice());
				sheet.addCell(numberLabel);
				numberLabel=new jxl.write.Number(4, line, item.getNumber());
				sheet.addCell(numberLabel);
				numberLabel=new jxl.write.Number(5, line, item.getPrice()*item.getNumber());
				sheet.addCell(numberLabel);
			}
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage toExcel(SnapshotVO vo, String path){
		File file=new File(path);
		try{
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableSheet sheet=book.createSheet("库存盘点", 0);
			jxl.write.Number labelNumber;
			Label label;
			label=new Label(0, 1, "日期");
			sheet.addCell(label);
			label=new Label(1, 1, vo.getDate());
			sheet.addCell(label);
			label=new Label(3, 1, "批次");
			sheet.addCell(label);
			labelNumber=new jxl.write.Number(4, 1, vo.getLotNumber()); 
			sheet.addCell(labelNumber);
			label=new Label(0, 2, "行号");
			sheet.addCell(label);
			label=new Label(1, 2, "ID");
			sheet.addCell(label);
			label=new Label(2, 2, "名称");
			sheet.addCell(label);
			label=new Label(3, 2, "型号");
			sheet.addCell(label);
			label=new Label(4, 2, "库存数量");
			sheet.addCell(label);
			label=new Label(5, 2, "库存均价");
			sheet.addCell(label);
			
			ArrayList<SnapshotItem> itemList=vo.getList();
			for(int i=1;i<=itemList.size();i++){
				int line=i+2;
				SnapshotItem item=itemList.get(i-1);
				labelNumber=new jxl.write.Number(0, line, i); 
				sheet.addCell(labelNumber);
				label=new Label(1, line, item.getID());
				sheet.addCell(label);
				label=new Label(2, line, item.getName());
				sheet.addCell(label);
				label=new Label(3, line, item.getModel());
				sheet.addCell(label);
				labelNumber=new jxl.write.Number(4, line, item.getNumber()); 
				sheet.addCell(labelNumber);
				labelNumber=new jxl.write.Number(5, line, item.getAverage()); 
				sheet.addCell(labelNumber);
			}
			
			book.write();
			book.close();
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage toExcel(OverflowVO vo,WritableWorkbook book,int n){
		try{
			WritableSheet sheet=book.createSheet(Integer.toString(n), n);
			jxl.write.Number numberLabel;
			Label label;
			label=new Label(0, 1, "ID");
			sheet.addCell(label);
			label=new Label(3, 1, vo.getID());
			sheet.addCell(label);
			label=new Label(0, 1, "日期");
			sheet.addCell(label);
			label=new Label(1, 1, vo.getDate());
			sheet.addCell(label);
			label=new Label(0, 2, "仓库");
			sheet.addCell(label);
			label=new Label(1, 2, vo.getWarehouse());
			sheet.addCell(label);
			label=new Label(3, 2, "业务员");
			sheet.addCell(label);
			label=new Label(4, 2, vo.getOperator());
			sheet.addCell(label);
			label=new Label(0, 4, "ID");
			sheet.addCell(label);
			label=new Label(1, 4, "名称");
			sheet.addCell(label);
			label=new Label(2, 4, "型号");
			sheet.addCell(label);
			
			ProductLineItem item=vo.getProduct();
			label=new Label(0, 5, item.getID());
			sheet.addCell(label);
			label=new Label(1, 5, item.getName());
			sheet.addCell(label);
			label=new Label(2, 5, item.getModel());
			sheet.addCell(label);
			
			label=new Label(0, 7, "实际数量");
			sheet.addCell(label);
			numberLabel=new jxl.write.Number(1, 7, vo.getNumInWarehouse());
			sheet.addCell(numberLabel);
			label=new Label(0, 8, "系统数量");
			sheet.addCell(label);
			numberLabel=new jxl.write.Number(1, 8, vo.getNumInSystem());
			sheet.addCell(numberLabel);
			label=new Label(0, 9, "差值");
			sheet.addCell(label);
			numberLabel=new jxl.write.Number(1, 9, vo.getNumInWarehouse()-vo.getNumInSystem());
			sheet.addCell(numberLabel);
			
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage toExcel(PresentVO vo,WritableWorkbook book,int n){
		try{
			WritableSheet sheet=book.createSheet(Integer.toString(n), n);
			jxl.write.Number numberLabel;
			Label label;
			label=new Label(0, 1, "ID");
			sheet.addCell(label);
			label=new Label(3, 1, vo.getID());
			sheet.addCell(label);
			label=new Label(0, 1, "日期");
			sheet.addCell(label);
			label=new Label(1, 1, vo.getDate());
			sheet.addCell(label);
			label=new Label(0, 2, "业务员");
			sheet.addCell(label);
			label=new Label(1, 2, vo.getOperator());
			sheet.addCell(label);
			label=new Label(0, 4, "ID");
			sheet.addCell(label);
			label=new Label(1, 4, "名称");
			sheet.addCell(label);
			label=new Label(2, 4, "型号");
			sheet.addCell(label);
			label=new Label(3, 4, "数量");
			sheet.addCell(label);
			
			ArrayList<ProductItem> itemList=vo.getProductList();
			int line;
			for(int i=0;i<itemList.size();i++){
				line=5+i;
				ProductItem item=itemList.get(i);
				label=new Label(0, line, item.getProductID());
				sheet.addCell(label);
				label=new Label(1, line, item.getProductName());
				sheet.addCell(label);
				label=new Label(2, line, item.getProductModel());
				sheet.addCell(label);
				numberLabel=new jxl.write.Number(3, line, item.getNumber());
				sheet.addCell(numberLabel);
			}
	
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage toExcel(SaleVO vo,WritableWorkbook book,int n){
		try{
			WritableSheet sheet=book.createSheet(Integer.toString(n), n);
			sheet.addCell(new Label(0,0,"ID"));
			sheet.addCell(new Label(0,1,vo.getID()));
			sheet.addCell(new Label(1,0,"客户"));
			sheet.addCell(new Label(1,1,vo.getCustomer()));
			sheet.addCell(new Label(2,0,"业务员"));
			sheet.addCell(new Label(2,1,vo.getSalesman()));
			sheet.addCell(new Label(3,0,"操作员"));
			sheet.addCell(new Label(3,1,vo.getOperator()));
			sheet.addCell(new Label(4,0,"仓库"));
			sheet.addCell(new Label(4,1,vo.getWarehouse()));
			sheet.addCell(new Label(5,0,"原总价"));
			sheet.addCell(new jxl.write.Number(5,1,vo.getTotal()));
			sheet.addCell(new Label(6,0,"折让"));
			sheet.addCell(new jxl.write.Number(6,1,vo.getDiscount1()+vo.getDiscount2()));
			sheet.addCell(new Label(7,0,"收代金券"));
			sheet.addCell(new jxl.write.Number(7,1,vo.getCreditGet()));
			sheet.addCell(new Label(8,0,"送代金券"));
			sheet.addCell(new jxl.write.Number(8,1,vo.getCreditGive()));
			sheet.addCell(new Label(9,0,"合计"));
			sheet.addCell(new jxl.write.Number(9,1,vo.getPostTotal()));
			sheet.addCell(new Label(10,0,"备注"));
			sheet.addCell(new Label(10,1,vo.getRemarks()));
			sheet.addCell(new Label(0,3,"出货商品清单"));
			sheet.addCell(new Label(0,4,"编号"));
			sheet.addCell(new Label(1,4,"名称"));
			sheet.addCell(new Label(2,4,"型号"));
			sheet.addCell(new Label(3,4,"数量"));
			sheet.addCell(new Label(4,4,"单价"));
			sheet.addCell(new Label(5,4,"金额"));
			sheet.addCell(new Label(6,4,"备注"));
			int count=5;
			for(SaleLineItem i:vo.getProductList()){
				sheet.addCell(new Label(0,count,i.getID()));
				sheet.addCell(new Label(1,count,i.getName()));
				sheet.addCell(new Label(2,count,i.getSize()));
				sheet.addCell(new jxl.write.Number(3,count,i.getNumber()));
				sheet.addCell(new jxl.write.Number(4,count,i.getPrice()));
				sheet.addCell(new jxl.write.Number(5,count,i.getMoney()));
				sheet.addCell(new Label(6,count,i.getRemark()));
				count=count+1;
			}
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage toExcel(ReturnVO vo,WritableWorkbook book,int n){
		try{
			WritableSheet sheet=book.createSheet(Integer.toString(n), n);
			sheet.addCell(new Label(0,0,"ID"));
			sheet.addCell(new Label(0,1,vo.getID()));
			sheet.addCell(new Label(1,0,"客户"));
			sheet.addCell(new Label(1,1,vo.getCustomer()));
			sheet.addCell(new Label(2,0,"业务员"));
			sheet.addCell(new Label(2,1,vo.getSalesman()));
			sheet.addCell(new Label(3,0,"操作员"));
			sheet.addCell(new Label(3,1,vo.getOperator()));
			sheet.addCell(new Label(4,0,"仓库"));
			sheet.addCell(new Label(4,1,vo.getWarehouse()));
			sheet.addCell(new Label(5,0,"合计"));
			sheet.addCell(new jxl.write.Number(5,1,vo.getTotal()));
			sheet.addCell(new Label(6,0,"备注"));
			sheet.addCell(new Label(6,1,vo.getRemarks()));
			sheet.addCell(new Label(0,3,"退货商品清单"));
			sheet.addCell(new Label(0,4,"编号"));
			sheet.addCell(new Label(1,4,"名称"));
			sheet.addCell(new Label(2,4,"型号"));
			sheet.addCell(new Label(3,4,"数量"));
			sheet.addCell(new Label(4,4,"单价"));
			sheet.addCell(new Label(5,4,"金额"));
			sheet.addCell(new Label(6,4,"备注"));
			int count=5;
			for(SaleLineItem i:vo.getProductList()){
				sheet.addCell(new Label(0,count,i.getID()));
				sheet.addCell(new Label(1,count,i.getName()));
				sheet.addCell(new Label(2,count,i.getSize()));
				sheet.addCell(new jxl.write.Number(3,count,i.getNumber()));
				sheet.addCell(new jxl.write.Number(4,count,i.getPrice()));
				sheet.addCell(new jxl.write.Number(5,count,i.getMoney()));
				sheet.addCell(new Label(6,count,i.getRemark()));
				count=count+1;
			}

			return ResultMessage.SUCCESS;
		}catch(Exception e){
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage toExcel(CheckInVO vo,WritableWorkbook book,int n){
		try{
			WritableSheet sheet=book.createSheet(Integer.toString(n), n);
			sheet.addCell(new Label(0,0,"ID"));
			sheet.addCell(new Label(0,1,vo.getID()));
			sheet.addCell(new Label(1,0,"客户"));
			sheet.addCell(new Label(1,1,vo.getCustomerName()));
			sheet.addCell(new Label(2,0,"操作员"));
			sheet.addCell(new Label(2,1,vo.getOperator()));
			sheet.addCell(new Label(3,0,"合计"));
			sheet.addCell(new jxl.write.Number(3,1,vo.getTotal()));
			sheet.addCell(new Label(4,0,"备注"));
			sheet.addCell(new Label(4,1,vo.getRemarks()));
			sheet.addCell(new Label(0,3,"转账列表"));
			sheet.addCell(new Label(0,4,"银行账户"));
			sheet.addCell(new Label(1,4,"转账金额"));
			sheet.addCell(new Label(2,4,"备注"));
			int count=5;
			for(ListInVO i:vo.getList()){
				sheet.addCell(new Label(0,count,i.getAccount()));
				sheet.addCell(new jxl.write.Number(1,count,i.getMoney()));
				sheet.addCell(new Label(2,count,i.getRemark()));
				count=count+1;
			}

			return ResultMessage.SUCCESS;
		}catch(Exception e){
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage toExcel(CheckOutVO vo,WritableWorkbook book,int n){
		try{
			WritableSheet sheet=book.createSheet(Integer.toString(n), n);
			sheet.addCell(new Label(0,0,"ID"));
			sheet.addCell(new Label(0,1,vo.getID()));
			sheet.addCell(new Label(1,0,"客户"));
			sheet.addCell(new Label(1,1,vo.getCustomerName()));
			sheet.addCell(new Label(2,0,"操作员"));
			sheet.addCell(new Label(2,1,vo.getOperator()));
			sheet.addCell(new Label(3,0,"合计"));
			sheet.addCell(new jxl.write.Number(3,1,vo.getTotal()));
			sheet.addCell(new Label(4,0,"备注"));
			sheet.addCell(new Label(4,1,vo.getRemarks()));
			sheet.addCell(new Label(0,3,"转账列表"));
			sheet.addCell(new Label(0,4,"银行账户"));
			sheet.addCell(new Label(1,4,"转账金额"));
			sheet.addCell(new Label(2,4,"备注"));
			int count=5;
			for(ListInVO i:vo.getList()){
				sheet.addCell(new Label(0,count,i.getAccount()));
				sheet.addCell(new jxl.write.Number(1,count,i.getMoney()));
				sheet.addCell(new Label(2,count,i.getRemark()));
				count=count+1;
			}

			return ResultMessage.SUCCESS;
		}catch(Exception e){
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage toExcel(CostVO vo,WritableWorkbook book,int n){
		try{
			WritableSheet sheet=book.createSheet(Integer.toString(n), n);
			sheet.addCell(new Label(0,0,"ID"));
			sheet.addCell(new Label(0,1,vo.getID()));
			sheet.addCell(new Label(1,0,"操作员"));
			sheet.addCell(new Label(1,1,vo.getOperator()));
			sheet.addCell(new Label(2,0,"银行账户"));
			sheet.addCell(new Label(2,1,vo.getAccount()));
			sheet.addCell(new Label(3,0,"合计"));
			sheet.addCell(new jxl.write.Number(3,1,vo.getTotal()));
			sheet.addCell(new Label(4,0,"备注"));
			sheet.addCell(new Label(4,1,vo.getRemarks()));
			sheet.addCell(new Label(0,3,"条目清单"));
			sheet.addCell(new Label(0,4,"条目名"));
			sheet.addCell(new Label(1,4,"金额"));
			sheet.addCell(new Label(2,4,"备注"));
			int count=5;
			for(ListOutVO i:vo.getList()){
				sheet.addCell(new Label(0,count,i.getName()));
				sheet.addCell(new jxl.write.Number(1,count,i.getMoney()));
				sheet.addCell(new Label(2,count,i.getRemark()));
				count=count+1;
			}

			return ResultMessage.SUCCESS;
		}catch(Exception e){
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage toExcel(ArrayList<SaleLog> log,String path){
		File file=new File(path);
		try{
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableSheet sheet=book.createSheet("商品销售记录",0);
			sheet.addCell(new Label(0,0,"时间"));
			sheet.addCell(new Label(1,0,"商品编号"));
			sheet.addCell(new Label(2,0,"商品名"));
			sheet.addCell(new Label(3,0,"型号"));
			sheet.addCell(new Label(4,0,"数量"));
			sheet.addCell(new Label(5,0,"单价"));
			sheet.addCell(new Label(6,0,"总价"));
			int count=1;
			for(SaleLog i:log){
				sheet.addCell(new Label(0,count,i.getDate()));
				sheet.addCell(new Label(1,count,i.getId()));
				sheet.addCell(new Label(2,count,i.getName()));
				sheet.addCell(new Label(3,count,i.getSize()));
				sheet.addCell(new jxl.write.Number(4,count,i.getNumber()));
				sheet.addCell(new jxl.write.Number(5,count,i.getPrice()));
				sheet.addCell(new jxl.write.Number(6,count,i.getMoney()));
                count++;
			}
			book.write();
			book.close();
		}catch(Exception e){
			return ResultMessage.SAVE_FAIL;
		}
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage toExcel(CircumstanceVO vo,String path){
		File file=new File(path);
		try{
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableSheet sheet=book.createSheet("经营情况表",0);
			sheet.addCell(new Label(0,0,"销售收入"));
			sheet.addCell(new jxl.write.Number(0,1,vo.getSalein()));
			sheet.addCell(new Label(1,0,"报溢收入"));
			sheet.addCell(new jxl.write.Number(1,1,vo.getOverin()));
			sheet.addCell(new Label(2,0,"进货退货差价"));
			sheet.addCell(new jxl.write.Number(2,1,vo.getChajia()));
			sheet.addCell(new Label(3,0,"成本调价收入"));
			sheet.addCell(new jxl.write.Number(3,1,vo.getChange()));
			sheet.addCell(new Label(4,0,"代金券收入"));
			sheet.addCell(new jxl.write.Number(4,1,vo.getCredit()));
			sheet.addCell(new Label(5,0,"总折让"));
			sheet.addCell(new jxl.write.Number(5,1,vo.getDiscount()));
			sheet.addCell(new Label(6,0,"销售成本"));
			sheet.addCell(new jxl.write.Number(6,1,vo.getSaleout()));
			sheet.addCell(new Label(7,0,"报损支出"));
			sheet.addCell(new jxl.write.Number(7,1,vo.getOverout()));
			sheet.addCell(new Label(8,0,"赠品"));
			sheet.addCell(new jxl.write.Number(8,1,vo.getPresent()));
			sheet.addCell(new Label(9,0,"总收入"));
			sheet.addCell(new jxl.write.Number(9,1,vo.getIncome()));
			sheet.addCell(new Label(10,0,"总支出"));
			sheet.addCell(new jxl.write.Number(10,1,vo.getExpense()));
			sheet.addCell(new Label(11,0,"利润"));
			sheet.addCell(new jxl.write.Number(11,1,vo.getProfit()));
			book.write();
			book.close();
		}catch(Exception e){
			return ResultMessage.SAVE_FAIL;
		}
		return ResultMessage.SUCCESS;
	}
}
