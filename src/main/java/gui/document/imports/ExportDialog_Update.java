package gui.document.imports;

import VO.ExportVO;
import enums.Status;
import gui.MainFrame;

public class ExportDialog_Update extends ExportDialog {

	private static final long serialVersionUID = 3711242961696034099L;

	public ExportDialog_Update(MainFrame parent,ExportVO vo) {
		super(parent,vo);
		setTitle("修改退货单");
		if(vo.getStatus() != Status.DRAFT)
			lbl_id.setText("编号："+vo.getID());
	}

}
