package gui.datecombobox;

import java.awt.event.ItemEvent;
import java.util.Calendar;

public class StartDateComboBoxManager extends DateComboBoxManager {
	private EndDateComboBoxManager end;

	protected StartDateComboBoxManager(DateComboBoxManager start) {
		super(start.getYearBox(),start.getMonthBox(),start.getDayBox());
	}

	public static StartDateComboBoxManager getInstance(DateComboBoxManager start,DateComboBoxManager end){
		StartDateComboBoxManager startDate = new StartDateComboBoxManager(start);
		EndDateComboBoxManager endDate = new EndDateComboBoxManager(end);
		startDate.setEndDateManager(endDate);
		endDate.setStartDateManager(startDate);
		return startDate;
	}

	public EndDateComboBoxManager getEndDateManager() {
		return end;
	}
	
	public void setEndDateManager(EndDateComboBoxManager end){
		this.end = end; 
		refill(initial,end.getCalendar());
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED){
			current = Calendar.getInstance();
			refill(initial,end.getCalendar());
			end.refill(getCalendar(), current);
		}
	}
	
	
}
