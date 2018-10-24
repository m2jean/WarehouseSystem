package gui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import VO.UserVO;
import businesslogic.userbl.UserBL;
import businesslogicservice.userblservice.UserBLService;
import enums.ResultMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class LoginDialog extends ResultDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1242537349907668771L;
	private JTextField txf_name;
	private JPasswordField pwf_psswd;
	private JButton btn_login;
	private JLabel lbl_msg;
	private JCheckBox chkbx_autolog;

	public LoginDialog(Frame parent, boolean startup) {
		super(parent,true);
		//this.setResizable(false);
		if(startup) this.setTitle(StringTable.LOGIN);
		else this.setTitle(StringTable.CHANGE_USER);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//int width = 334;
		//int height = 162;
		//setSize(width,height);
		getContentPane().setLayout(new BorderLayout(0,0));
		
		JPanel pnl_btn = new JPanel();
		//pnl_btn.setBorder(new LineBorder(Color.BLACK, 1));
		//pnl_btn.setLayout(new FlowLayout(FlowLayout.RIGHT,5,0));
		pnl_btn.setLayout(new BoxLayout(pnl_btn, BoxLayout.X_AXIS));
		pnl_btn.setBorder(BorderFactory.createEmptyBorder(0,5,2,2));
		getContentPane().add(pnl_btn, BorderLayout.SOUTH);

		pnl_btn.add(Box.createHorizontalGlue());
		
		lbl_msg = new JLabel(" ");
		pnl_btn.add(lbl_msg);
		lbl_msg.setForeground(Color.RED);
		
		pnl_btn.add(Box.createHorizontalStrut(10));
		
		btn_login = new JButton(StringTable.LOGIN);
		btn_login.setEnabled(false);
		pnl_btn.add(btn_login);
		
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				setVisible(false);
			}
		});
		JButton btn_exit = new JButton();
		if(startup) btn_exit.setText(StringTable.QUIT);
		else btn_exit.setText(StringTable.CANCEL);
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		pnl_btn.add(btn_exit);
		btn_login.addActionListener(new LoginListener());
		
		JPanel pnl_input = new JPanel();
		//pnl_input.setBorder(new EmptyBorder(0, 30, 0, 30));
		//pnl_input.setBorder(new LineBorder(Color.BLACK,1));
		getContentPane().add(pnl_input);
		GridBagLayout gbl_input = new GridBagLayout();
		pnl_input.setLayout(gbl_input);
		
		JLabel lb_name = new JLabel("用户名：");
		GridBagConstraints gbc_lb_name = new GridBagConstraints();
		gbc_lb_name.anchor = GridBagConstraints.EAST;
		//gbc_lb_name.insets = new Insets(0, 0, 5, 5);
		gbc_lb_name.gridx = 0;
		gbc_lb_name.gridy = 0;
		pnl_input.add(lb_name, gbc_lb_name);
		
		txf_name = new JTextField();
		GridBagConstraints gbc_txf_name = new GridBagConstraints();
		gbc_txf_name.fill = GridBagConstraints.HORIZONTAL;
		//gbc_txf_name.insets = new Insets(0, 0, 5, 0);
		gbc_txf_name.gridx = 1;
		gbc_txf_name.gridy = 0;
		pnl_input.add(txf_name, gbc_txf_name);
		txf_name.addActionListener(new LoginListener());
		txf_name.setColumns(15);
		txf_name.addCaretListener(new EmptyFieldListener());
		//txf_name.setBorder(new LineBorder(Color.BLACK, 1));
		
		JLabel lb_psswd = new JLabel("密码：");
		GridBagConstraints gbc_lb_psswd = new GridBagConstraints();
		gbc_lb_psswd.anchor = GridBagConstraints.EAST;
		//gbc_lb_password.insets = new Insets(0, 0, 0, 5);
		gbc_lb_psswd.gridx = 0;
		gbc_lb_psswd.gridy = 2;
		pnl_input.add(lb_psswd, gbc_lb_psswd);
		
		pwf_psswd = new JPasswordField();
		GridBagConstraints gbc_pwf_psswd = new GridBagConstraints();
		gbc_pwf_psswd.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwf_psswd.gridx = 1;
		gbc_pwf_psswd.gridy = 2;
		pnl_input.add(pwf_psswd, gbc_pwf_psswd);
		pwf_psswd.setColumns(15);
		pwf_psswd.addCaretListener(new EmptyFieldListener());
		//pwf_psswd.setBorder(new LineBorder(Color.BLACK, 1));
		pwf_psswd.addActionListener(new LoginListener());
		
		chkbx_autolog = new JCheckBox("自动登录");
		GridBagConstraints gbc_chkbx_autolog = new GridBagConstraints();
		gbc_chkbx_autolog.gridx = 1;
		gbc_chkbx_autolog.gridy = 3;
		gbc_chkbx_autolog.anchor = GridBagConstraints.LINE_START;
		pnl_input.add(chkbx_autolog, gbc_chkbx_autolog);
		
		if(startup)
			this.addWindowListener(new WindowAdapter(){
				@Override
				public void windowOpened(WindowEvent arg0) {
					UserVO userinfo;
					try {
						userinfo = new UserBL().getAutoLoginInfo();
					} catch (ClassNotFoundException | IOException e1) {
						userinfo = null;
						e1.printStackTrace();
					}
					if(userinfo != null){
						txf_name.setText(userinfo.getUsername());
						pwf_psswd.setText(userinfo.getPassword());
						chkbx_autolog.setSelected(true);
						btn_login.setEnabled(false);
						Timer autologt = new Timer(1000,new LoginListener());
						autologt.setRepeats(false);
						showMessage("自动登录中……");
						autologt.start();
					}
				}
	
				private void showMessage(String msg){
					lbl_msg.setForeground(Color.BLACK);
					lbl_msg.setText(msg);
				}
			});		
	}
	
	private class EmptyFieldListener implements CaretListener{
		@Override
		public void caretUpdate(CaretEvent e) {
			if(txf_name.getText().isEmpty() || pwf_psswd.getPassword().length == 0)
				btn_login.setEnabled(false);
			else
				btn_login.setEnabled(true);
		}		
	}
	
	class LoginListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if(txf_name.getText().isEmpty() || pwf_psswd.getPassword().length == 0)
				return;
			
			UserBLService userbl = new UserBL();

			ResultMessage result = userbl.login(new UserVO(txf_name.getText(),String.valueOf(pwf_psswd.getPassword()),
						null, null, null, null ), chkbx_autolog.isSelected());
			setResult(result);
			switch(result){
			case SUCCESS:
				setVisible(false);
				break;
			case LOGIN_FAIL:
				showError("密码错误！");
				break;
			case USER_NOT_EXIST:
				showError("不存在的用户名！");
				break;
			case REMOTE_FAIL:
				showError("通信失败！");
				break;
			default:
				showError("未知错误！");
				break;
			}
		}
		
		private void showError(String msg){
			lbl_msg.setForeground(Color.RED);
			lbl_msg.setText(msg);
		}
	}
}
