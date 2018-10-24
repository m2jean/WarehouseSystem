package gui.datecombobox;

import java.awt.event.ItemEvent;
import java.util.Calendar;

import javax.swing.JComboBox;

public class ChangeFutureDateManager extends DateComboBoxManager {
	private Calendar end;

	public ChangeFutureDateManager(JComboBox<Integer> year,
			JComboBox<Integer> month, JComboBox<Integer> day,int y,int m,int d) {
		super(year, month, day);
		end = Calendar.getInstance();
		end.set(y,m,d);
		Calendar future = (Calendar) end.clone();
		future.add(Calendar.YEAR,2);;
		refill(end,future);
		setDate(y,m,d);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED){
			Calendar future = (Calendar) end.clone();
			future.add(Calendar.YEAR,2);;
			refill(end,future);
		}
		
	}
}
