package productData;

import java.util.ArrayList;

import PO.CategoryPO;
import enums.ResultMessage;

public class CategoryDataStub /*implements CategoryDataService*/{
	public ResultMessage add(CategoryPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage delete(CategoryPO po){
		return ResultMessage.SUCCESS;
	}
	
	public ResultMessage update(CategoryPO po){
		return ResultMessage.SUCCESS;
	}
	
	public CategoryPO get(CategoryPO po){
		return new CategoryPO(null, null, null, false, null, null);
	}
	
	public ArrayList<CategoryPO> getTopCategory(){
		return new ArrayList<CategoryPO>();
	}

}
