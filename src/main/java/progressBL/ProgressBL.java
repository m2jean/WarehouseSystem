package progressBL;

import importBL.ExportBL;
import importBL.ImportBL;
import importBL.ImportLineItem;
import importBLService.ExportBLService;
import importBLService.ImportBLService;
import inventoryBL.InventoryBL;
import inventoryBLService.InventoryBLService;

import java.io.File;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import factory.ExportToExcel;
import businesslogic.userbl.UserBL;
import businesslogicservice.userblservice.UserBLService;
import accountBL.CheckInBL;
import accountBL.CheckOutBL;
import accountBL.CostBL;
import accountBLService.CheckInBLService;
import accountBLService.CheckOutBLService;
import accountBLService.CostBLService;
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
import VO.ProductVO;
import VO.ProgressVO;
import VO.ReturnVO;
import VO.SaleVO;
import enums.DataMessage;
import enums.ResultMessage;
import enums.Status;
import factory.Factory;
import productBL.CostChange;
import productBL.ProductBL;
import productBL.ProductItem;
import productBLService.ProductBLService;
import progressBLService.ProgressBLService;
import saleBL.ReturnBL;
import saleBL.SaleBL;
import saleBL.SaleLineItem;
import saleBLService.ReturnBLService;
import saleBLService.SaleBLService;

public class ProgressBL implements ProgressBLService{

	public ProgressVO getBill(String stime,String etime, String kind, String customer,
			String yewu, String cangku) {
		ProgressVO vo=new ProgressVO();
		if(kind.equals("Sale")){
			SaleBLService sales=new SaleBL();
			ReturnBLService returns=new ReturnBL();
			DataMessage<ArrayList<SaleVO>> Sale=sales.getSales(stime, etime, "", customer, yewu, cangku);
			DataMessage<ArrayList<ReturnVO>> Return=returns.getReturns(stime, etime,"", customer, yewu, cangku);
		    vo.setSales(Sale);
		    vo.setReturns(Return);
		}
		if(kind.equals("Import")){
			ImportBLService imports=new ImportBL();
			ExportBLService exports=new ExportBL();
			vo.setImports(imports.getImport(stime, etime, customer, yewu, cangku));
			vo.setExports(exports.getExport(stime, etime, customer, yewu, cangku));
		}
		if(kind.equals("Check")){
			CheckInBLService checkin=new CheckInBL();
			CheckOutBLService checkout=new CheckOutBL();
			CostBLService cost=new CostBL();
			vo.setCheckin(checkin.getCheck(stime, etime, customer));
			vo.setCheckout(checkout.getCheck(stime, etime,customer));
			vo.setCost(cost.getCheck(stime, etime));
		}
		if(kind.equals("Inventory")){
			InventoryBLService inventory=new InventoryBL();
			vo.setOverflow(inventory.getOverflow(stime, etime, cangku));
			vo.setPresent(inventory.getPresent(stime, etime));
		}
		return vo;
	}

