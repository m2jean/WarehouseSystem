package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import VO.UserVO;
import businesslogic.userbl.UserBL;
import enums.ResultMessage;
import enums.Subsystem;
import enums.UserPermission;

public class MainFrame extends JFrame {

	public static MainFrame mf;
	private static final long serialVersionUID = -4960106057751440960L;
	
	private JPanel contentPane;
	private JPanel pnl_mainView;
	private CardLayout cardlayout;
	//private JLabel lbl_userinfo;
	private JLabel lbl_date;
	//private Timer msgTimer;
	//private ArrayList<MessageVO> messagelist;
	//private FixedSizeButton btn_msg;
	private JLabel lbl_status;
	private JPanel pnl_menu;
	private ButtonFactory btnfac_mn;
	private ButtonGroup btngrp_mn;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		for(LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()){
			if(laf.getName().equals("Nimbus")){
				try {
					UIManager.setLookAndFeel(laf.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);					
					ResultDialog logdia = new LoginDialog(frame, true);
					logdia.pack();
					logdia.setResizable(false);
					GUIUtility.setCenter(logdia);
					logdia.setVisible(true);
					ResultMessage result = ResultDialog.getResultAndDispose(logdia);					
					if(result == ResultMessage.SUCCESS || result == ResultMessage.SAVE_FAIL){
						UserVO user = new UserBL().getCurrent();
						frame.login(user);
						if(result == ResultMessage.SAVE_FAIL)
							frame.setMessage("登陆成功！保存自动登录信息失败");
					}else{
						frame.dispose();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		mf = this;
		setTitle("进销存系统");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int width = 800;
		int height = 640;
		setSize(width,height);
		GUIUtility.setCenter(this);	
						
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		pnl_menu = new JPanel();
		contentPane.add(pnl_menu, BorderLayout.NORTH);
		pnl_menu.setLayout(new BoxLayout(pnl_menu, BoxLayout.X_AXIS));	
		
		pnl_mainView = new JPanel();
		contentPane.add(pnl_mainView, BorderLayout.CENTER);
		cardlayout = new CardLayout();
		pnl_mainView.setLayout(cardlayout);

		JPanel pnl_status = new JPanel();
		pnl_status.setLayout(new BoxLayout(pnl_status, BoxLayout.X_AXIS));
		contentPane.add(pnl_status, BorderLayout.SOUTH);
		
		Box box_msg = new Box(BoxLayout.X_AXIS);
		pnl_status.add(box_msg);
		box_msg.setBorder(BorderFactory.createLoweredBevelBorder());
		box_msg.add(Box.createHorizontalGlue());
		lbl_status = new JLabel("欢迎使用进销存系统");
		box_msg.add(lbl_status);
		box_msg.add(Box.createHorizontalGlue());

		Box box_date = new Box(BoxLayout.X_AXIS);
		pnl_status.add(box_date);
		box_date.setBorder(BorderFactory.createLoweredBevelBorder());
		//box_date.add(Box.createHorizontalGlue());
		lbl_date = new JLabel();
		box_date.add(lbl_date);
		lbl_date.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		//box_date.add(Box.createHorizontalGlue());
		
		startClock();		
		
	}
	
	private void startClock() {
		Timer timer = new Timer(1000,null);
		timer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				lbl_date.setText(DateFormat.getDateTimeInstance()
						.format(Calendar.getInstance().getTime()));
			}					
		});
		timer.setRepeats(true);
		timer.start();		
	}

	/*private void startMessageTimer(){
		updateMessage();
		msgTimer = new Timer(60000,new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateMessage();
				//System.out.println("update message");
			}
		});
		msgTimer.setRepeats(true);
		msgTimer.start();
	}
	
	private void updateMessage() {
		MessageBLService bl = new MessageBL();
		
		int i = 0;
		try {
			ArrayList<MessageVO> messagelist = bl.get();
			for(MessageVO vo:messagelist)
				if(!vo.isRead()) ++i;					
		} catch (RemoteFailException e) {
			setError("消息获取失败");
			e.printStackTrace();
		} catch (EmptyException e) {
			btn_msg.setText("消息");
			btn_msg.setForeground(Color.BLACK);
			setTitle("进销存系统");
			messagelist = new ArrayList<MessageVO>();
			return;
		}						
		btn_msg.setText("消息（"+i+"）");
		if(i!=0){
			btn_msg.setForeground(Color.RED);
			setTitle("（新消息）进销存系统");
		}
		else
			try {
				throw new EmptyException("Message at MainFrame");
			} catch (EmptyException e) {
				e.printStackTrace();
			}
		
	}*/

