package gui.user;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import VO.UserVO;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import enums.DataMessage;
import enums.ResultMessage;
import gui.GUIUtility;
import gui.LeftAlignTableRenderer;
import gui.MainFrame;
import gui.ResultDialog;
import businesslogic.userbl.UserBL;
import businesslogicservice.userblservice.UserBLService;

public class UserPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7286749578439976602L;
	private static final String NO_USER = "NO_USER";
	private static final String USER_LIST = "USER_LIST";
	
	private JTable tbl_user;
	private JButton btn_addUser,btn_delUser,btn_updUser;
	private CardLayout cardlayout;
	private JPanel pnl_user;
	
	private UserBLService userbl = new UserBL();

	/**
	 * Create the panel.
	 */
	public UserPanel() {
		setPreferredSize(new Dimension(800, 640));
		setLayout(new BorderLayout(0, 0));
		
		pnl_user = new JPanel();
		add(pnl_user, BorderLayout.CENTER);
		cardlayout = new CardLayout();
		pnl_user.setLayout(cardlayout);
		
		JLabel lbl_NoUser = new JLabel("没有用户");
		pnl_user.add(lbl_NoUser, NO_USER);
		lbl_NoUser.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane scrp_product = new JScrollPane();
		pnl_user.add(scrp_product, USER_LIST);
		
		tbl_user = new JTable();		
		tbl_user.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbl_user.setDefaultRenderer(String.class, new LeftAlignTableRenderer());
		tbl_user.setRowHeight(30);
		tbl_user.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent me){
				if(me.getButton() == MouseEvent.BUTTON1 && me.getClickCount() == 2)
					btn_updUser.getActionListeners()[0].actionPerformed(null);
			}
		});
		scrp_product.setViewportView(tbl_user);
		
		JPanel pnl_userFunc = new JPanel();
		add(pnl_userFunc, BorderLayout.SOUTH);
		FlowLayout fl_pnl_userFunc = (FlowLayout) pnl_userFunc.getLayout();
		fl_pnl_userFunc.setAlignment(FlowLayout.LEFT);
		
		btn_addUser = new JButton("添加用户");
		btn_addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDialog ud = new UserDialog(MainFrame.mf,(UserTableModel)tbl_user.getModel());
				ud.pack();
				ud.setResizable(false);
				GUIUtility.setCenter(ud);
				ud.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(ud);
				if(result == ResultMessage.SUCCESS){
					MainFrame.mf.setMessage("添加用户成功！");
					buildTable();
				}
			}
		});
		pnl_userFunc.add(btn_addUser);
		
		btn_delUser = new JButton("删除用户");
		btn_delUser.setEnabled(false);
		btn_delUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserTableModel ptmodel = (UserTableModel) tbl_user.getModel();
				int row = tbl_user.getSelectedRow();
				UserVO user = ptmodel.getUserVOAt(row);
				int choice = JOptionPane.showConfirmDialog(MainFrame.mf, "确认删除用户"+user.getName()+"？", "确认删除", JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				if(choice != JOptionPane.OK_OPTION)
					return;
				ResultMessage result = userbl.delete(user);
				switch(result){
				case SUCCESS:
					MainFrame.mf.setMessage("用户删除成功！");
					buildTable();
					break;
				default:
					MainFrame.mf.setError("用户删除失败");
					break;
				}
			}
		});
		pnl_userFunc.add(btn_delUser);
		
		btn_updUser = new JButton("修改用户信息");
		btn_updUser.setEnabled(false);
		btn_updUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserTableModel model = (UserTableModel) tbl_user.getModel();
				UserVO user = model.getUserVOAt(tbl_user.getSelectedRow());
				UserDialog ud = new UserDialog(MainFrame.mf,user,(UserTableModel)tbl_user.getModel());
				ud.pack();
				ud.setResizable(false);
				GUIUtility.setCenter(ud);
				ud.setVisible(true);
				ResultMessage result = ResultDialog.getResultAndDispose(ud);
				if(result == ResultMessage.SUCCESS){
					MainFrame.mf.setMessage("修改用户信息成功！");
					buildTable();
				}
			}
		});
		pnl_userFunc.add(btn_updUser);

		tbl_user.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tbl_user.getSelectedRow() == -1){
					btn_delUser.setEnabled(false);
					btn_updUser.setEnabled(false);
				}
				else{
					UserTableModel model = (UserTableModel) tbl_user.getModel();
					UserVO user = model.getUserVOAt(tbl_user.getSelectedRow());
					if(userbl.getCurrent().getUsername().equals(user.getUsername())){
						btn_delUser.setEnabled(false);
						btn_updUser.setEnabled(false);
					}
					else{
						btn_delUser.setEnabled(true);
						btn_updUser.setEnabled(true);
					}
				}
			}		
		});
		
		buildTable();
	}
	
	protected void buildTable() {
		DataMessage<ArrayList<UserVO>> result = userbl.getAllUser();
		switch(result.resultMessage){
		case REMOTE_FAIL:
			JOptionPane.showMessageDialog(MainFrame.mf, "通信失败！");
			break;
		case IS_EMPTY:
			cardlayout.show(pnl_user,NO_USER);
			break;
		default:
			if(result.resultMessage == ResultMessage.SUCCESS){
				if(result.data == null || result.data.isEmpty())
					cardlayout.show(pnl_user,NO_USER);
				else{
					buildTable(result.data);
				}
			}
			else
				JOptionPane.showMessageDialog(MainFrame.mf, "未知错误！");
			break;			
		}
	}

	private void buildTable(final ArrayList<UserVO> users) {
		tbl_user.setModel(new UserTableModel(users));
		cardlayout.show(pnl_user,USER_LIST);
	}


}
