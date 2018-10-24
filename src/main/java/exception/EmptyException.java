package exception;

public class EmptyException extends Exception {

	private static final long serialVersionUID = -3353821081450640781L;

	public EmptyException(String description){
		super(description);
	}
}
