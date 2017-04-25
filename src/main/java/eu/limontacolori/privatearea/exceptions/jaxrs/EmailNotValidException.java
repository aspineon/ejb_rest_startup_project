package eu.limontacolori.privatearea.exceptions.jaxrs;

public class EmailNotValidException extends Exception {
	
	private static final long serialVersionUID = 1L;
    public EmailNotValidException() {
        super();
    }
    public EmailNotValidException(String msg)   {
        super(msg);
    }
    public EmailNotValidException(String msg, Exception e)  {
        super(msg, e);
    }

}
