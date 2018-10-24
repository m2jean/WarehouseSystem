package gui.present;

import javax.swing.JFrame;
import inventoryBL.InventoryBL;
import inventoryBLService.InventoryBLService;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import gui.GUIUtility;
import VO.PresentVO;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PresentCheckFrame extends JFrame {

	private static final long serialVersionUID = -332628466536821747L;
	private JTable table;
	private JLabel label_name;
	private PresentTableModel model;
	private JLabel label_date;
	private PresentVO present;
	private JLabel label_status;
	
	public PresentCheckFrame(PresentVO present) {
		this.present = present;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		GUIUtility.setCenter(this);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton button = new JButton("返回");
		button.addActionListener(new CloseListener());
		panel.add(button);;
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{75, 72, 0, 60, 72, 0, 72, 0};
		gbl_panel_3.rowHeights = new int[]{18, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblId = new JLabel("填写日期：");
		lblId.setFont(new Font("宋体", Font.PLAIN, 15));
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.anchor = GridBagConstraints.WEST;
		gbc_lblId.insets = new Insets(0, 0, 0, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		panel_3.add(lblId, gbc_lblId);
		
		label_date = new JLabel("2014-12-13");
		label_date.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label_date = new GridBagConstraints();
		gbc_label_date.anchor = GridBagConstraints.WEST;
		gbc_label_date.insets = new Insets(0, 0, 0, 5);
		gbc_label_date.gridx = 1;
		gbc_label_date.gridy = 0;
		panel_3.add(label_date, gbc_label_date);
		
		JLabel label = new JLabel("填写人：");
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 3;
		gbc_label.gridy = 0;
		panel_3.add(label, gbc_label);
		
		label_name = new JLabel("许珂磊");
		label_name.setHorizontalAlignment(SwingConstants.CENTER);
		label_name.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label_name = new GridBagConstraints();
		gbc_label_name.anchor = GridBagConstraints.WEST;
		gbc_label_name.insets = new Insets(0, 0, 0, 5);
		gbc_label_name.gridx = 4;
		gbc_label_name.gridy = 0;
		panel_3.add(label_name, gbc_label_name);
		
		label_status = new JLabel("草稿");
		label_status.setHorizontalAlignment(SwingConstants.CENTER);
		label_status.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_label_status = new GridBagConstraints();
		gbc_label_status.anchor = GridBagConstraints.EAST;
		gbc_label_status.gridx = 6;
		gbc_label_status.gridy = 0;
		panel_3.add(label_status, gbc_label_status);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		
		showInfo();
	}
	
	public void showInfo(){
		if(present == null){
			JOptionPane.showMessageDialog(null, "NULLPOINTER", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		model = new PresentTableModel(present.getProductList());
		table.setModel(model);
		label_date.setText(present.getDate());
		label_name.setText(present.getOperator());
		label_status.setText(present.getStatus().toString());
	}
	
	class CloseListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}	
	}
	
	public static void main(String[] args){
		InventoryBLService service = new InventoryBL();
		PresentVO present = service.getPresentTable(new PresentVO("ZPD-20141214-00001")).data;
		PresentCheckFrame frame =new PresentCheckFrame(present);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

}