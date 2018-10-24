package gui.product;

import enums.ResultMessage;
import gui.GUIUtility;
import gui.ResultDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;

import productBL.ProductBL;
import VO.ProductVO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlarmDialog extends ResultDialog {

	private static final long serialVersionUID = -3614506507074063553L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txf_alarm;
	private JLabel lbl_product;
	private ProductVO vo;

	/**
	 * Create the dialog.
	 */
	public AlarmDialog(Frame parent,ProductVO vo) {
		super(parent,true);
		this.vo = vo;
		
		setTitle("设置报警单");
		setResizable(false);
		setSize(303,123);
		GUIUtility.setCenter(this);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{70, 152, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			lbl_product = new JLabel("商品：");
			GridBagConstraints gbc_lbl_product = new GridBagConstraints();
			gbc_lbl_product.gridwidth = 3;
			gbc_lbl_product.anchor = GridBagConstraints.WEST;
			gbc_lbl_product.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_product.gridx = 0;
			gbc_lbl_product.gridy = 0;
			contentPanel.add(lbl_product, gbc_lbl_product);
		}
		{
			JLabel label = new JLabel("警戒数量：");
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.WEST;
			gbc_label.insets = new Insets(0, 0, 0, 5);
			gbc_label.gridx = 0;
			gbc_label.gridy = 1;
			contentPanel.add(label, gbc_label);
		}
		{
			txf_alarm = new JTextField();
			GridBagConstraints gbc_txf_alarm = new GridBagConstraints();
			gbc_txf_alarm.insets = new Insets(0, 0, 0, 5);
			gbc_txf_alarm.fill = GridBagConstraints.HORIZONTAL;
			gbc_txf_alarm.gridx = 1;
			gbc_txf_alarm.gridy = 1;
			contentPanel.add(txf_alarm, gbc_txf_alarm);
			txf_alarm.setColumns(10);
			//txf_alarm.addFocusListener(GUIUtility.getFocusListenerForInt(txf_alarm, this));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btn_confirm = new JButton("确定");
				btn_confirm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!txf_alarm.getText().trim().matches("\\d+")){
							JOptionPane.showMessageDialog(AlarmDialog.this, "请输入合法整数！");
							return;
						}
						AlarmDialog.this.vo.setLowerLimit(Integer.parseInt(txf_alarm.getText().trim()));
						
						ResultMessage result = new ProductBL().updateProduct(AlarmDialog.this.vo);
						switch(result){
						case REMOTE_FAIL:
							JOptionPane.showMessageDialog(AlarmDialog.this, "通信错误！");
							break;
						case SUCCESS:
							JOptionPane.showMessageDialog(AlarmDialog.this, "操作成功！");
							setVisible(false);
							break;
						default:
							JOptionPane.showMessageDialog(AlarmDialog.this, "未知错误！");
							break;
						}
					}
				});
				btn_confirm.setActionCommand("OK");
				buttonPane.add(btn_confirm);
				getRootPane().setDefaultButton(btn_confirm);
			}
			{
				JButton btn_cancel = new JButton("取消");
				btn_cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btn_cancel.setActionCommand("Cancel");
				buttonPane.add(btn_cancel);
			}
		}
		
		lbl_product.setText("商品："+vo.getID()+" "+vo.getName()+" "+vo.getModel());
		txf_alarm.setText(String.valueOf(vo.getLowerLimit()));
	}

}
