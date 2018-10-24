package gui.document.imports;

import gui.MainFrame;

import java.awt.GridBagConstraints;
import javax.swing.JPanel;

import VO.ImportVO;
import businesslogic.userbl.UserBL;

public class ImportDialog_Add extends ImportDialog_Editable {

	private static final long serialVersionUID = -4362466935427539531L;

	public ImportDialog_Add(MainFrame parent) {
		super(parent);
		setTitle("添加进货单");
		vo = new ImportVO("");
		vo.setOperator(new UserBL().getCurrent().getName());
		setOperator(vo.getOperator());
	}

	@Override
	protected void setLineItemPanel(JPanel contentPanel, GridBagConstraints gbc) {
		pnl_item =new ImportLineItemPanel_Editable(this);
		contentPanel.add(pnl_item, gbc);
	}
}
