package productBLService;

import javax.swing.tree.DefaultMutableTreeNode;
import VO.CategoryVO;
import enums.DataMessage;
import enums.ResultMessage;

public interface CategoryBLService {
	public ResultMessage addCategory(CategoryVO vo);
	public ResultMessage deleteCategory(CategoryVO vo);
	public ResultMessage updateCategory(CategoryVO vo);
	public DataMessage<CategoryVO> getCategory(CategoryVO vo);
	public ResultMessage getCategoryTree(DefaultMutableTreeNode root);
}