	public CircumstanceVO getCircumstance(String stime, String etime) {
		SaleBLService salebl=new SaleBL();
		ReturnBLService returnbl=new ReturnBL();
		InventoryBLService inventorybl=new InventoryBL();
		ProductBLService productbl=new ProductBL();
		ImportBLService importbl=new ImportBL();
		ExportBLService exportbl=new ExportBL();
		CircumstanceVO result=new CircumstanceVO();
		DataMessage<ArrayList<SaleVO>> Sale=salebl.getSales(stime, etime, "", "", "", "");
		if(Sale.resultMessage==ResultMessage.SUCCESS){
			ArrayList<SaleVO> del=new ArrayList<SaleVO>();
			ArrayList<SaleVO> sale=Sale.data;
			for(SaleVO i:sale){
				if(i.getStatus()!=Status.PASS){
					del.add(i);
				}
			}
			sale.removeAll(del);
			for(SaleVO i:sale){
				result.setSalein(result.getSalein()+i.getTotal());
				result.setDiscount(result.getDiscount()+i.getDiscount1()+i.getDiscount2());
				if((i.getCreditGet()+i.getDiscount1()+i.getDiscount2()-i.getTotal())>0){
					result.setCredit(result.getCredit()+i.getCreditGet()+i.getDiscount1()+i.getDiscount2()-i.getTotal());
				}
				for(SaleLineItem j:i.getProductList()){
					DataMessage<ProductVO> product=productbl.getProduct(new ProductVO(j.getID()));
					if(product.resultMessage==ResultMessage.SUCCESS){
						result.setSaleout(result.getSaleout()+product.data.getPriceIn()*j.getNumber());
					}
				}
			}
		}
		
		DataMessage<ArrayList<ReturnVO>> Return=returnbl.getReturns(stime, etime, "", "", "","");
		if(Return.resultMessage==ResultMessage.SUCCESS){
			ArrayList<ReturnVO> del=new ArrayList<ReturnVO>();
			ArrayList<ReturnVO> returns=Return.data;
			for(ReturnVO i:returns){
				if(i.getStatus()!=Status.PASS){
					del.add(i);
				}
			}
			returns.removeAll(del);
			for(ReturnVO i:returns){
				result.setSalein(result.getSalein()-i.getTotal());
				for(SaleLineItem j:i.getProductList()){
					DataMessage<ProductVO> product=productbl.getProduct(new ProductVO(j.getID()));
					if(product.resultMessage==ResultMessage.SUCCESS){
						result.setSaleout(result.getSaleout()-product.data.getPriceIn()*j.getNumber());
					}
				}
			}
		}
		
		DataMessage<ArrayList<ExportVO>> Export=exportbl.getExport(stime, etime);
		if(Export.resultMessage==ResultMessage.SUCCESS){
			ArrayList<ExportVO> del=new ArrayList<ExportVO>();
			ArrayList<ExportVO> exports=Export.data;
			for(ExportVO i:exports){
				if(i.getStatus()!=Status.PASS){
					del.add(i);
				}
			}
			exports.removeAll(del);
			for(ExportVO i:exports){
				DataMessage<ImportVO> j=importbl.getImport(new ImportVO(i.getImportID()));
				if(j.resultMessage==ResultMessage.SUCCESS){
					result.setChajia(result.getChajia()+i.getTotal()-j.data.getTotal());
				}
			}
		}
		
		DataMessage<ArrayList<CostChange>> Cost=productbl.getCostChange();
		if(Cost.resultMessage==ResultMessage.SUCCESS){
			ArrayList<CostChange> costc=Cost.data;
			for(CostChange i:costc){
				result.setChange(result.getChange()+i.getChange());
			}
		}
		
		DataMessage<ArrayList<OverflowVO>> Over=inventorybl.getOverflow(stime, etime,null);
		if(Over.resultMessage==ResultMessage.SUCCESS){
			ArrayList<OverflowVO> del=new ArrayList<OverflowVO>();
			ArrayList<OverflowVO> overf=Over.data;
			for(OverflowVO i:overf){
				if(i.getStatus()!=Status.PASS){
					del.add(i);
				}
			}
			overf.removeAll(del);
			for(OverflowVO i:overf){
				if(i.getNumInSystem()<i.getNumInWarehouse()){
					DataMessage<ProductVO> pvo=productbl.getProduct(new ProductVO(i.getProduct().getID()));
					if(pvo.resultMessage==ResultMessage.SUCCESS){
						int num=i.getNumInWarehouse()-i.getNumInSystem();
						result.setOverin(result.getOverin()+num*pvo.data.getPriceIn());
					}
				}else{
					DataMessage<ProductVO> pvo=productbl.getProduct(new ProductVO(i.getProduct().getID()));
					if(pvo.resultMessage==ResultMessage.SUCCESS){
						int num=i.getNumInSystem()-i.getNumInWarehouse();
						result.setOverout(result.getOverout()+num*pvo.data.getPriceIn());
					}
				}
			}
		}
		
		DataMessage<ArrayList<PresentVO>> present=inventorybl.getPresent(stime, etime);
		if(present.resultMessage==ResultMessage.SUCCESS){
			ArrayList<PresentVO> del=new ArrayList<PresentVO>();
			ArrayList<PresentVO> prese=present.data;
			for(PresentVO i:prese){
				if(i.getStatus()!=Status.PASS){
					del.add(i);
				}
			}
			prese.removeAll(del);
			for(PresentVO i:prese){
				for(ProductItem j:i.getProductList()){
					DataMessage<ProductVO> product=productbl.getProduct(new ProductVO(j.getProductID()));
					if(product.resultMessage==ResultMessage.SUCCESS){
						result.setPresent(result.getPresent()+product.data.getPriceIn()*j.getNumber());
					}
				}
			}
		}
		
		result.setIncome(result.getSalein()+result.getOverin()+result.getChange()+result.getChajia()+result.getCredit()-result.getDiscount());
		result.setExpense(result.getSaleout()+result.getOverout());
		result.setProfit(result.getIncome()-result.getExpense());
		return result;
	}

	public SaleVO hong(SaleVO vo) {
		Factory factory=new Factory();
		UserBLService user=new UserBL();
		ArrayList<SaleLineItem> item=vo.getProductList();
		for(SaleLineItem i:item){
			i.setNumber(0-i.getNumber());
			i.setMoney(i.getPrice()*i.getNumber());
		}
		vo.setDate(factory.getDate());
		vo.setOperator(user.getCurrent().getName());
		vo.setPreTotal(0-vo.getTotal());
		vo.setPostTotal(0-vo.getPostTotal());
		vo.setCreditGet(0-vo.getCreditGet());
		vo.setDiscount1(0-vo.getDiscount1());
		vo.setCreditGive(0-vo.getCreditGive());
		vo.setDiscount2(0-vo.getDiscount2());
		vo.setStatus(Status.SUBMIT);
		vo.setHong(true);
		return vo;
	}
	
