package factory;

public class Info extends Throwable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7099786238386146514L;

	private boolean success;
	//仅用于分类树的操作，找到对应分类PO后抛出
	Info(boolean b){
		success = b;
	}
	
	public boolean success(){
		return success;
	}
}
