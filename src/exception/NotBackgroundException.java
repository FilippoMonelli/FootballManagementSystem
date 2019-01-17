package exception;

public class NotBackgroundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotBackgroundException(){
		
	}
	
	public NotBackgroundException(String msg){
		super(msg);
	}
	
}
