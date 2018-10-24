package gui.user;

import enums.ResultMessage;
import gui.GUIUtility;
import gui.ResultDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

import businesslogic.userbl.UserBL;
import businesslogicservice.userblservice.UserBLService;
import VO.UserVO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetPasswordDialog extends ResultDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6496158081208106097L;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField pwf_password;
	private JPasswordField pwf_confirm;
	private UserVO user;
	private UserDialog ud;
	private UserBLService userbl = new UserBL();

	/**
	 * Create the dialog.
	 */
	public SetPasswordDialog(Frame ud,UserVO user) {
		super(ud,true);
		//this.ud = ud;
		this.user = user;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(308, 174);
		GUIUtility.setCenter(this);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 0, 20, 60));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 20));
		{
			JLabel lbl_password = new JLabel("密码：");
			lbl_password.setHorizontalAlignment(SwingConstants.TRAILING);
			contentPanel.add(lbl_password);
		}
		{
			pwf_password = new JPasswordField();
			contentPanel.add(pwf_password);
			pwf_password.addActionListener(new ConfirmListener());
			pwf_password.setColumns(10);
		}
		{
			JLabel lbl_confirm = new JLabel("确认密码：");
			lbl_confirm.setHorizontalAlignment(SwingConstants.TRAILING);
			contentPanel.add(lbl_confirm);
		}
		{
			pwf_confirm = new JPasswordField();
			contentPanel.add(pwf_confirm);
			pwf_confirm.addActionListener(new ConfirmListener());
			pwf_confirm.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btn_confirm = new JButton("OK");
				btn_confirm.setActionCommand("OK");
				btn_confirm.addActionListener(new ConfirmListener());
				buttonPane.add(btn_confirm);
				getRootPane().setDefaultButton(btn_confirm);
			}
			{
				JButton btn_cancel = new JButton("Cancel");
				btn_cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btn_cancel.setActionCommand("Cancel");
				buttonPane.add(btn_cancel);
			}
		}
	}

	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String pass1 = String.valueOf(pwf_password.getPassword()).trim();
			String pass2 = String.valueOf(pwf_confirm.getPassword()).trim();
			if(pass1.equals("") || pass2.equals(""))
				JOptionPane.showMessageDialog(SetPasswordDialog.this, "密码不能为空！");
			else if(pass1.length() < 5)
				JOptionPane.showMessageDialog(SetPasswordDialog.this, "密码长度至少为5位！");
			else if(!pass1.equals(pass2))
				JOptionPane.showMessageDialog(SetPasswordDialog.this, "密码不匹配！");
			else{
				user.setPassword(pass1);
				ResultMessage result = userbl.add(user);
				switch(result){
				case ITEM_EXIST:
					JOptionPane.showMessageDialog(SetPasswordDialog.this, "用户名已存在！");
					break;
				case REMOTE_FAIL:
					JOptionPane.showMessageDialog(SetPasswordDialog.this, "通信错误！");
					break;
				case SUCCESS:
					JOptionPane.showMessageDialog(SetPasswordDialog.this, "操作成功！");
					//TODO MainFrame.mf.pnl_userAdmin.buildTable();
					ud.dispose();
					dispose();
					break;
				default:
					JOptionPane.showMessageDialog(SetPasswordDialog.this, "未知错误！");
					break;
				}
			}
		}		
	}
}
