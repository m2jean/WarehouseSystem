package gui.document.imports;

import VO.ImportVO;
import businesslogic.userbl.UserBL;
import gui.MainFrame;

public class ExportDialog_Add extends ExportDialog {

	private static final long serialVersionUID = 4160170655765339371L;

	public ExportDialog_Add(MainFrame parent,ImportVO imp) {
		super(parent,imp);
		setTitle("添加退货单");
		vo.setOperator(new UserBL().getCurrent().getName());
		setOperator(vo.getOperator());
	}

}