	public PresentVO hong(PresentVO present){
		Factory factory=new Factory();
		UserBLService user=new UserBL();
		present.setDate(factory.getDate());
		for(ProductItem i:present.getProductList()){
			i.setNumber(0-i.getNumber());
		}
		present.setOperator(user.getCurrent().getName());
		present.setHong(true);
		present.setStatus(Status.SUBMIT);
		return present;
	}

	public ReturnVO hong(ReturnVO vo) {
		Factory factory=new Factory();
		UserBLService user=new UserBL();
		ArrayList<SaleLineItem> item=vo.getProductList();
		for(SaleLineItem i:item){
			i.setNumber(0-i.getNumber());
			i.setMoney(i.getPrice()*i.getNumber());
		}
		vo.setDate(factory.getDate());
		vo.setOperator(user.getCurrent().getName());
		vo.setTotal(0-vo.getTotal());
		vo.setStatus(Status.SUBMIT);
		vo.setHong(true);
		return vo;
	}

	public ImportVO hong(ImportVO vo) {
		Factory factory=new Factory();
		UserBLService user=new UserBL();
		ArrayList<ImportLineItem> item=vo.getProductList();
		for(ImportLineItem i:item){
			i.setNumber(0-i.getNumber());
		}
		vo.setDate(factory.getDate());
		vo.setOperator(user.getCurrent().getName());
		vo.setTotal(0-vo.getTotal());
		vo.setStatus(Status.SUBMIT);
		vo.setHong(true);
		return vo;
	}

	public ExportVO hong(ExportVO vo) {
		Factory factory=new Factory();
		UserBLService user=new UserBL();
		ArrayList<ImportLineItem> item=vo.getProductList();
		for(ImportLineItem i:item){
			i.setNumber(0-i.getNumber());
		}
		vo.setDate(factory.getDate());
		vo.setOperator(user.getCurrent().getName());
		vo.setTotal(0-vo.getTotal());
		vo.setStatus(Status.SUBMIT);
		vo.setHong(true);
		return vo;
	}

	public CheckInVO hong(CheckInVO vo) {
		Factory factory=new Factory();
		UserBLService user=new UserBL();
		vo.setDate(factory.getDate());
		vo.setOperator(user.getCurrent().getName());
		vo.setTotal(0-vo.getTotal());
		vo.setStatus(Status.SUBMIT);
		ArrayList<ListInVO> in=vo.getList();
		for(ListInVO i:in){
			i.setMoney(0-i.getMoney());
		}
		vo.setList(in);
		vo.setHong(true);
		return vo;
	}

	public CheckOutVO hong(CheckOutVO vo) {
		Factory factory=new Factory();
		UserBLService user=new UserBL();
		vo.setDate(factory.getDate());
		vo.setOperator(user.getCurrent().getName());
		vo.setTotal(0-vo.getTotal());
		vo.setStatus(Status.SUBMIT);
		ArrayList<ListInVO> in=vo.getList();
		for(ListInVO i:in){
			i.setMoney(0-i.getMoney());
		}
		vo.setList(in);
		vo.setHong(true);
		return vo;
	}
	
	public CostVO hong(CostVO vo){
		Factory factory=new Factory();
		UserBLService user=new UserBL();
		vo.setDate(factory.getDate());
		vo.setOperator(user.getCurrent().getName());
		vo.setTotal(0-vo.getTotal());
		vo.setStatus(Status.SUBMIT);
		ArrayList<ListOutVO> out=vo.getList();
		for(ListOutVO i:out){
			i.setMoney(0-i.getMoney());
		}
		vo.setList(out);
		vo.setHong(true);
		return vo;
	}

	public ResultMessage hongcopy(SaleVO vo,PresentVO present) {
		SaleBLService Sale=new SaleBL();
		return Sale.addSale(vo,present);
	}

	public ResultMessage hongcopy(ReturnVO vo) {
		ReturnBLService Return=new ReturnBL();
		return Return.addReturn(vo);
	}

	public ResultMessage hongcopy(ImportVO vo) {
		ImportBLService Import=new ImportBL();
		return Import.newImport(vo);
	}

	public ResultMessage hongcopy(ExportVO vo) {
		ExportBLService Export=new ExportBL();
		return Export.newExport(vo);
	}

	public ResultMessage hongcopy(CheckInVO vo) {
		CheckInBLService CheckIn=new CheckInBL();
		return CheckIn.addReceipt(vo);
	}

