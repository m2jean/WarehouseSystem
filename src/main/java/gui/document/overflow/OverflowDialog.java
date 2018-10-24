package gui.document.overflow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import VO.OverflowVO;
import gui.DefaultTextField;
import gui.ResultDialog;

public abstract class OverflowDialog extends ResultDialog {

	private static final long serialVersionUID = 1521253374798779811L;
	private final JPanel contentPanel = new JPanel();
	protected DefaultTextField txf_actual;
	protected JTextField txf_stock;
	protected JLabel lbl_id;
	protected JLabel lbl_record;
	protected OverflowVO vo;
	protected JLabel lbl_msg;


	/**
	 * Create the dialog.
	 */
	public OverflowDialog(Frame parent) {
		super(parent,true);
		
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				close();
			}
		});
		//setResizable(false);
		//setSize(450, 155);
		//GUIUtility.setCenter(this);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(15, 15, 5, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		/*gbl_contentPanel.columnWidths = new int[]{0, 147, 38, 80, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};*/
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lbl_idlbl = new JLabel("编号：");
			GridBagConstraints gbc_lbl_id = new GridBagConstraints();
			gbc_lbl_id.anchor = GridBagConstraints.EAST;
			gbc_lbl_id.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_id.gridx = 0;
			gbc_lbl_id.gridy = 0;
			contentPanel.add(lbl_idlbl, gbc_lbl_id);
		}
		{
			lbl_id = new JLabel("待定");
			GridBagConstraints gbc_lbl_id = new GridBagConstraints();
			gbc_lbl_id.anchor = GridBagConstraints.WEST;
			gbc_lbl_id.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_id.gridx = 1;
			gbc_lbl_id.gridy = 0;
			contentPanel.add(lbl_id, gbc_lbl_id);
		}
		{
			JLabel lbl_stock = new JLabel("仓库：");
			GridBagConstraints gbc_lbl_stock = new GridBagConstraints();
			gbc_lbl_stock.anchor = GridBagConstraints.EAST;
			gbc_lbl_stock.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_stock.gridx = 0;
			gbc_lbl_stock.gridy = 1;
			contentPanel.add(lbl_stock, gbc_lbl_stock);
		}
		{
			txf_stock = new JTextField();
			GridBagConstraints gbc_txf_stock = new GridBagConstraints();
			gbc_txf_stock.gridwidth = 2;
			gbc_txf_stock.insets = new Insets(0, 0, 5, 0);
			gbc_txf_stock.fill = GridBagConstraints.HORIZONTAL;
			gbc_txf_stock.gridx = 1;
			gbc_txf_stock.gridy = 1;
			contentPanel.add(txf_stock, gbc_txf_stock);
			txf_stock.setColumns(10);
		}
		{
			JLabel lbl_prod = new JLabel("商品：");
			GridBagConstraints gbc_lbl_prod = new GridBagConstraints();
			gbc_lbl_prod.anchor = GridBagConstraints.EAST;
			gbc_lbl_prod.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_prod.gridx = 0;
			gbc_lbl_prod.gridy = 2;
			contentPanel.add(lbl_prod, gbc_lbl_prod);
		}
		{
			GridBagConstraints gbc_lbl_product = new GridBagConstraints();
			gbc_lbl_product.anchor = GridBagConstraints.WEST;
			gbc_lbl_product.gridwidth = 4;
			gbc_lbl_product.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_product.gridx = 1;
			gbc_lbl_product.gridy = 2;
			contentPanel.add(getProductComponent(), gbc_lbl_product);
		}
		{
			JLabel lbl_reclbl = new JLabel("记录数量：");
			GridBagConstraints gbc_lbl_record = new GridBagConstraints();
			gbc_lbl_record.anchor = GridBagConstraints.EAST;
			gbc_lbl_record.insets = new Insets(0, 0, 0, 5);
			gbc_lbl_record.gridx = 0;
			gbc_lbl_record.gridy = 3;
			contentPanel.add(lbl_reclbl, gbc_lbl_record);
		}
		{
			lbl_record = new JLabel("");
			GridBagConstraints gbc_lbl_record = new GridBagConstraints();
			gbc_lbl_record.anchor = GridBagConstraints.WEST;
			gbc_lbl_record.insets = new Insets(0, 0, 0, 5);
			gbc_lbl_record.gridx = 1;
			gbc_lbl_record.gridy = 3;
			contentPanel.add(lbl_record, gbc_lbl_record);
		}
		{
			JLabel lbl_actual = new JLabel("实际数量：");
			GridBagConstraints gbc_lbl_actual = new GridBagConstraints();
			gbc_lbl_actual.anchor = GridBagConstraints.EAST;
			gbc_lbl_actual.insets = new Insets(0, 0, 0, 5);
			gbc_lbl_actual.gridx = 0;
			gbc_lbl_actual.gridy = 4;
			contentPanel.add(lbl_actual, gbc_lbl_actual);
		}
		{
			txf_actual = new DefaultTextField();
			GridBagConstraints gbc_txf_actual = new GridBagConstraints();
			gbc_txf_actual.insets = new Insets(0, 0, 0, 5);
			gbc_txf_actual.fill = GridBagConstraints.HORIZONTAL;
			gbc_txf_actual.gridx = 1;
			gbc_txf_actual.gridy = 4;
			contentPanel.add(txf_actual, gbc_txf_actual);
			txf_actual.setColumns(10);
			//txf_actual.addFocusListener(GUIUtility.getFocusListenerForInt(txf_actual, this));
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.X_AXIS));
			buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

			buttonPane.add(Box.createHorizontalGlue());
			lbl_msg = new JLabel();
			lbl_msg.setForeground(Color.RED);
			buttonPane.add(lbl_msg);

			buttonPane.add(Box.createHorizontalStrut(10));
			addButtons(buttonPane);
			buttonPane.add(Box.createHorizontalStrut(10));
			{
				JButton btn_cancel = new JButton("取消");
				btn_cancel.setActionCommand("Cancel");
				btn_cancel.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						close();
					}
				});
				buttonPane.add(btn_cancel);
			}
			buttonPane.add(Box.createHorizontalStrut(5));
		}
	}


	protected abstract void addButtons(JPanel buttonPane);


	protected abstract Component getProductComponent();

}
