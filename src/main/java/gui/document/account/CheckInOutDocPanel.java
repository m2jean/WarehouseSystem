package gui.document.account;

import javax.swing.JPanel;

import gui.document.component.DocPanel;
import gui.document.doclist.DocListPanel;
import gui.document.vo.CheckInOutVO;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;

public abstract class CheckInOutDocPanel extends DocPanel {

	private static final long serialVersionUID = 8315734829008509504L;

	/**
	 * Create the panel.
	 */
	public CheckInOutDocPanel(CheckInOutVO vo, int func, DocListPanel pnl_list) {
		super(vo, func, pnl_list);
				
		pnl_content.setLayout(new BoxLayout(pnl_content, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		pnl_content.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalGlue = Box.createVerticalGlue();
		panel.add(verticalGlue);
		
		JLabel lbl_customer = new JLabel("客户：和白色的有限公司（供应商）");
		lbl_customer.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lbl_customer);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		panel.add(verticalGlue_1);
		
		JLabel lbl_number = new JLabel("6条转账条目");
		lbl_number.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lbl_number);
		
		Component verticalGlue_2 = Box.createVerticalGlue();
		panel.add(verticalGlue_2);
		
		JLabel lbl_total = new JLabel("共计43545.55元");
		lbl_total.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lbl_total);
		
		Component verticalGlue_3 = Box.createVerticalGlue();
		panel.add(verticalGlue_3);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		pnl_content.add(horizontalGlue);
		
		Component horizontalStrut = Box.createHorizontalStrut(30);
		pnl_content.add(horizontalStrut);
		
		JLabel lbl_remarks = new JLabel("备注：");
		pnl_content.add(lbl_remarks);
		
		JTextArea txa_remarks = new JTextArea();
		txa_remarks.setEditable(false);
		txa_remarks.setBackground(new Color(UIManager.getColor("Panel.background").getRGB()));
		txa_remarks.setWrapStyleWord(true);
		txa_remarks.setLineWrap(true);
		txa_remarks.setFocusable(false);
		pnl_content.add(txa_remarks);
		
		lbl_customer.setText("客户："+vo.getCustomerName());
		lbl_number.setText(vo.getList().size()+"条转账条目");
		lbl_total.setText("共计："+vo.getTotal());
		if(vo.getRemarks().isEmpty())
			txa_remarks.setText("无");
		else
			txa_remarks.setText(vo.getRemarks());
	}


}
