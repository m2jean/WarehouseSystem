package gui.progress;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;

import factory.ExportToExcel;
import gui.datecombobox.ChooseDatePanel;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.SwingConstants;
import progressBL.ProgressBL;
import progressBLService.ProgressBLService;
import VO.CircumstanceVO;


public class CircumstancePanelNew extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5583904664771926930L;
	private JLabel label_income;
	private JLabel label_salein;
	private JLabel label_chajia;
	private JLabel label_credit;
	private JLabel label_discount;
	private JLabel label_change;
	private JLabel label_overin;
	private JLabel label_incomeAfterDiscount;
	private JLabel label_saleout;
	private JLabel label_overout;
	private JLabel label_present;
	private JLabel label_expense;
	private JLabel label_profit;
	
	private CircumstanceVO circumstance;
	private ChooseDatePanel chooseDatePanel;
	
	public CircumstancePanelNew() {
		setSize(800, 640);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{150, 375, 57, 47, 57, 0};
		gbl_panel_2.rowHeights = new int[]{23, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		chooseDatePanel = new ChooseDatePanel();
		GridBagConstraints gbc_chooseDatePanel = new GridBagConstraints();
		gbc_chooseDatePanel.anchor = GridBagConstraints.WEST;
		gbc_chooseDatePanel.insets = new Insets(0, 0, 0, 5);
		gbc_chooseDatePanel.gridx = 1;
		gbc_chooseDatePanel.gridy = 0;
		panel_2.add(chooseDatePanel, gbc_chooseDatePanel);
		
		JButton button = new JButton("查看");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.NORTHWEST;
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 2;
		gbc_button.gridy = 0;
		panel_2.add(button, gbc_button);
		
		JButton button_1 = new JButton("导出");
		button_1.addActionListener(new ExportListener());
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_button_1.gridx = 4;
		gbc_button_1.gridy = 0;
		panel_2.add(button_1, gbc_button_1);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(null);
		
		JLabel label_1 = new JLabel("收入类");
		label_1.setFont(new Font("宋体", Font.PLAIN, 30));
		label_1.setBounds(348, 10, 100, 42);
		panel_3.add(label_1);
		
		JLabel label_2 = new JLabel("销售收入：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 15));
		label_2.setBounds(217, 59, 80, 34);
		panel_3.add(label_2);
		
		JLabel lblNewLabel = new JLabel("成本调价收入：");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel.setBounds(458, 64, 105, 24);
		panel_3.add(lblNewLabel);
		
		JLabel label_3 = new JLabel("进退货差价收入：");
		label_3.setFont(new Font("宋体", Font.PLAIN, 15));
		label_3.setBounds(177, 92, 120, 34);
		panel_3.add(label_3);
		
		JLabel label_4 = new JLabel("报溢收入：");
		label_4.setFont(new Font("宋体", Font.PLAIN, 15));
		label_4.setBounds(468, 98, 82, 24);
		panel_3.add(label_4);
		
		JLabel label_5 = new JLabel("代金券收入：");
		label_5.setFont(new Font("宋体", Font.PLAIN, 15));
		label_5.setBounds(202, 127, 95, 24);
		panel_3.add(label_5);
		
		JLabel label_6 = new JLabel("折让前总收入：");
		label_6.setFont(new Font("宋体", Font.PLAIN, 15));
		label_6.setBounds(458, 132, 105, 29);
		panel_3.add(label_6);
		
		JLabel label_7 = new JLabel("总折让：");
		label_7.setFont(new Font("宋体", Font.PLAIN, 15));
		label_7.setBounds(217, 161, 60, 24);
		panel_3.add(label_7);
		
		JLabel label_8 = new JLabel("折让后总收入：");
		label_8.setFont(new Font("宋体", Font.PLAIN, 15));
		label_8.setBounds(458, 171, 105, 19);
		panel_3.add(label_8);
		
		JLabel label_9 = new JLabel("支出类");
		label_9.setFont(new Font("宋体", Font.PLAIN, 30));
		label_9.setBounds(348, 211, 100, 42);
		panel_3.add(label_9);
		
		JLabel label_10 = new JLabel("销售成本：");
		label_10.setFont(new Font("宋体", Font.PLAIN, 15));
		label_10.setBounds(217, 268, 80, 34);
		panel_3.add(label_10);
		
		JLabel label_11 = new JLabel("赠品支出：");
		label_11.setFont(new Font("宋体", Font.PLAIN, 15));
		label_11.setBounds(468, 278, 82, 24);
		panel_3.add(label_11);
		
		JLabel label_12 = new JLabel("报损支出：");
		label_12.setFont(new Font("宋体", Font.PLAIN, 15));
		label_12.setBounds(217, 312, 80, 24);
		panel_3.add(label_12);
		
		JLabel label_13 = new JLabel("总支出：");
		label_13.setFont(new Font("宋体", Font.PLAIN, 15));
		label_13.setBounds(478, 317, 72, 19);
		panel_3.add(label_13);
		
		JLabel label_14 = new JLabel("总利润");
		label_14.setFont(new Font("宋体", Font.PLAIN, 30));
		label_14.setBounds(348, 368, 100, 42);
		panel_3.add(label_14);
		
		label_profit = new JLabel("88888888");
		label_profit.setHorizontalAlignment(SwingConstants.CENTER);
		label_profit.setFont(new Font("宋体", Font.PLAIN, 30));
		label_profit.setBounds(330, 420, 138, 49);
		panel_3.add(label_profit);
		
		label_salein = new JLabel("10000000");
		label_salein.setHorizontalAlignment(SwingConstants.CENTER);
		label_salein.setFont(new Font("宋体", Font.PLAIN, 15));
		label_salein.setBounds(307, 69, 72, 15);
		panel_3.add(label_salein);
		
		label_chajia = new JLabel("10000");
		label_chajia.setHorizontalAlignment(SwingConstants.CENTER);
		label_chajia.setFont(new Font("宋体", Font.PLAIN, 15));
		label_chajia.setBounds(307, 102, 72, 15);
		panel_3.add(label_chajia);
		
		label_credit = new JLabel("10000");
		label_credit.setHorizontalAlignment(SwingConstants.CENTER);
		label_credit.setFont(new Font("宋体", Font.PLAIN, 15));
		label_credit.setBounds(307, 132, 72, 15);
		panel_3.add(label_credit);
		
		label_discount = new JLabel("10000");
		label_discount.setHorizontalAlignment(SwingConstants.CENTER);
		label_discount.setFont(new Font("宋体", Font.PLAIN, 15));
		label_discount.setBounds(307, 166, 72, 15);
		panel_3.add(label_discount);
		
		label_change = new JLabel("10000");
		label_change.setHorizontalAlignment(SwingConstants.CENTER);
		label_change.setFont(new Font("宋体", Font.PLAIN, 15));
		label_change.setBounds(571, 69, 72, 15);
		panel_3.add(label_change);
		
		label_overin = new JLabel("10000");
		label_overin.setHorizontalAlignment(SwingConstants.CENTER);
		label_overin.setFont(new Font("宋体", Font.PLAIN, 15));
		label_overin.setBounds(571, 102, 72, 15);
		panel_3.add(label_overin);
		
		label_income = new JLabel("10000");
		label_income.setHorizontalAlignment(SwingConstants.CENTER);
		label_income.setFont(new Font("宋体", Font.PLAIN, 15));
		label_income.setBounds(571, 136, 72, 15);
		panel_3.add(label_income);
		
		label_incomeAfterDiscount = new JLabel("10000");
		label_incomeAfterDiscount.setHorizontalAlignment(SwingConstants.CENTER);
		label_incomeAfterDiscount.setFont(new Font("宋体", Font.PLAIN, 15));
		label_incomeAfterDiscount.setBounds(571, 170, 72, 15);
		panel_3.add(label_incomeAfterDiscount);
		
		label_saleout = new JLabel("10000");
		label_saleout.setHorizontalAlignment(SwingConstants.CENTER);
		label_saleout.setFont(new Font("宋体", Font.PLAIN, 15));
		label_saleout.setBounds(307, 278, 72, 15);
		panel_3.add(label_saleout);
		
		label_overout = new JLabel("10000");
		label_overout.setHorizontalAlignment(SwingConstants.CENTER);
		label_overout.setFont(new Font("宋体", Font.PLAIN, 15));
		label_overout.setBounds(307, 317, 72, 15);
		panel_3.add(label_overout);
		
		label_present = new JLabel("10000");
		label_present.setHorizontalAlignment(SwingConstants.CENTER);
		label_present.setFont(new Font("宋体", Font.PLAIN, 15));
		label_present.setBounds(571, 283, 72, 15);
		panel_3.add(label_present);
		
		label_expense = new JLabel("10000");
		label_expense.setHorizontalAlignment(SwingConstants.CENTER);
		label_expense.setFont(new Font("宋体", Font.PLAIN, 15));
		label_expense.setBounds(571, 317, 72, 15);
		panel_3.add(label_expense);
		
		showData("0000-00-00", "9999-99-99");
	}
	
	public void showData(String startTime, String endTime){
		ProgressBLService service = new ProgressBL();
		circumstance = service.getCircumstance(startTime, endTime);
		label_chajia.setText(String.valueOf(circumstance.getChajia()));
		label_change.setText(String.valueOf(circumstance.getChange()));
		label_credit.setText(String.valueOf(circumstance.getCredit()));
		label_discount.setText(String.valueOf(circumstance.getDiscount()));
		label_expense.setText(String.valueOf(circumstance.getExpense()));
		label_income.setText(String.valueOf(circumstance.getIncome()));
		label_overin.setText(String.valueOf(circumstance.getOverin()));
		label_overout.setText(String.valueOf(circumstance.getOverout()));
		label_present.setText(String.valueOf(circumstance.getPresent()));
		label_profit.setText(String.valueOf(circumstance.getProfit()));
		label_salein.setText(String.valueOf(circumstance.getSalein()));
		label_saleout.setText(String.valueOf(circumstance.getSaleout()));
		label_incomeAfterDiscount.setText(String.valueOf(circumstance.getIncome() - circumstance.getDiscount()));
	}
	
	class CheckListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String start = chooseDatePanel.getStartDate();
			String end = chooseDatePanel.getEndDate();
			showData(start, end);
		}
		
	}
	
	class ExportListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setSelectedFile(new File("经营情况.xls"));
			int result = fc.showSaveDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				ExportToExcel tool = new ExportToExcel();
				String filepath = getPath(file);
				tool.toExcel(circumstance, filepath);
				JOptionPane.showMessageDialog(null, "导出成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		private String getPath(File file){
			int count = 0;
			File parent = file.getParentFile();
			String name = file.getName();
			String originalName = name.split("\\.")[0];
			File[] list = parent.listFiles();
			for(int i=0;i<list.length;i++){
				if(list[i].getName().equals(name)){
					count++;
					name = originalName+"("+count+").xls";
					i=-1;
					continue;
				}
			}
			
			return parent.getPath()+"/"+name;
		}
	}
	
	public static void main(String[] args){
		CircumstancePanelNew panel = new CircumstancePanelNew();
		JFrame frame = new JFrame();
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 640);
		frame.setVisible(true);
	}
}
