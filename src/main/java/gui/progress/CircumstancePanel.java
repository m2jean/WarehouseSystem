package gui.progress;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;

import VO.CircumstanceVO;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import gui.datecombobox.ChooseDatePanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.JTable;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;

import javax.swing.JScrollPane;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CircumstancePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4347760615495586920L;
	private JLabel title;
	ChooseDatePanel chooseDatePanel;
	private CircumstanceVO circumstance;
	private IncomeTableModel incomeModel;
	private DisbursementTableModel disbursementModel;
	private String[] incomeData;
	private String[] disbursementData;
	private JTable table_income;
	private JTable table_disbursement;
	private JLabel label_profit;
	
	public CircumstancePanel() {
		setSize(800, 640);
		setLayout(new BorderLayout(0, 0));
		
		title = new JLabel("经营情况", SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.BOTTOM);
		title.setFont(new Font("宋体", Font.PLAIN, 40));
		add(title, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{178, 550, 0};
		gbl_panel_1.rowHeights = new int[]{17, 18, -1, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		chooseDatePanel = new ChooseDatePanel();
		GridBagConstraints gbc_chooseDatePanel = new GridBagConstraints();
		gbc_chooseDatePanel.insets = new Insets(0, 0, 5, 0);
		gbc_chooseDatePanel.fill = GridBagConstraints.BOTH;
		gbc_chooseDatePanel.gridx = 1;
		gbc_chooseDatePanel.gridy = 1;
		panel_1.add(chooseDatePanel, gbc_chooseDatePanel);
		
		JButton button = new JButton("查看");
		button.addActionListener(new GetListener());
		chooseDatePanel.add(button);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{20, 0, 47, 0, 0, 49, 40, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 1;
		panel_2.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new CardLayout(0, 0));
		
		JLabel label = new JLabel("收入类");
		label.setFont(new Font("宋体", Font.PLAIN, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(label, "name_9499285806565");
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		panel_2.add(scrollPane, gbc_scrollPane);
		
		table_income = new JTable();
		scrollPane.setViewportView(table_income);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 4;
		panel_2.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new CardLayout(0, 0));
		
		JLabel label_1 = new JLabel("支出类");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("宋体", Font.PLAIN, 30));
		panel_4.add(label_1, "name_9507934653687");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 5;
		panel_2.add(scrollPane_1, gbc_scrollPane_1);
		
		table_disbursement = new JTable();
		scrollPane_1.setViewportView(table_disbursement);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 6;
		panel_2.add(panel_5, gbc_panel_5);
		panel_5.setLayout(new CardLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 0);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 7;
		panel_2.add(panel_6, gbc_panel_6);
		panel_6.setLayout(new CardLayout(0, 0));
		
		label_profit = new JLabel("");
		label_profit.setHorizontalAlignment(SwingConstants.CENTER);
		label_profit.setFont(new Font("宋体", Font.PLAIN, 40));
		panel_6.add(label_profit, "name_9960274931763");
		
		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(0, 0, 5, 0);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 8;
		panel_2.add(panel_7, gbc_panel_7);
		
		JPanel panel_8 = new JPanel();
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.insets = new Insets(0, 0, 5, 0);
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 9;
		panel_2.add(panel_8, gbc_panel_8);
		
		JButton button_export = new JButton("导出");
		button_export.setFont(new Font("宋体", Font.PLAIN, 20));
		panel_8.add(button_export);
		
		getData("0000-00-00", "9999-99-99");
	}
	
	public void getData(String startTime, String endTime){
		ProgressBLService service = new ProgressBL();
		circumstance = service.getCircumstance(startTime, endTime);
		incomeData = new String[]{String.valueOf(circumstance.getSalein()),
				String.valueOf(circumstance.getChange()),
				String.valueOf(circumstance.getChajia()), 
				String.valueOf(circumstance.getCredit()),
				String.valueOf(circumstance.getOverin()),
				String.valueOf(circumstance.getIncome()), 
				String.valueOf(circumstance.getIncome()-circumstance.getDiscount())};
		
		disbursementData = new String[]{String.valueOf(circumstance.getSaleout()), 
				String.valueOf(circumstance.getPresent()),
				String.valueOf(circumstance.getOverout()), 
				String.valueOf(circumstance.getExpense()), 
				String.valueOf(circumstance.getDiscount())};
		
		showTable();
		label_profit.setText("总利润： "+String.valueOf(circumstance.getProfit()));
		
	}
	
	public void showTable(){
		incomeModel = new IncomeTableModel(incomeData);
		disbursementModel = new DisbursementTableModel(disbursementData);
		table_income.setModel(incomeModel);
		table_disbursement.setModel(disbursementModel);
	}
	
	class GetListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String startTime = chooseDatePanel.getStartDate();
			String endTime = chooseDatePanel.getEndDate();
			
			getData(startTime, endTime);
			
		}
	}

	public static void main(String[] args){
		JFrame frame = new JFrame();
		CircumstancePanel panel = new CircumstancePanel();
		frame.setSize(800, 640);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
}
