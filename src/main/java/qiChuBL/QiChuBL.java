package qiChuBL;

import java.net.MalformedURLException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import importBL.ExportBL;
import importBL.ImportBL;
import importBLService.ExportBLService;
import importBLService.ImportBLService;
import inventoryBL.InventoryBL;
import inventoryBLService.InventoryBLService;
import productBL.CategoryBL;
import productBL.ProductBL;
import productBLService.CategoryBLService;
import productBLService.ProductBLService;
import customerBL.CustomerBL;
import customerBLService.CustomerBLService;
import businesslogic.logbl.LogBLStub;
import businesslogic.messagebl.MessageBL;
import businesslogic.promotionbl.PromotionBLStub;
import businesslogic.userbl.UserBL;
import businesslogicservice.logblservice.LogBLService;
import businesslogicservice.messageblservice.MessageBLService;
import businesslogicservice.promotionblservice.PromotionBLService;
import businesslogicservice.userblservice.UserBLService;
import accountBL.AccountBL;
import accountBL.CheckInBL;
import accountBL.CheckOutBL;
import accountBL.CostBL;
import accountBLService.AccountBLService;
import accountBLService.CheckInBLService;
import accountBLService.CheckOutBLService;
import accountBLService.CostBLService;
import VO.QiChuVO;
import enums.DataMessage;
import enums.ResultMessage;
import factory.RMIFactory;
import qiChuBLService.QiChuBLService;
import qiChuDataService.QiChuDataService;
import saleBL.ReturnBL;
import saleBL.SaleBL;
import saleBLService.ReturnBLService;
import saleBLService.SaleBLService;

public class QiChuBL implements QiChuBLService{
	AccountBLService account=new AccountBL();
	CheckInBLService checkin=new CheckInBL();
	CheckOutBLService checkout=new CheckOutBL();
	CostBLService cost=new CostBL();
	LogBLService log=new LogBLStub();
	MessageBLService message=new MessageBL();
	PromotionBLService promotion=new PromotionBLStub();
	UserBLService user=new UserBL();
	CustomerBLService customer=new CustomerBL();
	ImportBLService imports=new ImportBL();
	ExportBLService exports=new ExportBL();
	SaleBLService sales=new SaleBL();
	ReturnBLService returns=new ReturnBL();
	InventoryBLService inventory=new InventoryBL();
	ProductBLService product=new ProductBL();
	CategoryBLService category=new CategoryBL();

	public ResultMessage Initial() {
		RMIFactory rmi=new RMIFactory();
		QiChuVO qc=new QiChuVO();
		qc.setAccount(account.getAll().data);
		qc.setCheckIn(checkin.getAllReceipt().data);
		qc.setCheckOut(checkout.getAllPayment().data);
        qc.setCost(cost.getAllCost().data);
		qc.setCustomer(customer.getAllCustomer().data);
		qc.setExports(exports.getExportList().data);
		qc.setImports(imports.getImportList().data);
		qc.setOverflow(inventory.getAllOverflow().data);
		qc.setPresent(inventory.getAllPresentTable().data);
		qc.setProduct(product.getProductList().data);
		qc.setPromotion(promotion.getUnexpired().data);
		qc.setReturns(returns.AllReturn().data);
		qc.setSales(sales.AllSale().data);
		try {
			QiChuDataService qichu=rmi.newQiChuDataService();
			return qichu.newQiChu(qc.toPO());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return ResultMessage.REMOTE_FAIL;
		}
	}

	public DataMessage<QiChuVO> Check(QiChuVO vo) {
		RMIFactory rmi=new RMIFactory();
		DataMessage<QiChuVO> result=new DataMessage<QiChuVO>(ResultMessage.SUCCESS);
		try {
			QiChuDataService qichu=rmi.newQiChuDataService();
			result.data=qichu.findQiChu(vo.getID()).toVO();
			if(result.data==null){
				result.resultMessage=ResultMessage.ITEM_NOT_EXIST;
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			result.resultMessage=ResultMessage.REMOTE_FAIL;
		}
		return result;
	}
	
}
