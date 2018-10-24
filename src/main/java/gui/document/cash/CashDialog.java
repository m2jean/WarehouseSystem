package gui.document.cash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import VO.CostVO;
import businesslogic.userbl.UserBL;
import enums.ResultMessage;
import enums.Status;
import gui.GUIUtility;
import gui.ResultDialog;

public abstract class CashDialog extends ResultDialog {

	private static final long serialVersionUID = 2367050636548807748L;
	protected final JPanel contentPanel = new JPanel();
	protected CostVO vo;
	private JLabel lbl_id;
	protected JLabel lbl_account;
	private JLabel lbl_operator;
	protected CashItemPanel pnl_item;
	protected JLabel lbl_msg;


	/**
	 * Create the dialog.
	 */

	public CashDialog(Frame parent,CostVO vo) {
		super(parent,true);
		this.vo = vo;
		construct();
		if(vo.getStatus() != Status.DRAFT){
			lbl_id.setText("编号："+vo.getID());
			lbl_operator.setText("操作员："+vo.getOperator());
		}
		else
			lbl_operator.setText("操作员："+new UserBL().getCurrent().getName());
	}
		
	private void construct(){

		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				setResult(ResultMessage.CLOSE);
				setVisible(false);
			}
		});
		setResizable(false);
		setSize(494, 300);
		GUIUtility.setCenter(this);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 15, 5, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{160, 79, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			lbl_id = new JLabel("编号：");
			GridBagConstraints gbc_lbl_id = new GridBagConstraints();
			gbc_lbl_id.anchor = GridBagConstraints.WEST;
			gbc_lbl_id.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_id.gridx = 0;
			gbc_lbl_id.gridy = 0;
			contentPanel.add(lbl_id, gbc_lbl_id);
		}
		{
			lbl_account = new JLabel("账户：");
			GridBagConstraints gbc_lbl_customer = new GridBagConstraints();
			gbc_lbl_customer.anchor = GridBagConstraints.EAST;
			gbc_lbl_customer.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_customer.gridx = 2;
			gbc_lbl_customer.gridy = 0;
			contentPanel.add(lbl_account, gbc_lbl_customer);
		}
		{
			GridBagConstraints gbc_cbx_customer = new GridBagConstraints();
			gbc_cbx_customer.insets = new Insets(0, 0, 5, 0);
			gbc_cbx_customer.fill = GridBagConstraints.HORIZONTAL;
			gbc_cbx_customer.gridx = 3;
			gbc_cbx_customer.gridy = 0;
			setAccountComponent(contentPanel,gbc_cbx_customer);
		}
		{
			lbl_operator = new JLabel("操作员：撒低洼");
			GridBagConstraints gbc_lbl_operator = new GridBagConstraints();
			gbc_lbl_operator.anchor = GridBagConstraints.WEST;
			gbc_lbl_operator.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_operator.gridx = 0;
			gbc_lbl_operator.gridy = 1;
			contentPanel.add(lbl_operator, gbc_lbl_operator);
		}
		{
			pnl_item = getItemPanel();
			GridBagConstraints gbc_pnl_item = new GridBagConstraints();
			gbc_pnl_item.gridwidth = 4;
			gbc_pnl_item.insets = new Insets(0, 0, 0, 5);
			gbc_pnl_item.fill = GridBagConstraints.BOTH;
			gbc_pnl_item.gridx = 0;
			gbc_pnl_item.gridy = 2;
			contentPanel.add(pnl_item, gbc_pnl_item);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.X_AXIS));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			buttonPane.add(Box.createHorizontalGlue());
			lbl_msg = new JLabel();
			lbl_msg.setForeground(Color.RED);
			buttonPane.add(lbl_msg);
			
			buttonPane.add(Box.createHorizontalStrut(10));
			addButtons(buttonPane);
			buttonPane.add(Box.createHorizontalStrut(10));
			{
				JButton btn_cancel = new JButton("取消");
				btn_cancel.setActionCommand("Cancel");
				btn_cancel.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						close();
					}
				});
				buttonPane.add(btn_cancel);
			}
			buttonPane.add(Box.createHorizontalStrut(5));
		}
	}
	
	protected abstract void addButtons(JPanel buttonPane);

	protected abstract void setAccountComponent(JPanel contnetPanel,Object gbc_cbx_customer);

	protected abstract CashItemPanel getItemPanel();

}
