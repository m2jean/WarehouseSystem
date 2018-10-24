package productData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import productBL.CostChange;
import productBL.ProductItem;
import productDataService.CategoryDataService;
import productDataService.ProductDataService;
import PO.CategoryPO;
import PO.ProductPO;
import PO.SpecialProductPO;
import enums.ResultMessage;
import factory.CategoryTree;
import factory.Factory;
import factory.Helper;
import factory.Info;

public class ProductData extends UnicastRemoteObject implements ProductDataService, CategoryDataService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7108323272704279644L;
	ArrayList<ProductPO> productList;
	ArrayList<SpecialProductPO> specialProductList;
	CategoryTree tree;
	
	public ProductData() throws RemoteException{
		initProduct();
		initSpecial();
		initCategory();
	}
	
	private void initProduct(){
		productList=new ArrayList<ProductPO>();
		File file=new File("productdata.ser");
		if(file.exists()){
			try{
				FileInputStream fis=new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				ProductPO po;
				while(fis.available()>0){
					po=(ProductPO)ois.readObject();
					productList.add(po);
				}
				ois.close();
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void initSpecial(){
		specialProductList=new ArrayList<SpecialProductPO>();
		File file=new File("specialproductdata.ser");
		if(file.exists()){
			try{
				FileInputStream fis=new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				SpecialProductPO po;
				while(fis.available()>0){
					po=(SpecialProductPO)ois.readObject();
					specialProductList.add(po);
				}
				ois.close();
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void initCategory(){
		tree=new CategoryTree();
		File file=new File("categorydata.ser");
		if(file.exists()){
			try{
				FileInputStream fis=new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				tree=(CategoryTree)ois.readObject();
				ois.close();
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private ResultMessage saveProduct(){
		File file=new File("productdata.ser");
		try{
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			for(ProductPO po:productList){
				oos.writeObject(po);
			}
			oos.close();
			fos.close();
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	private ResultMessage saveSpecial(){
		File file=new File("specialProductdata.ser");
		try{
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			for(SpecialProductPO po:specialProductList){
				oos.writeObject(po);
			}
			oos.close();
			fos.close();
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	private ResultMessage saveCategory(){
		File file=new File("categorydata.ser");
		try{
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(tree);
			oos.close();
			fos.close();
			return ResultMessage.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ResultMessage.SAVE_FAIL;
		}
	}
	
	public String add(ProductPO po){
		for(ProductPO product:productList){
			if(po.equals(product)){
				return "ITEM_EXIST";
			}
		}
		
		String id=new Factory().getProductID();
		po.setID(id);
		ResultMessage message = tree.addProduct(po);
		if(message == ResultMessage.CANNOT_ADD){
			return "CANNOT_ADD";
		}
		productList.add(po);
		saveCategory();
		saveProduct();
		return id;
	}
	
	public ResultMessage delete(ProductPO po){
		for(ProductPO product:productList){
			if(po.getID().equals(product.getID())){
				tree.deleteProduct(product);
				productList.remove(product);
				saveCategory();
				return saveProduct();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	public ResultMessage update(ProductPO po){
		for(ProductPO product:productList){
			if(po.getID().equals(product.getID())){
				if(po.getPriceIn()!=product.getPriceIn()){
					double change=po.getNumber()*po.getPriceIn()-product.getPriceIn()*product.getNumber();
					saveCostChange(change);
				}
				productList.remove(product);
				productList.add(po);
				Collections.sort(productList);
				return saveProduct();
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	
	private void saveCostChange(double change){
		String date=new Factory().getDate();
		CostChange cc=new CostChange(date, change);
		try{
			ArrayList<CostChange> list=getCostChange();
			list.add(cc);
			File file=new File("costchange.ser");
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			for(CostChange obj:list){
				oos.writeObject(obj);
			}
			oos.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<CostChange> getCostChange() throws RemoteException{
		File file=new File("costchange.ser");
		ArrayList<CostChange> list=new ArrayList<CostChange>();
		if(file.exists()){
			try{
				FileInputStream fis=new FileInputStream(file);
				ObjectInputStream ois=new ObjectInputStream(fis);
				while(fis.available()>0){
					CostChange cc=(CostChange)ois.readObject();
					list.add(cc);
				}
				ois.close();
				fis.close();
				return list;
			}catch(Exception e){
				return list;
			}
		}else{
			return list;
		}
	}
	
	public ProductPO get(ProductPO po){
		for(ProductPO product:productList){
			if(po.getID().equals(product.getID())){
				return new ProductPO(product);
			}
		}
		
		return null;		
	}
	
	public ArrayList<ProductPO> get(String id, String name){ //keyword
		ArrayList<ProductPO> list=new ArrayList<ProductPO>();
		Helper helper=new Helper();
		for(ProductPO po:productList){
			if(id==null||helper.StringContains(po.getID(), id)){
				if(name==null||helper.StringContains(po.getName(), name)){
					list.add(new ProductPO(po));
				}
			}
		}
		
		return list;
	}
	
	public ArrayList<ProductPO> getAllProduct(){
		ArrayList<ProductPO> list=new ArrayList<ProductPO>();
		for(ProductPO po:productList){
			list.add(new ProductPO(po));
		}
		
		return list;
	}
	
	//*****************************************************************
	
	public String add(CategoryPO po){
		if(getByName(po)!=null){
			return "ITEM_EXISTS";
		}

		String id=new Factory().getCategoryID();
		po.setID(id);
		try{
			tree.add(po);			
			return "ITEM_NOT_EXISTS";
		}catch(Info s){
			if(s.success()){
				saveCategory();
				return "SUCCESS";
			}else{
				return "CANNOT_ADD";
			}
		}
	}
	
	public ResultMessage delete(CategoryPO po){
		try{
			tree.delete(po);
			return ResultMessage.ITEM_NOT_EXIST;
		}catch(Info e){
			return saveCategory();
		}
	}
	
	public ResultMessage update(CategoryPO po){
		try{
			tree.update(po);
			return ResultMessage.ITEM_NOT_EXIST;
		}catch(Info e){
			return saveCategory();
		}
	}
	
	public CategoryPO get(CategoryPO po){
		CategoryPO category=tree.get(po);
		if(category==null){
			return null;
		}
		return new CategoryPO(category);
	}
	
	private CategoryPO getByName(CategoryPO po){
		CategoryPO category=tree.getByName(po.getName());
		if(category!=null){
			return new CategoryPO(category);
		}else{
			return null;
		}
	}
	
	public CategoryTree getAllCategory(){
		return tree;
	}


	public String addSpecial(SpecialProductPO po) {
		for(SpecialProductPO product:specialProductList){
			if(sameSpecialProduct(product, po)){
				return "ITEM_EXIST";
			}
		}
		
		Factory factory=new Factory();
		String id=factory.getSpecialProductID();
		po.setID(id);
		specialProductList.add(po);
		saveSpecial();
		return id;
	}
	
	private boolean sameSpecialProduct(SpecialProductPO p1, SpecialProductPO p2){
		ArrayList<ProductItem> list1=p1.getProductList();
		ArrayList<ProductItem> list2=p2.getProductList();
		
		if(list1.size()!=list2.size()){
			return false;
		}else{
			for(ProductItem item1:list1){
				boolean find=false;
				
				for(ProductItem item2:list2){
					if(item1.getProductID().equals(item2.getProductID())){
						find=true;
						break;
					}
				}
				
				if(!find){
					return false;
				}
			}
		}
		
		return true;
	}

	public ResultMessage deleteSpecial(SpecialProductPO po) throws RemoteException {
		for(SpecialProductPO product:specialProductList){
			if(product.getID().equals(po.getID())){
				specialProductList.remove(product);
				saveSpecial();
				return ResultMessage.SUCCESS;
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}

	public ResultMessage updateSpecial(SpecialProductPO po) throws RemoteException {
		for(SpecialProductPO product:specialProductList){
			if(product.getID().equals(po.getID())){
				specialProductList.remove(product);
				specialProductList.add(po);
				Collections.sort(specialProductList);
				saveSpecial();
				return ResultMessage.SUCCESS;
			}
		}
		
		return ResultMessage.ITEM_NOT_EXIST;
	}
	
	public SpecialProductPO getSpecial(SpecialProductPO po) {
		for(SpecialProductPO product:specialProductList){
			if(product.getID().equals(po.getID())){
				return new SpecialProductPO(product);
			}
		}
		
		return null;
	}

	public ArrayList<SpecialProductPO> getAllSpecial() throws RemoteException {
		ArrayList<SpecialProductPO> list=new ArrayList<SpecialProductPO>();
		for(SpecialProductPO product:specialProductList){
			list.add(new SpecialProductPO(product));
		}
		
		return list;
	}

}
