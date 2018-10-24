package gui.datecombobox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JComboBox;

public class DateComboBoxManager implements ItemListener{
	protected JComboBox<Integer> year;
	protected JComboBox<Integer> month;
	protected JComboBox<Integer> day;
	public static Calendar current;	
	protected static Calendar initial;
	static {
		current = Calendar.getInstance();
		initial = Calendar.getInstance();
		initial.set(2014, 0, 1);
	}
	
	public DateComboBoxManager(JComboBox<Integer> year,JComboBox<Integer> month,JComboBox<Integer> day){
		this.year = year;
		this.month = month;
		this.day = day;
		init();
	}
	
	private void init(){
		removeListener();
		current = Calendar.getInstance();
		int[] selected = getDate(current);
		year.addItem(selected[0]);
		month.addItem(selected[1]);
		day.addItem(selected[2]);
		year.setSelectedItem(selected[0]);
		month.setSelectedItem(selected[1]);
		day.setSelectedItem(selected[2]);
		refill(initial,current);
	}
	
	protected void addListener(){
		year.addItemListener(this);
		month.addItemListener(this);
		day.addItemListener(this);
	}
	
	protected void removeListener(){
		for(ItemListener lis:year.getItemListeners()){
			year.removeItemListener(lis);
		}
		for(ItemListener lis:month.getItemListeners()){
			month.removeItemListener(lis);
		}
		for(ItemListener lis:day.getItemListeners()){
			day.removeItemListener(lis);
		}
	}
	
	protected void refill(Calendar startDate,Calendar endDate){
		removeListener();
		int[] selected = getDate(getCalendar());
		int[] start = getDate(startDate);
		int[] end = getDate(endDate);
		
		year.removeAllItems();
		month.removeAllItems();
		day.removeAllItems();
		
		for(int i = end[0];i >= start[0];i--){
			year.addItem(i);
		}
		
		for(int i = 12;i >= 1;i--){
			month.addItem(i);
		}
		if(selected[0] <= start[0]){
			selected[0] = start[0];
			for(int i = month.getItemCount()-1;month.getItemAt(i) < start[1];i--){
				month.removeItemAt(i);
			}
		}
		if(selected[0] >= end[0]){
			selected[0] = end[0];
			for(int i = 0;month.getItemAt(i) > end[1];i++){
				month.removeItemAt(i--);
			}
		}
		year.setSelectedItem(selected[0]);
		
		for(int i = 31;i >= 1;i--){
			day.addItem(i);
		}
		if(selected[0] == start[0] && selected[1] <= start[1]){
			selected[1] = start[1];
			for(int i = day.getItemCount()-1;day.getItemAt(i) < start[2];i--){
				day.removeItemAt(i);
			}
		}
		if(selected[0] == end[0] && selected[1] >= end[1]){
			selected[1] = end[1];
			for(int i = 0;day.getItemAt(i) > end[2];i++){
				day.removeItemAt(i--);
			}
		}
		month.setSelectedItem(selected[1]);
		for(int i = 0;day.getItemAt(i) > getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);i++){
			day.removeItemAt(i--);
		}
		
		if(selected[0] == start[0] && selected[1] == start[1] && selected[2] < start[2])
			selected[2] = start[2];
		else if(selected[0] == end[0] && selected[1] == end[1] && selected[2] > end[2])
			selected[2] = end[2];
		day.setSelectedItem(selected[2]);
		
		addListener();
	}
	
	public String getFormattedDate(){
		return getFormattedCalendar(getCalendar());
	}
	
	public Calendar getCalendar(){
		Calendar c =Calendar.getInstance();
		c.set((int)year.getSelectedItem(),(int)month.getSelectedItem()-1,(int)day.getSelectedItem());
		return c;
	}
	
	public static String getFormattedCalendar(Calendar c){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	
	public static int[] getDate(Calendar c){
		int[] date = {c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1,c.get(Calendar.DATE)};
		return date;
	}
	
	public JComboBox<Integer> getYearBox(){
		return year;
	}

	public JComboBox<Integer> getMonthBox(){
		return month;
	}
	
	public JComboBox<Integer> getDayBox(){
		return day;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED){
			current = Calendar.getInstance();
			refill(initial,current);
		}
		
	}
	
	public void setDate(int year,int month,int day){
		this.year.setSelectedItem(year);
		this.month.setSelectedItem(month);
		this.day.setSelectedItem(day);
	}
}
