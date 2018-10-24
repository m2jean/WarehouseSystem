package factory;

import importDataService.ExportDataService;
import importDataService.ImportDataService;
import inventoryDataService.InventoryDataService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import accountDataService.AccountDataService;
import customerDataService.CustomerDataService;
import dataservice.logdataservice.LogDataService;
import dataservice.promotiondataservice.PromotionDataService;
import dataservice.userdataservice.UserDataService;
import productDataService.CategoryDataService;
import productDataService.ProductDataService;
import qiChuDataService.QiChuDataService;
import saleDataService.ReturnDataService;
import saleDataService.SaleDataService;

public class RMIFactory {
	private String ip;
	private int productPort;
	private int customerPort;
	private int importPort;
	private int exportPort;
	private int inventoryPort;
	private int promotionPort;
	private int userPort;
	private int salePort;
	private int returnPort;
	private int qiChuPort;
	private int logPort;
	private int accountPort;

	public RMIFactory(String ip){
		this.ip=ip;
		setPort();
	}
	
	public RMIFactory(){
		File file = new File("IP.txt");
		try{
			if(!file.exists()){
				ip="127.0.0.1";
				file.createNewFile();
				FileWriter fw = new FileWriter(file);
				fw.write(ip);
				fw.close();
			}else{
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				ip = br.readLine();
				br.close();
				fr.close();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		setPort();
	}
	
	private void setPort(){
		productPort=5001;
		customerPort=5003;
		importPort=5004;
		exportPort=5005;
		inventoryPort=5006;
		promotionPort=5007;
		userPort=5008;
		salePort=5009;
		returnPort=5010;
		qiChuPort=5011;
		logPort=5012;
		accountPort=5013;
	}

	public int getProductPort() {
		return productPort;
	}

	public int getCategoryPort() {             //与product共用同一个data
		return productPort;
	}

	public int getCustomerPort() {
		return customerPort;
	}

	public int getImportPort() {
		return importPort;
	}

	public int getExportPort() {
		return exportPort;
	}

	public int getInventoryPort() {
		return inventoryPort;
	}

	public int getPromotionPort() {
		return promotionPort;
	}
	
	public int getUserPort() {
		return userPort;
	}

	public int getSalePort() {
		return salePort;
	}

	public int getReturnPort() {
		return returnPort;
	}

	public int getQiChuPort() {
		return qiChuPort;
	}

	public int getLogPort() {
		return logPort;
	}

	public int getAccountPort() {
		return accountPort;
	}
	
	public String getProductNaming(){
		return getNaming(productPort, "productService");
	}
	
	public String getCategoryNaming(){                       //与product共用同一个端口
		return getNaming(productPort, "productService");
	}
	
	public String getCustomerNaming(){
		return getNaming(customerPort, "customerService");
	}
	
	public String getImportNaming(){
		return getNaming(importPort, "importService");
	}
	
	public String getExportNaming(){
		return getNaming(exportPort, "exportService");
	}
	
	public String getInventoryNaming(){
		return getNaming(inventoryPort, "inventoryService");
	}
	
	public String getPromotionNaming(){
		return getNaming(promotionPort, "promotionService");
	}
	
	public String getUserNaming(){
		return getNaming(userPort, "userService");
	}
	
	public String getSaleNaming(){
		return getNaming(salePort, "saleService");
	}
	
	public String getReturnNaming(){
		return getNaming(returnPort, "returnService");
	}
	
	public String getQiChuNaming(){
		return getNaming(qiChuPort, "qiChuService");
	}
	
	public String getAccountNaming(){
		return getNaming(accountPort, "accountService");
	}
	
	public String getLogNaming(){
		return getNaming(logPort, "logService");
	}
	
	private String getNaming(int port, String service){
		StringBuffer sb=new StringBuffer("rmi://");
		sb.append(ip);
		sb.append(":");
		sb.append(port);
		sb.append("/");
		sb.append(service);
		return sb.toString();
	}
	
	public ProductDataService newProductDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (ProductDataService)Naming.lookup(getProductNaming());
	}
	
	public CategoryDataService newCategoryDataService() throws MalformedURLException, RemoteException, NotBoundException{
		return (CategoryDataService)Naming.lookup(getCategoryNaming());
	}
	
	public CustomerDataService newCustomerDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (CustomerDataService)Naming.lookup(getCustomerNaming());
	}
	
	public ImportDataService newImportDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (ImportDataService)Naming.lookup(getImportNaming());
	}
	
	public ExportDataService newExportDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (ExportDataService)Naming.lookup(getExportNaming());
	}
	
	public InventoryDataService newInventoryDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (InventoryDataService)Naming.lookup(getInventoryNaming());
	}
	
	public PromotionDataService newPromotionDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (PromotionDataService)Naming.lookup(getPromotionNaming());
	}
	
	public UserDataService newUserDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (UserDataService)Naming.lookup(getUserNaming());
	}
	
	public SaleDataService newSaleDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (SaleDataService)Naming.lookup(getSaleNaming());
	}
	
	public ReturnDataService newReturnDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (ReturnDataService)Naming.lookup(getReturnNaming());
	}
	
	public QiChuDataService newQiChuDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (QiChuDataService)Naming.lookup(getQiChuNaming());
	}
	
	public LogDataService newLogDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (LogDataService)Naming.lookup(getLogNaming());
	}
	
	public AccountDataService newAccountDataService() throws RemoteException, MalformedURLException, NotBoundException{
		return (AccountDataService)Naming.lookup(getAccountNaming());
	}
}
