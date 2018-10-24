package gui.account;

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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import VO.AccountVO;
import accountBL.AccountBL;
import enums.ResultMessage;
import gui.DefaultTextField;
import gui.GUIUtility;
import gui.ResultDialog;

public class AccountDialog extends ResultDialog {

	private static final long serialVersionUID = 7852001110234014177L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txf_name;
	private JTextArea txa_dscrpt;
	private JTextField txf_balance;
	private JLabel lbl_msg;
	private JButton btn_confirm;
	AccountVO account;

	public AccountDialog(Frame parent, AccountVO account){
		this(parent);
		this.account = account;
		txf_name.setText(account.getName());
		txf_balance.setText(GUIUtility.formatDouble(account.getBalance()));
		txf_balance.setFocusable(false);
		txf_balance.setEditable(false);
		txf_balance.setBorder(null);
		txf_balance.setBackground(new Color(this.getBackground().getRGB()));
		txa_dscrpt.setText(account.getDescription());
	}
	public AccountDialog(Frame parent) {
		super(parent,true);
		
		setTitle("添加账户");
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				close();
			}
		});
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 20, 5, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lbl_name = new JLabel("账户名称：");
			GridBagConstraints gbc_lbl_name = new GridBagConstraints();
			gbc_lbl_name.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_name.anchor = GridBagConstraints.WEST;
			gbc_lbl_name.gridx = 0;
			gbc_lbl_name.gridy = 0;
			contentPanel.add(lbl_name, gbc_lbl_name);
		}
		{
			txf_name = new JTextField();
			txf_name.addCaretListener(new CaretListener(){
				@Override
				public void caretUpdate(CaretEvent e) {
					if(txf_name.getText().trim().isEmpty())
						btn_confirm.setEnabled(false);
					else
						btn_confirm.setEnabled(true);
				}
			});
			GridBagConstraints gbc_txf_name = new GridBagConstraints();
			gbc_txf_name.insets = new Insets(0, 0, 5, 0);
			gbc_txf_name.fill = GridBagConstraints.HORIZONTAL;
			gbc_txf_name.gridx = 1;
			gbc_txf_name.gridy = 0;
			contentPanel.add(txf_name, gbc_txf_name);
			txf_name.setColumns(10);
		}
		{
			JLabel label = new JLabel("初始金额：");
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.EAST;
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 0;
			gbc_label.gridy = 1;
			contentPanel.add(label, gbc_label);
		}
		{
			txf_balance = new DefaultTextField("0");
			GridBagConstraints gbc_txf_balance = new GridBagConstraints();
			gbc_txf_balance.insets = new Insets(0, 0, 5, 0);
			gbc_txf_balance.fill = GridBagConstraints.HORIZONTAL;
			gbc_txf_balance.gridx = 1;
			gbc_txf_balance.gridy = 1;
			contentPanel.add(txf_balance, gbc_txf_balance);
			//txf_balance.addFocusListener(GUIUtility.getFocusListenerForDouble(txf_balance, this));
			txf_balance.setColumns(10);
		}
		{
			JLabel lbl_description = new JLabel("描述：");
			GridBagConstraints gbc_lbl_description = new GridBagConstraints();
			gbc_lbl_description.anchor = GridBagConstraints.WEST;
			gbc_lbl_description.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_description.gridx = 0;
			gbc_lbl_description.gridy = 2;
			contentPanel.add(lbl_description, gbc_lbl_description);
		}
		{			
			txa_dscrpt = new JTextArea();
			txa_dscrpt.setRows(3);
			txa_dscrpt.setLineWrap(true);
			GridBagConstraints gbc_txa_description = new GridBagConstraints();
			gbc_txa_description.gridwidth = 2;
			gbc_txa_description.fill = GridBagConstraints.BOTH;
			gbc_txa_description.gridx = 0;
			gbc_txa_description.gridy = 3;
			
			JScrollPane scp_dscrpt = new JScrollPane(txa_dscrpt);
			contentPanel.add(scp_dscrpt, gbc_txa_description);
		}
		{
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.X_AXIS));
			buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

			buttonPane.add(Box.createHorizontalGlue());
			lbl_msg = new JLabel();
			lbl_msg.setForeground(Color.RED);
			buttonPane.add(lbl_msg);

			buttonPane.add(Box.createHorizontalStrut(10));
			{
				btn_confirm = new JButton("确定");
				btn_confirm.setEnabled(false);
				btn_confirm.setActionCommand("OK");
				btn_confirm.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String name = txf_name.getText().trim();
						String balance = txf_balance.getText().trim();
						if(name.isEmpty()){
							lbl_msg.setText("账户名称不能为空!");
							txf_name.requestFocus();
							return;
						}
						else if(!balance.matches("\\d*\\.?\\d+")){
							lbl_msg.setText("请输入合理金额!");
							txf_balance.requestFocus();
							return;
						}

						ResultMessage result;
						if(txf_balance.isEditable()){
							account = new AccountVO("",name,Double.parseDouble(balance),txa_dscrpt.getText());
							result = new AccountBL().addAccount(account);
						}
						else{
							account.setName(name);
							account.setDescription(txa_dscrpt.getText());
							result = new AccountBL().updAccount(account);
						}
						setResult(result);
						switch(result){
						case REMOTE_FAIL:
							lbl_msg.setText("通信错误!");
							break;
						case SUCCESS:
							setVisible(false);
							break;
						default:
							lbl_msg.setText("未知错误!");
							break;
						}
					}
				});
				buttonPane.add(btn_confirm);
				getRootPane().setDefaultButton(btn_confirm);
			}
			buttonPane.add(Box.createHorizontalStrut(10));
			{
				JButton btn_cancel = new JButton("取消");
				btn_cancel.setActionCommand("Cancel");
				btn_cancel.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						close();
					}
				});
				buttonPane.add(btn_cancel);
			}
			buttonPane.add(Box.createHorizontalStrut(5));
		}
		}
	}

}
