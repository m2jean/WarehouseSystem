package productBL;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.tree.DefaultMutableTreeNode;

import productBLService.CategoryBLService;
import productDataService.CategoryDataService;
import PO.CategoryPO;
import VO.CategoryVO;
import enums.DataMessage;
import enums.ResultMessage;
import factory.CategoryTree;
import factory.RMIFactory;

public class CategoryBL implements CategoryBLService{
	
	public ResultMessage addCategory(CategoryVO vo){
		try{
			//CategoryDataService dataService=new ProductData();
			CategoryDataService dataService=new RMIFactory().newCategoryDataService();
			String id=dataService.add(vo.toPO());
			if(id.equals("ITEM_EXISTS")){
				return ResultMessage.ITEM_EXIST;
			}else if(id.equals("ITEM_NOT_EXISTS")){
				return ResultMessage.ITEM_NOT_EXIST;
			}else if(id.equals("CANNOT_ADD")){
				return ResultMessage.CANNOT_ADD;
			}
			else{
				//log
				return ResultMessage.SUCCESS;
			}
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage deleteCategory(CategoryVO vo){
		try{
			//CategoryDataService dataService=new ProductData();
			CategoryDataService dataService=new RMIFactory().newCategoryDataService();
			return dataService.delete(vo.toPO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public ResultMessage updateCategory(CategoryVO vo){
		try{
			//CategoryDataService dataService=new ProductData();
			CategoryDataService dataService=new RMIFactory().newCategoryDataService();
			return dataService.update(vo.toPO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
	}
	
	public DataMessage<CategoryVO> getCategory(CategoryVO vo){
		try{
			//CategoryDataService dataService=new ProductData();
			CategoryDataService dataService=new RMIFactory().newCategoryDataService();
			CategoryPO po=dataService.get(vo.toPO());
			if(po==null){
				return new DataMessage<CategoryVO>(ResultMessage.ITEM_NOT_EXIST);
			}
		
			return new DataMessage<CategoryVO>(po.toVO());
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return new DataMessage<CategoryVO>(ResultMessage.REMOTE_FAIL);
		}
	}
	
	public ResultMessage getCategoryTree(DefaultMutableTreeNode root){
		try{
			//CategoryDataService dataService=new ProductData();
			CategoryDataService dataService=new RMIFactory().newCategoryDataService();
			CategoryTree tree=dataService.getAllCategory();
			tree.toJTreeNode(root);
			return ResultMessage.SUCCESS;
		}catch(RemoteException | MalformedURLException | NotBoundException e){
			return ResultMessage.REMOTE_FAIL;
		}
		
	}

}
