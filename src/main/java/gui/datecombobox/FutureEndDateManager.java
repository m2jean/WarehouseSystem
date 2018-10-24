package gui.datecombobox;

import java.awt.event.ItemEvent;
import java.util.Calendar;

public class FutureEndDateManager extends DateComboBoxManager {
	private FutureStartDateManager start;

	protected FutureEndDateManager(DateComboBoxManager end) {
		super(end.getYearBox(),end.getMonthBox(),end.getDayBox());
	}
	
	public static FutureEndDateManager getInstance(DateComboBoxManager start,DateComboBoxManager end){
		FutureStartDateManager startDate = new FutureStartDateManager(start);
		FutureEndDateManager endDate = new FutureEndDateManager(end);
		startDate.setEndDateManager(endDate);
		endDate.setStartDateManager(startDate);
		return endDate;
	}

	public FutureStartDateManager getStartDateManager() {
		return start;
	}

	public void setStartDateManager(FutureStartDateManager start) {
		this.start = start;
		Calendar futureEnd = (Calendar) current.clone();
		futureEnd.add(Calendar.YEAR,2);
		refill(current,futureEnd);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED){
			current = Calendar.getInstance();
			Calendar futureEnd = (Calendar) current.clone();
			futureEnd.add(Calendar.YEAR,2);
			refill(current,futureEnd);
			start.refill(current, getCalendar());
		}
	}
	
	
}
