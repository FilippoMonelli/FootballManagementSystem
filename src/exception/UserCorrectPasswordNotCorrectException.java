package exception;

public class UserCorrectPasswordNotCorrectException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserCorrectPasswordNotCorrectException(){
		
	}
	
	public UserCorrectPasswordNotCorrectException(String msg){
		super(msg);
	}
	
}
