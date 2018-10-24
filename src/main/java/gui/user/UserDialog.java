package gui.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import businesslogic.userbl.UserBL;
import businesslogicservice.userblservice.UserBLService;
import VO.UserVO;
import enums.Operation;
import enums.ResultMessage;
import enums.UserPermission;
import gui.GUIUtility;
import gui.ResultDialog;
import gui.SelectHintTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDialog extends ResultDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7961568199852374852L;
	private final JPanel pnl_content = new JPanel();
	private JTextField txf_username;
	private JTextField txf_name;
	private JTextField txf_phone;
	private JComboBox<UserPermission> cbx_perm;
	private Operation opt;
	private UserBLService userbl = new UserBL();
	UserTableModel tblmdl;
	private JPasswordField pwf_repeat;
	private JPasswordField pwf_psswd;
	private JLabel lbl_msg;
	private JButton btn_confirm;

	/**
	 * Create the dialog.
	 */
	public UserDialog(Frame f,UserTableModel usermdl) {
		super(f,true);
		opt = Operation.ADD_USER;
		tblmdl = usermdl;
		setTitle("添加用户");
		construct();
	}
	
	public UserDialog(Frame f,UserVO user,UserTableModel usermdl){
		super(f,true);
		opt = Operation.UPD_USER;
		tblmdl = usermdl;
		setTitle("修改用户信息");
		construct();
		txf_username.setText(user.getUsername());
		txf_username.setEditable(false);
		txf_username.setBorder(null);
		txf_username.setFocusable(false);
		txf_username.setBackground(new Color(this.getBackground().getRGB()));
		cbx_perm.setSelectedItem(user.getPermission());
		txf_name.setText(user.getName());
		txf_phone.setText(user.getTele());
	}
	
	private void construct(){
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		pnl_content.setBorder(new EmptyBorder(10,0,0,0));
		getContentPane().add(pnl_content, BorderLayout.CENTER);
		pnl_content.setLayout(new GridBagLayout());
		GridBagConstraints gbc;
		int x = 0, y = 0; int cols = 16;
		{
			JLabel lbl_username = new JLabel("用户名：");
			lbl_username.setHorizontalAlignment(SwingConstants.TRAILING);
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.insets = new Insets(0,5,0,0);
			gbc.anchor = GridBagConstraints.LINE_END;
			pnl_content.add(lbl_username, gbc);
		}
		{
			txf_username = new JTextField();
			txf_username.addFocusListener(GUIUtility.getSelectFocusListener());
			txf_username.addActionListener(new ConfirmListener());
			txf_username.addCaretListener(new EmptyFieldListener());
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.anchor = GridBagConstraints.LINE_START;
			pnl_content.add(txf_username,gbc);
			txf_username.setColumns(cols);
		}
		{
			JLabel lbl_perm = new JLabel("权限：");
			lbl_perm.setHorizontalAlignment(SwingConstants.TRAILING);
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.insets = new Insets(0,10,0,0);
			gbc.anchor = GridBagConstraints.LINE_END;
			pnl_content.add(lbl_perm,gbc);
		}
		{
			cbx_perm = new JComboBox<UserPermission>(UserPermission.values());
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			//gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.LINE_START;
			pnl_content.add(cbx_perm, gbc);
		}
		x = 0; ++y;
		if(opt == Operation.ADD_USER){
			JLabel lbl_psswd = new JLabel("密码：");
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.anchor = GridBagConstraints.LINE_END;
			pnl_content.add(lbl_psswd, gbc);
			
			pwf_psswd = new JPasswordField();
			pwf_psswd.addFocusListener(GUIUtility.getSelectFocusListener());
			pwf_psswd.addCaretListener(new EmptyFieldListener());
			pwf_psswd.setColumns(cols);
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.anchor = GridBagConstraints.LINE_START;
			pnl_content.add(pwf_psswd, gbc);
			
			JLabel lbl_repeat = new JLabel("密码确认：");
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.anchor = GridBagConstraints.LINE_END;
			gbc.insets = new Insets(0,10,0,0);
			pnl_content.add(lbl_repeat, gbc);
			
			pwf_repeat = new JPasswordField();
			pwf_repeat.addFocusListener(GUIUtility.getSelectFocusListener());
			pwf_repeat.addCaretListener(new EmptyFieldListener());
			pwf_repeat.setColumns(cols);
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.anchor = GridBagConstraints.LINE_START;
			pnl_content.add(pwf_repeat, gbc);
		}
		x = 0;++y;
		{
			JLabel lbl_name = new JLabel("姓名：");
			lbl_name.setHorizontalAlignment(SwingConstants.TRAILING);
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.anchor = GridBagConstraints.LINE_END;
			pnl_content.add(lbl_name,gbc);
		}
		{
			txf_name = new JTextField();
			txf_name.addFocusListener(GUIUtility.getSelectFocusListener());
			txf_name.addActionListener(new ConfirmListener());
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.anchor = GridBagConstraints.LINE_START;
			pnl_content.add(txf_name, gbc);
			txf_name.setColumns(10);
		}
		{
			JLabel lbl_phone = new JLabel("电话：");
			lbl_phone.setHorizontalAlignment(SwingConstants.TRAILING);
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.anchor = GridBagConstraints.LINE_END;
			pnl_content.add(lbl_phone,gbc);
		}
		{
			txf_phone = new SelectHintTextField("可不填");
			txf_phone.addActionListener(new ConfirmListener());
			gbc = new GridBagConstraints();
			gbc.gridx = x++;
			gbc.gridy = y;
			gbc.anchor = GridBagConstraints.LINE_START;
			pnl_content.add(txf_phone, gbc);
			txf_phone.setColumns(cols);
		}
		
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
				btn_confirm = new JButton("确定(Enter)");
				btn_confirm.setActionCommand("OK");
				btn_confirm.addActionListener(new ConfirmListener());
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
						setVisible(false);
					}
				});
				buttonPane.add(btn_cancel);
			}
			buttonPane.add(Box.createHorizontalStrut(5));
		}
	}
	
	private void showMessage(String msg){
		lbl_msg.setText(msg);
	}
	
	private class EmptyFieldListener implements CaretListener{
		@Override
		public void caretUpdate(CaretEvent e) {
			if(opt == Operation.ADD_USER){
				if(txf_username.getText().trim().isEmpty() ||
						pwf_psswd.getPassword().length == 0 ||
						pwf_repeat.getPassword().length == 0)
					btn_confirm.setEnabled(false);
				else
					btn_confirm.setEnabled(true);
			}
		}		
	}
	
	private class ConfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = txf_username.getText().trim();
			int perm = cbx_perm.getSelectedIndex();
			String name = txf_name.getText().trim();
			String phone = txf_phone.getText().trim();
			String pass1 = "";
			String pass2;
			if(username.equals("")){
				showMessage("请输入用户名！");
				txf_username.requestFocus();
				return;
			}
			if(perm == -1){
				showMessage("请选择权限！");
				cbx_perm.requestFocus();
				return;
			}
			if(opt == Operation.ADD_USER){
				pass1 = String.valueOf(pwf_psswd.getPassword());
				pass2 = String.valueOf(pwf_repeat.getPassword());
				if(pass1.equals("")){
					showMessage("密码不能为空！");
					pwf_psswd.requestFocus();
					return;
				}
				if(pass1.length() < 5){
					showMessage("密码长度至少为5位！");
					pwf_psswd.requestFocus();
					return;
				}
				if(!pass1.equals(pass2)){
					showMessage("密码确认不匹配！");
					pwf_repeat.requestFocus();
					return;
				}
			}
			if(name.equals("")){
				showMessage("请输入姓名！");
				txf_name.requestFocus();
				return;
			}
			if(opt == Operation.ADD_USER){
				boolean exist = (tblmdl.findUser(username) != null);
				if(!exist){
					UserVO user = new UserVO(username, pass1, cbx_perm.getItemAt(perm), name, phone, null);
					ResultMessage result = userbl.add(user);
					setResult(result);
					switch(result){
					case ITEM_EXIST:
						showMessage("用户名已存在！");
						txf_username.requestFocus();
						break;
					case REMOTE_FAIL:
						showMessage("通信错误！");
						break;
					case SUCCESS:
						setVisible(false);				
						break;
					default:
						showMessage("未知错误！");
						break;
					}
				}
				else
					showMessage("用户名已存在！");
			}		
			else if(opt == Operation.UPD_USER){
				UserVO user = new UserVO(username, "",cbx_perm.getItemAt(perm), name, phone, null);
				ResultMessage result = userbl.update(user);
				setResult(result);
				switch(result){
				case ITEM_NOT_EXIST:
					showMessage("不存在的用户名！");
					break;
				case REMOTE_FAIL:
					showMessage("通信错误！");
					break;
				case SUCCESS:
					setVisible(false);					
					break;
				default:
					showMessage("未知错误！");
					break;
				}
			}
		}
		
	}

}
