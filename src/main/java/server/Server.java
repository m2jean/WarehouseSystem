package server;

import factory.RMIFactory;

import importData.ExportData;
import importData.ImportData;
import importDataService.ExportDataService;
import importDataService.ImportDataService;
import inventoryData.InventoryData;
import inventoryDataService.InventoryDataService;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import logData.LogData;
import accountData.AccountData;
import accountDataService.AccountDataService;
import customerData.CustomerData;
import customerDataService.CustomerDataService;
import data.promotiondata.PromotionData;
import data.userdata.UserData;
import dataservice.logdataservice.LogDataService;
import dataservice.promotiondataservice.PromotionDataService;
import dataservice.userdataservice.UserDataService;
import productData.ProductData;
import productDataService.ProductDataService;
import qiChuData.QiChuData;
import qiChuDataService.QiChuDataService;
import saleData.ReturnData;
import saleData.SaleData;
import saleDataService.ReturnDataService;
import saleDataService.SaleDataService;


public class Server {
	private String ip;
	static ProductDataService productService;
	static CustomerDataService customerService;
	static ImportDataService importService;
	static ExportDataService exportService;
	static InventoryDataService inventoryService;
	static PromotionDataService promotionService;
	static UserDataService userService;
	static SaleDataService saleService;
	static ReturnDataService returnService;
	static QiChuDataService qiChuService;
	static LogDataService logService;
	static AccountDataService accountService;
	private ServerFrame frame;

	public Server(){
		try{
			for(LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()){
				if(laf.getName().equals("Nimbus")){
					UIManager.setLookAndFeel(laf.getClassName());
					break;
				}
			}
			
			frame = new ServerFrame();
			frame.setVisible(true);
			
			getIP();
			productService=new ProductData();
			customerService=new CustomerData();
			importService=new ImportData();
			exportService=new ExportData();
			inventoryService=new InventoryData();
			promotionService=new PromotionData();
			userService=new UserData();
			saleService=new SaleData();
			returnService=new ReturnData();
			qiChuService=new QiChuData();
			logService=new LogData();
			accountService=new AccountData();
			
		}catch(Exception e){
			println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void start(){
		try{
			RMIFactory factory=new RMIFactory(ip);
			
			LocateRegistry.createRegistry(factory.getProductPort());
			Naming.rebind(factory.getProductNaming(), productService);
			println(factory.getProductNaming());
			
			LocateRegistry.createRegistry(factory.getCustomerPort());
			Naming.rebind(factory.getCustomerNaming(), customerService);
			println(factory.getCustomerNaming());
			
			LocateRegistry.createRegistry(factory.getImportPort());
			Naming.rebind(factory.getImportNaming(), importService);
			println(factory.getImportNaming());
			
			LocateRegistry.createRegistry(factory.getExportPort());
			Naming.rebind(factory.getExportNaming(), exportService);
			println(factory.getExportNaming());
			
			LocateRegistry.createRegistry(factory.getInventoryPort());
			Naming.rebind(factory.getInventoryNaming(), inventoryService);
			println(factory.getInventoryNaming());
			
			LocateRegistry.createRegistry(factory.getPromotionPort());
			Naming.rebind(factory.getPromotionNaming(), promotionService);
			println(factory.getPromotionNaming());
			
			LocateRegistry.createRegistry(factory.getUserPort());
			Naming.rebind(factory.getUserNaming(), userService);
			println(factory.getUserNaming());
			
			
			LocateRegistry.createRegistry(factory.getSalePort());
			Naming.rebind(factory.getSaleNaming(), saleService);
			println(factory.getSaleNaming());
			
			LocateRegistry.createRegistry(factory.getReturnPort());
			Naming.rebind(factory.getReturnNaming(), returnService);
			println(factory.getReturnNaming());
			
			LocateRegistry.createRegistry(factory.getQiChuPort());
			Naming.rebind(factory.getQiChuNaming(), qiChuService);
			println(factory.getQiChuNaming());
			
			LocateRegistry.createRegistry(factory.getLogPort());
			Naming.rebind(factory.getLogNaming(), logService);
			println(factory.getLogNaming());
			
			LocateRegistry.createRegistry(factory.getAccountPort());
			Naming.rebind(factory.getAccountNaming(), accountService);
			println(factory.getAccountNaming());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void getIP(){
		try{
			InetAddress addr = InetAddress.getLocalHost();
			ip=addr.getHostAddress().toString();
			System.out.println(ip);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public static void main(String args[]){
		Server server=new Server();
		server.start();
	}
	
	private void println(String str){
		frame.append(str+"\r\n");
	}
}
