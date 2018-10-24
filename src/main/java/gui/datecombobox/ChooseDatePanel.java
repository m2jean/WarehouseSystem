package gui.datecombobox;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

public class ChooseDatePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8851754778860008819L;
	private StartDateComboBoxManager sdcm;
	private EndDateComboBoxManager edcm;
	/**
	 * Create the panel.
	 */
	public ChooseDatePanel() {
		setBorder(null);
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		JLabel label = new JLabel("选择时段：");
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		add(label);
		
		JComboBox<Integer> cbx_startYear = new JComboBox<Integer>();
		add(cbx_startYear);
		
		JComboBox<Integer> cbx_startMonth = new JComboBox<Integer>();
		add(cbx_startMonth);
		
		JComboBox<Integer> cbx_startDay = new JComboBox<Integer>();
		add(cbx_startDay);
		
		JLabel label_1 = new JLabel(" 至");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(label_1);
		
		JComboBox<Integer> cbx_endYear = new JComboBox<Integer>();
		add(cbx_endYear);
		
		JComboBox<Integer> cbx_endMonth = new JComboBox<Integer>();
		add(cbx_endMonth);
		
		JComboBox<Integer> cbx_endDay = new JComboBox<Integer>();
		add(cbx_endDay);
		
		sdcm = StartDateComboBoxManager.getInstance(new DateComboBoxManager(cbx_startYear,cbx_startMonth,cbx_startDay),new DateComboBoxManager(cbx_endYear,cbx_endMonth,cbx_endDay));
		edcm = sdcm.getEndDateManager();
	}

	
	public String getStartDate(){
		return sdcm.getFormattedDate();
	}
	
	public String getEndDate(){
		return edcm.getFormattedDate();
	}
}
