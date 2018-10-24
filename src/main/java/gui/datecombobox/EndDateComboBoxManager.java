package gui.datecombobox;

import java.awt.event.ItemEvent;
import java.util.Calendar;

public class EndDateComboBoxManager extends DateComboBoxManager {
	private StartDateComboBoxManager start;

	protected EndDateComboBoxManager(DateComboBoxManager end) {
		super(end.getYearBox(),end.getMonthBox(),end.getDayBox());
	}
	
	public static EndDateComboBoxManager getInstance(DateComboBoxManager start,DateComboBoxManager end){
		StartDateComboBoxManager startDate = new StartDateComboBoxManager(start);
		EndDateComboBoxManager endDate = new EndDateComboBoxManager(end);
		startDate.setEndDateManager(endDate);
		endDate.setStartDateManager(startDate);
		return endDate;
	}

	public StartDateComboBoxManager getStartDateManager() {
		return start;
	}

	public void setStartDateManager(StartDateComboBoxManager start) {
		this.start = start;
		refill(start.getCalendar(),current);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED){
			current = Calendar.getInstance();
			refill(start.getCalendar(),current);
			start.refill(initial, getCalendar());
		}
	}
	
	
}