	private void login(UserVO user) {
		//lbl_userinfo.setText(user.getPermission().toString()+" "+user.getName());
		buildMenu(user.getPermission());
		//startMessageTimer();
		setMessage("登陆成功");
	}
	
	private void changeUser(){
		/*try {
			new UserBL().clearAutoLoginInfo();
		} catch (IOException e) {
			setMessage("更新自动登录数据失败");
			e.printStackTrace();
		}*/
		ResultDialog logdia = new LoginDialog(MainFrame.this, false);
		logdia.pack();
		logdia.setResizable(false);
		GUIUtility.setCenter(logdia);
		logdia.setVisible(true);
		ResultMessage result = ResultDialog.getResultAndDispose(logdia);					
		if(result == ResultMessage.SUCCESS){
			//msgTimer.stop();			
			UserVO user = new UserBL().getCurrent();
			login(user);
		}
	}
	
	public void setMessage(String status){
		lbl_status.setForeground(Color.BLACK);
		lbl_status.setText(status);
	}
	public void setError(String err){
		lbl_status.setForeground(Color.RED);
		lbl_status.setText(err);
	}

	private void buildMenu(UserPermission permission) {
		pnl_menu.removeAll();
		pnl_mainView.removeAll();

		if(permission == null)
			return;	
		
		btnfac_mn = new ButtonFactory(new Font("黑体",Font.PLAIN,19));
		
		btngrp_mn = new ButtonGroup();
		Subsystem[] systems = Subsystem.getMenu(permission);
		{
			JToggleButton btn = btnfac_mn.getToggleButton(systems[0].getDisplayName());			
			pnl_menu.add(btn);
			btngrp_mn.add(btn);
			btn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel pnl = systems[0].getPanel();
					pnl_mainView.add(pnl,systems[0].toString());
					cardlayout.show(pnl_mainView,systems[0].toString());	
				}
			});
			btn.setSelected(true);
			btn.getActionListeners()[0].actionPerformed(null);
		}
		for(int i = 1;i < systems.length;++i){
			Subsystem sbsys = systems[i];
			JToggleButton btn = btnfac_mn.getToggleButton(sbsys.getDisplayName());			
			pnl_menu.add(btn);
			btngrp_mn.add(btn);
			btn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel pnl = sbsys.getPanel();
					pnl_mainView.add(pnl,sbsys.toString());
					cardlayout.show(pnl_mainView,sbsys.toString());	
				}
			});
		}
		
		pnl_menu.add(Box.createHorizontalGlue());
		
		/*btn_msg = btnfac_mn.getFixedSizeButton(new String[]{"消息","消息（00）"});		
		btn_msg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_msg.setText("消息");
				btn_msg.setForeground(Color.BLACK);
				new MessageFrame(messagelist).setVisible(true);			
			}
		});	
		pnl_menu.add(btn_msg);*/
		
		JButton btn_chgusr = btnfac_mn.getButton("切换用户");
		btn_chgusr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeUser();
				//buildMenu(null);				
				//btn_msg.setEnabled(false);
				//btn_logout.setEnabled(false);			
			}
		});
		pnl_menu.add(btn_chgusr);
		
		/*
		MenuButton btn_password = new MenuButton("修改密码");
		menu_user.add(btn_password);
		btn_password.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new SetPasswordDialog(MainFrame.mf);
			}
		});
			
		JButton btn_exit = btnfac_mn.getButton("退出");
		btn_exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(NORMAL);
			}
		});
		pnl_state.add(btn_exit);
		*/
		pnl_menu.repaint();
	}
	
}
