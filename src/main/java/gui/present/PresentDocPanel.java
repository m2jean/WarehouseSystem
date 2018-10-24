package gui.present;

import javax.swing.JOptionPane;

import inventoryBL.InventoryBL;
import inventoryBLService.InventoryBLService;
import VO.PresentVO;
import enums.DocumentType;
import enums.ResultMessage;
import enums.Status;
import gui.document.component.DocPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JScrollPane;

import javax.swing.JTable;

import progressBL.ProgressBL;
import progressBLService.ProgressBLService;

public class PresentDocPanel extends DocPanel{

	private static final long serialVersionUID = -6599457833782487361L;
	private PresentVO present;
	private JTable table;
	private PresentTableModel model;
	private JLabel label_date;
	private JLabel label_operator;
	private JLabel label_status;
	
	public PresentDocPanel(PresentVO present) {
		super(present);
		
		this.present = present;
		pnl_content.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		pnl_content.add(panel, BorderLayout.WEST);
		/*panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("75px"),
				ColumnSpec.decode("80px"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("18px"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));*/
		
		JLabel label = new JLabel("填写日期：");
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		panel.add(label, "1, 2, left, center");
		
		label_date = new JLabel("2014-12-14");
		label_date.setFont(new Font("宋体", Font.PLAIN, 15));
		panel.add(label_date, "2, 2, left, center");
		
		JLabel label_2 = new JLabel("操作人：");
		label_2.setFont(new Font("宋体", Font.PLAIN, 15));
		panel.add(label_2, "1, 3");
		
		label_operator = new JLabel("许珂磊");
		label_operator.setFont(new Font("宋体", Font.PLAIN, 15));
		panel.add(label_operator, "2, 3");
		
		JLabel label_3 = new JLabel("状态：");
		label_3.setFont(new Font("宋体", Font.PLAIN, 15));
		panel.add(label_3, "1, 4");
		
		label_status = new JLabel("草稿");
		label_status.setFont(new Font("宋体", Font.PLAIN, 15));
		panel.add(label_status, "2, 4");
		
		JScrollPane scrollPane = new JScrollPane();
		pnl_content.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		showInfo();
	}
	
	public void showInfo(){
		label_date.setText(present.getDate());
		label_operator.setText(present.getOperator());
		label_status.setText(present.getStatus().toString());
		model = new PresentTableModel(present.getProductList());
		table.setModel(model);
	}

	@Override
	public DocumentType getType() {
		return DocumentType.PRESENT;
	}

	@Override
	public void check() {
		PresentCheckFrame frame = new PresentCheckFrame(present);
		frame.setVisible(true);
	}

	@Override
	public void update() {
		if(present.getStatus() != Status.DRAFT){
			JOptionPane.showMessageDialog(null, "该赠品单不能删除", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		PresentUpdateFrame frame = new PresentUpdateFrame(present);
		frame.setVisible(true);
	}

	@Override
	public void delete() {
		if(present.getStatus() != Status.DRAFT){
			JOptionPane.showMessageDialog(null, "该赠品单不能删除", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		InventoryBLService service = new InventoryBL();
		ResultMessage message = service.deletePresentDraft(present);
		
		if(message == ResultMessage.SUCCESS){
			JOptionPane.showMessageDialog(null, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
		}else if(message == ResultMessage.ITEM_NOT_EXIST){
			JOptionPane.showMessageDialog(null, "该赠品单已被删除", "错误", JOptionPane.ERROR_MESSAGE);
		}else if(message == ResultMessage.REMOTE_FAIL){
			JOptionPane.showMessageDialog(null, "网络连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public ResultMessage getDeletionResult() {
		InventoryBLService service = new InventoryBL();
		ResultMessage message = service.deletePresentDraft(present);
		return message;
	}

	@Override
	public void examine() {
		PresentShenPiFrame frame = new PresentShenPiFrame(present);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public ResultMessage getExaminationResult(boolean pass) {
		InventoryBLService service = new InventoryBL();
		if(pass){
			present.setStatus(Status.PASS);
		}else{
			present.setStatus(Status.FAIL);
		}
		
		ResultMessage message = service.updatePresentTable(present);
		if(message != ResultMessage.SUCCESS){
			present.setStatus(Status.SUBMIT);
		}
		
		return message;
	}

	@Override
	public void redcopy() {
		if(present.isHong()){
			JOptionPane.showMessageDialog(null, "红冲单不能再次红冲", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}else if(present.getStatus() != Status.PASS){
			JOptionPane.showMessageDialog(null, "未审批通过的单子不能红冲", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		PresentHongFrame frame = new PresentHongFrame(present);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public ResultMessage getRedResult() {
		present.setHong(true);
		ProgressBLService service = new ProgressBL();
		PresentVO p = service.hong(present);
		ResultMessage message = service.hongcopy(p);
		return message;
	}

}
