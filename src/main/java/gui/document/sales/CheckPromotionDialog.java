package gui.document.sales;

import gui.GUIUtility;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import VO.PromotionVO;

public class CheckPromotionDialog extends JDialog {

	private static final long serialVersionUID = 5871423280780305342L;
	private JTable table;

	/**
	 * Create the dialog.
	 */
	public CheckPromotionDialog(JDialog parent,ArrayList<PromotionVO> list) {
		super(parent,true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("查看促销策略");
		
		setSize(800, 300);
		GUIUtility.setCenter(this);
		
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable(new PromotionTableModel(list));
				scrollPane.setViewportView(table);
			}
		}
	}

}