	public ResultMessage hongcopy(CheckOutVO vo) {
		CheckOutBLService CheckOut=new CheckOutBL();
		return CheckOut.addPayment(vo);
	}
	
	public ResultMessage hongcopy(CostVO vo){
		CostBLService costbl=new CostBL();
		return costbl.addCost(vo);
	}
	
	public ResultMessage hongcopy(PresentVO vo){
		InventoryBLService inventorybl=new InventoryBL();
		return inventorybl.newPresentTable(vo);
	}
	
	public ResultMessage ImportToExcel(ArrayList<ImportVO> vo,String path){
		File file=new File(path);
    	try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			int count=0;
			ResultMessage result=ResultMessage.SUCCESS;
			ExportToExcel excel=new ExportToExcel();
			for(ImportVO i:vo){
				result=excel.toExcel(i, book, count);
				count++;
				if(result==ResultMessage.SAVE_FAIL){
					return result;
				}
			}
			book.write();
			book.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public ResultMessage ExportToExcel(ArrayList<ExportVO> vo,String path){
		File file=new File(path);
    	try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			int count=0;
			ResultMessage result=ResultMessage.SUCCESS;
			ExportToExcel excel=new ExportToExcel();
			for(ExportVO i:vo){
				result=excel.toExcel(i, book, count);
				count++;
				if(result==ResultMessage.SAVE_FAIL){
					return result;
				}
			}
			book.write();
			book.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
	
    public ResultMessage SaleToExcel(ArrayList<SaleVO> vo,String path){
    	File file=new File(path);
    	try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			int count=0;
			ResultMessage result=ResultMessage.SUCCESS;
			ExportToExcel excel=new ExportToExcel();
			for(SaleVO i:vo){
				result=excel.toExcel(i, book, count);
				count++;
				if(result==ResultMessage.SAVE_FAIL){
					return result;
				}
			}
			book.write();
			book.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
    
    public ResultMessage ReturnToExcel(ArrayList<ReturnVO> vo,String path){
    	File file=new File(path);
    	try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			int count=0;
			ResultMessage result=ResultMessage.SUCCESS;
			ExportToExcel excel=new ExportToExcel();
			for(ReturnVO i:vo){
				result=excel.toExcel(i, book, count);
				count++;
				if(result==ResultMessage.SAVE_FAIL){
					return result;
				}
			}
			book.write();
			book.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
    
    public ResultMessage CheckInToExcel(ArrayList<CheckInVO> vo,String path){
    	File file=new File(path);
    	try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			int count=0;
			ResultMessage result=ResultMessage.SUCCESS;
			ExportToExcel excel=new ExportToExcel();
			for(CheckInVO i:vo){
				result=excel.toExcel(i, book, count);
				count++;
				if(result==ResultMessage.SAVE_FAIL){
					return result;
				}
			}
			book.write();
			book.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
    
    public ResultMessage CheckOutToExcel(ArrayList<CheckOutVO> vo,String path){
    	File file=new File(path);
    	try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			int count=0;
			ResultMessage result=ResultMessage.SUCCESS;
			ExportToExcel excel=new ExportToExcel();
			for(CheckOutVO i:vo){
				result=excel.toExcel(i, book, count);
				count++;
				if(result==ResultMessage.SAVE_FAIL){
					return result;
				}
			}
			book.write();
			book.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
    
    public ResultMessage CostToExcel(ArrayList<CostVO> vo,String path){
    	File file=new File(path);
    	try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			int count=0;
			ResultMessage result=ResultMessage.SUCCESS;
			ExportToExcel excel=new ExportToExcel();
			for(CostVO i:vo){
				result=excel.toExcel(i, book, count);
				count++;
				if(result==ResultMessage.SAVE_FAIL){
					return result;
				}
			}
			book.write();
			book.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
    
    public ResultMessage OverflowToExcel(ArrayList<OverflowVO> vo,String path){
    	File file=new File(path);
    	try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			int count=0;
			ResultMessage result=ResultMessage.SUCCESS;
			ExportToExcel excel=new ExportToExcel();
			for(OverflowVO i:vo){
				result=excel.toExcel(i, book, count);
				count++;
				if(result==ResultMessage.SAVE_FAIL){
					return result;
				}
			}
			book.write();
			book.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
    
    public ResultMessage PresentToExcel(ArrayList<PresentVO> vo,String path){
    	File file=new File(path);
    	try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			int count=0;
			ResultMessage result=ResultMessage.SUCCESS;
			ExportToExcel excel=new ExportToExcel();
			for(PresentVO i:vo){
				result=excel.toExcel(i, book, count);
				count++;
				if(result==ResultMessage.SAVE_FAIL){
					return result;
				}
			}
			book.write();
			book.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
    
}
