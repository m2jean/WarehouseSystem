package gui.datecombobox;

import java.awt.event.ItemEvent;
import java.util.Calendar;

public class FutureStartDateManager extends DateComboBoxManager {
	private FutureEndDateManager end;

	protected FutureStartDateManager(DateComboBoxManager start) {
		super(start.getYearBox(),start.getMonthBox(),start.getDayBox());
	}

	public static FutureStartDateManager getInstance(DateComboBoxManager start,DateComboBoxManager end){
		FutureStartDateManager startDate = new FutureStartDateManager(start);
		FutureEndDateManager endDate = new FutureEndDateManager(end);
		startDate.setEndDateManager(endDate);
		endDate.setStartDateManager(startDate);
		return startDate;
	}

	public FutureEndDateManager getEndDateManager() {
		return end;
	}
	
	public void setEndDateManager(FutureEndDateManager end){
		this.end = end; 
		refill(current,end.getCalendar());
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED){
			current = Calendar.getInstance();
			refill(current,end.getCalendar());
			Calendar futureEnd = (Calendar) current.clone();
			futureEnd.add(Calendar.YEAR,2);
			end.refill(current,futureEnd);
		}
	}
	
	
}
