package productBL;


import javax.swing.tree.DefaultMutableTreeNode;

import VO.CategoryVO;
import enums.DataMessage;
import enums.ResultMessage;
import productBLService.CategoryBLService;
import PO.*;
import productData.CategoryDataStub;

public class CategoryBLStub implements CategoryBLService{
	public ResultMessage addCategory(CategoryVO vo){
		new CategoryDataStub().add(new CategoryPO(null, null, null, false, null, null));
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage deleteCategory(CategoryVO vo){
		new CategoryDataStub().delete(new CategoryPO(null, null, null, false, null, null));
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage updateCategory(CategoryVO vo){
		new CategoryDataStub().update(new CategoryPO(null, null, null, false, null, null));
		return ResultMessage.SUCCESS;
	}
	
	public DataMessage<CategoryVO> getCategory(CategoryVO vo){
		new CategoryDataStub().get(new CategoryPO(null, null, null, false, null, null));
		return new DataMessage<CategoryVO>(new CategoryVO(null, null, null, false, null, null));
	}
	
	public ResultMessage getCategoryTree(DefaultMutableTreeNode root){
		return ResultMessage.SUCCESS;
		
	}

}
