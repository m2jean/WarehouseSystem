package gui.datecombobox;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

public class ChangeFutureDatePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8851754778860008819L;
	private String startDate;
	private ChangeFutureDateManager dcbm_end;
	/**
	 * Create the panel.
	 */
	public ChangeFutureDatePanel(String startDate,String endDate) {
		setBorder(null);
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		JLabel label = new JLabel("选择时段：");
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		add(label);
		
		JLabel lbl_start = new JLabel(startDate);
		add(lbl_start);
		
		JLabel label_1 = new JLabel(" 至");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(label_1);
		
		JComboBox<Integer> cbx_endYear = new JComboBox<Integer>();
		add(cbx_endYear);
		
		JComboBox<Integer> cbx_endMonth = new JComboBox<Integer>();
		add(cbx_endMonth);
		
		this.startDate = startDate;
		JComboBox<Integer> cbx_endDay = new JComboBox<Integer>();
		add(cbx_endDay);
		
		String[] str = endDate.trim().split("-");
		int[] args = new int[3];
		for(int i = 0;i < 3;i++)
			args[i] = Integer.parseInt(str[i]);
		
		dcbm_end = new ChangeFutureDateManager(cbx_endYear,cbx_endMonth,cbx_endDay,args[0],args[1]-1,args[2]);
		
	}

	
	public String getStartDate(){
		return startDate;
	}
	
	public String getEndDate(){
		return dcbm_end.getFormattedDate();
	}
}
