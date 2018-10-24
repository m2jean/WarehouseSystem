package gui.inventory;

import java.util.Comparator;

public class InventoryDateComparator implements Comparator<InventoryLineItem> {

	public InventoryDateComparator() {
	}

	@Override
	public int compare(InventoryLineItem o1, InventoryLineItem o2) {
		return o1.getDate().compareTo(o2.getDate());
	}

}
