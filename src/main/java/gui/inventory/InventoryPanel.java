package gui.inventory;

import enums.DataMessage;
import gui.MainFrame;
import gui.datecombobox.DateComboBoxManager;
import gui.datecombobox.EndDateComboBoxManager;
import gui.datecombobox.StartDateComboBoxManager;
import inventoryBL.InventoryBL;
import inventoryBLService.InventoryBLService;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;

import javax.swing.JTabbedPane;
import javax.swing.JComboBox;

import VO.InventoryVO;

import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collections;

public class InventoryPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7286749578439976602L;
	
	private InventoryBLService invebl = new InventoryBL();
	private JTabbedPane tbpnl_inventory;
	private JPanel pnl_detail;
	private JPanel pnl_sum;
	private JTable tbl_detail;
	private JTable tbl_sum;
	private JPanel pnl_search;
	private JLabel lbl_choose;
	private JComboBox<Integer> cbx_startYear;
	private JComboBox<Integer> cbx_startMonth;
	private JComboBox<Integer> cbx_startDay;
	private JLabel lbl_to;
	private JComboBox<Integer> cbx_endYear;
	private JComboBox<Integer> cbx_endMonth;
	private JComboBox<Integer> cbx_endDay;
	private JButton btn_search;
	private JLabel lbl_numIn;
	private JLabel lbl_importAmount;
	private JLabel lbl_sumIn;
	private JLabel lbl_saleNumber;
	private JLabel lbl_numOut;
	private JLabel lbl_saleAmount;
	private JLabel lbl_sumOut;
	
	private StartDateComboBoxManager startDate;
	private EndDateComboBoxManager endDate;

	/**
	 * Create the panel.
	 */
	public InventoryPanel() {
		setPreferredSize(new Dimension(800, 640));
		setLayout(new BorderLayout(0, 0));
		
		pnl_search = new JPanel();
		add(pnl_search, BorderLayout.NORTH);
		pnl_search.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		
		lbl_choose = new JLabel("选择时段：");
		pnl_search.add(lbl_choose);
		
		cbx_startYear = new JComboBox<Integer>();
		pnl_search.add(cbx_startYear);
		
		cbx_startMonth = new JComboBox<Integer>();
		pnl_search.add(cbx_startMonth);
		
		cbx_startDay = new JComboBox<Integer>();
		pnl_search.add(cbx_startDay);
		
		lbl_to = new JLabel(" 至");
		pnl_search.add(lbl_to);
		
		cbx_endYear = new JComboBox<Integer>();
		pnl_search.add(cbx_endYear);
		
		cbx_endMonth = new JComboBox<Integer>();
		pnl_search.add(cbx_endMonth);
		
		cbx_endDay = new JComboBox<Integer>();
		pnl_search.add(cbx_endDay);
		
		btn_search = new JButton("查看");
		btn_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildInventoryPanel();
			}
		});
		pnl_search.add(btn_search);
		
		buildCheckBox();
		
		tbpnl_inventory = new JTabbedPane(JTabbedPane.TOP);
		tbpnl_inventory.setEnabled(false);
		add(tbpnl_inventory, BorderLayout.CENTER);
		
		pnl_detail = new JPanel();
		tbpnl_inventory.addTab("详细条目", null, pnl_detail, null);
		pnl_detail.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrp_detail = new JScrollPane();
		pnl_detail.add(scrp_detail, BorderLayout.CENTER);
		
		tbl_detail = new JTable();
		tbl_detail.setRowHeight(30);
		tbl_detail.getTableHeader().setReorderingAllowed(false);
		scrp_detail.setViewportView(tbl_detail);
		
		pnl_sum = new JPanel();
		pnl_sum.setBorder(new EmptyBorder(0, 20, 30, 20));
		tbpnl_inventory.addTab("总计", null, pnl_sum, null);
		pnl_sum.setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_total = new JPanel();
		pnl_total.setBorder(new EmptyBorder(20, 20, 0, 20));
		pnl_sum.add(pnl_total, BorderLayout.SOUTH);
		pnl_total.setLayout(new GridLayout(2, 4, 0, 10));
		
		JLabel lbl_importNum = new JLabel("商品进货总数：");
		lbl_importNum.setFont(new Font("宋体", Font.PLAIN, 19));
		lbl_importNum.setHorizontalAlignment(SwingConstants.TRAILING);
		pnl_total.add(lbl_importNum);
		
		lbl_numIn = new JLabel("New label");
		lbl_numIn.setFont(new Font("宋体", Font.PLAIN, 19));
		pnl_total.add(lbl_numIn);
		
		lbl_importAmount = new JLabel("商品进货总价：");
		lbl_importAmount.setFont(new Font("宋体", Font.PLAIN, 19));
		lbl_importAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		pnl_total.add(lbl_importAmount);
		
		lbl_sumIn = new JLabel("New label");
		lbl_sumIn.setFont(new Font("宋体", Font.PLAIN, 19));
		pnl_total.add(lbl_sumIn);
		
		lbl_saleNumber = new JLabel("商品销售总数：");
		lbl_saleNumber.setFont(new Font("宋体", Font.PLAIN, 19));
		lbl_saleNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		pnl_total.add(lbl_saleNumber);
		
		lbl_numOut = new JLabel("New label");
		lbl_numOut.setFont(new Font("宋体", Font.PLAIN, 19));
		pnl_total.add(lbl_numOut);
		
		lbl_saleAmount = new JLabel("商品销售总价:");
		lbl_saleAmount.setFont(new Font("宋体", Font.PLAIN, 19));
		lbl_saleAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		pnl_total.add(lbl_saleAmount);
		
		lbl_sumOut = new JLabel("New label");
		lbl_sumOut.setFont(new Font("宋体", Font.PLAIN, 19));
		pnl_total.add(lbl_sumOut);
		
		JScrollPane scrp_sum = new JScrollPane();
		pnl_sum.add(scrp_sum, BorderLayout.CENTER);
		
		tbl_sum = new JTable();
		tbl_sum.setRowHeight(30);
		tbl_sum.getTableHeader().setReorderingAllowed(false);
		scrp_sum.setViewportView(tbl_sum);
		
	}
	
	private void buildInventoryPanel() {
		//System.out.println(startDate.getFormattedDate()+ endDate.getFormattedDate());
		DataMessage<InventoryVO> result = invebl.check(startDate.getFormattedDate(), endDate.getFormattedDate());
		switch(result.resultMessage){
		case SUCCESS:
			break;
		case IS_EMPTY:
			JOptionPane.showMessageDialog(MainFrame.mf, "该时段内未找到记录！");
			return;
		default:
			JOptionPane.showMessageDialog(MainFrame.mf, "发生未知错误！");
			return;
		}
		tbpnl_inventory.setEnabled(true);
		InventoryVO vo = result.data;
		Collections.sort(vo.getItemlist(),new InventoryDateComparator());
		tbl_detail.setModel(new InventoryDetailTableModel(vo.getItemlist()));
		tbl_sum.setModel(new InventorySumTableModel(vo.getSumlist()));
		lbl_numIn.setText(String.valueOf(vo.getNumIn()));
		lbl_sumIn.setText(String.valueOf(vo.getSumIn()));
		lbl_numOut.setText(String.valueOf(vo.getNumOut()));
		lbl_sumOut.setText(String.valueOf(vo.getSumOut()));
	}


	private void buildCheckBox(){
		startDate = StartDateComboBoxManager.getInstance(
				new DateComboBoxManager(cbx_startYear, cbx_startMonth,cbx_startDay),
				new DateComboBoxManager(cbx_endYear, cbx_endMonth,cbx_endDay));
		endDate = startDate.getEndDateManager();
	}
}
