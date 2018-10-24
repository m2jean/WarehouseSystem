package gui.message;

import gui.GUIUtility;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;

import businesslogic.messagebl.MessageBL;
import businesslogic.userbl.UserBL;
import VO.MessageVO;

public class MessageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4697259205833872970L;
	private JPanel contentPane;
	private ArrayList<MessageVO> list;

	/**
	 * Create the frame.
	 */
	public MessageFrame(ArrayList<MessageVO> list) {
		this.list = list;
		setTitle("查看消息");
		
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		int width = 300;
		int height = 360;
		this.setSize(width, height);
		GUIUtility.setCenter(this);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		
		buildMessages(panel);
	}
	

	public void buildMessages(JPanel panel){
		Collections.sort(list);
		for(MessageVO vo : list){
			panel.add(new MessageItemPanel(vo.getDate(),vo.getContent(),!vo.isRead()));
			vo.setRead(true);
		}
		panel.add(Box.createVerticalStrut(360-120));
		new MessageBL().update(new UserBL().getCurrent(),list);
	}

}
