package gui.document.overflow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;

import javax.swing.JLabel;
import javax.swing.JPanel;

import VO.OverflowVO;

public class OverflowDialog_Uneditable extends OverflowDialog {

	private static final long serialVersionUID = 9201091441919119416L;
	private JLabel lbl_product;

	public OverflowDialog_Uneditable(Frame parent,OverflowVO vo) {
		super(parent);
		setTitle("查看报损报溢单");
		this.vo = vo;
		lbl_id.setText(vo.getID());
		txf_stock.setText(vo.getWarehouse());
		txf_stock.setEditable(false);
		lbl_product.setText(vo.getProduct().getID()+" "+vo.getProduct().getName()+" "+vo.getProduct().getModel());
		lbl_record.setText(String.valueOf(vo.getNumInSystem()));
		txf_actual.setText(String.valueOf(vo.getNumInWarehouse()));
		txf_actual.setEditable(false);
		txf_actual.setBorder(null);
		txf_actual.setBackground(new Color(this.getBackground().getRGB()));
	}

	@Override
	protected void addButtons(JPanel buttonPane) {
		//do nothing
	}

	@Override
	protected Component getProductComponent() {
		lbl_product = new JLabel("");
		return lbl_product;
	}

}
