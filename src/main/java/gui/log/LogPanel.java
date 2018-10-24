package gui.log;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import VO.LogVO;
import businesslogicservice.logblservice.LogBLService;
import enums.DataMessage;
import enums.ResultMessage;
import gui.datecombobox.ChooseDatePanel;
import logBL.LogBL;

public class LogPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2376427522194732471L;
	private LogBLService logService;
	private JTable table;
	private ChooseDatePanel datePanel;
	
	public LogPanel() {
		logService = new LogBL();
		setSize(800, 640);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{155, 372, 91, 112, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		datePanel = new ChooseDatePanel();
		GridBagConstraints gbc_chooseDatePanel = new GridBagConstraints();
		gbc_chooseDatePanel.insets = new Insets(0, 0, 0, 5);
		gbc_chooseDatePanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_chooseDatePanel.gridx = 1;
		gbc_chooseDatePanel.gridy = 0;
		panel_1.add(datePanel, gbc_chooseDatePanel);
		
		JButton button = new JButton("查看所有日志");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataMessage<ArrayList<LogVO>> message=logService.getAll();
				if(message.resultMessage==ResultMessage.SUCCESS){
					ArrayList<LogVO> list=message.data;
					showTable(list);
				}else if(message.resultMessage==ResultMessage.IS_EMPTY){
					JOptionPane.showMessageDialog(null, "没有日志记录", "提示", JOptionPane.INFORMATION_MESSAGE);
				}else if(message.resultMessage==ResultMessage.REMOTE_FAIL){
					JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton button_1 = new JButton("查看");
		button_1.addActionListener(new getLogListener());
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.anchor = GridBagConstraints.WEST;
		gbc_button_1.insets = new Insets(0, 0, 0, 5);
		gbc_button_1.gridx = 2;
		gbc_button_1.gridy = 0;
		panel_1.add(button_1, gbc_button_1);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.gridx = 3;
		gbc_button.gridy = 0;
		panel_1.add(button, gbc_button);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setRowHeight(30);
		showTable(null);
		scrollPane.setViewportView(table);
		
	}
	
	public void showTable(ArrayList<LogVO> list){
		LogTableModel model=new LogTableModel(list);
		table.setModel(model);
	}
	
	class getLogListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String startDate = datePanel.getStartDate();
			String endDate = datePanel.getEndDate();
			DataMessage<ArrayList<LogVO>> message=logService.find(startDate+" 00:00:00", endDate+" 23:59:59");
			
			if(message.resultMessage==ResultMessage.SUCCESS){
				ArrayList<LogVO> list=message.data;
				Collections.sort(list);
				showTable(list);
			}else if(message.resultMessage==ResultMessage.IS_EMPTY){
				JOptionPane.showMessageDialog(null, "没有日志记录", "提示", JOptionPane.INFORMATION_MESSAGE);
			}else if(message.resultMessage==ResultMessage.REMOTE_FAIL){
				JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		LogPanel panel = new LogPanel();
		frame.setSize(800,  640);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

}
