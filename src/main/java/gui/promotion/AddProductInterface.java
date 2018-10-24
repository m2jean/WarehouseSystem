package gui.promotion;

import productBL.ProductItem;

public interface AddProductInterface {
	public void addProduct(ProductItem item, double total) throws ItemExistException;
	public void updateProduct(ProductItem item, double difference);
}
