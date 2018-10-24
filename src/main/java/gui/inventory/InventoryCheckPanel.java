package gui.inventory;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import VO.SnapshotVO;
import enums.DataMessage;
import enums.ResultMessage;
import factory.ExportToExcel;
import inventoryBL.InventoryBL;
import inventoryBLService.InventoryBLService;

public class InventoryCheckPanel extends JPanel {

	private static final long serialVersionUID = 1475685261041695442L;
	private JTable table;
	private SnapshotTableModel model;
	private SnapshotVO snapshot;
	
	public InventoryCheckPanel() {
		//setSize(800, 640);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("批次：2014-12-19");
		//label.setFont(new Font("宋体", Font.PLAIN, 15));
		panel.add(label);
		
		JLabel label_2 = new JLabel("批号：第1批");
		//label_2.setFont(new Font("宋体", Font.PLAIN, 15));
		panel.add(label_2);
		
		JButton button = new JButton("导出");
		button.addActionListener(new ExportListener());
		panel.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
		
		showData();
	}
	
	private void showData(){
		InventoryBLService service = new InventoryBL();
		DataMessage<SnapshotVO> dataMessage = service.stocking();
		if(dataMessage.resultMessage == ResultMessage.REMOTE_FAIL){
			JOptionPane.showMessageDialog(null, "网络连接出错", "错误", JOptionPane.ERROR_MESSAGE);
		}else if(dataMessage.resultMessage == ResultMessage.SUCCESS){
			snapshot = dataMessage.data;
			model = new SnapshotTableModel(snapshot);
			table.setModel(model);
			TableColumn col;
			col = table.getColumnModel().getColumn(0);
			col.setMaxWidth(40);
		}
	}
	
	class ExportListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			chooser.setSelectedFile(new File("库存盘点.xls"));
			int result = chooser.showSaveDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				ExportToExcel tool = new ExportToExcel();
				String filepath = getPath(file);
				
				tool.toExcel(snapshot, filepath);
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

}
