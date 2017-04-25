package eu.limontacolori.privatearea.exceptions.jaxrs;

public class BadImportException extends Exception {
	
	private static final long serialVersionUID = 1L;
    public BadImportException() {
        super();
    }
    public BadImportException(String msg)   {
        super(msg);
    }
    public BadImportException(String msg, Exception e)  {
        super(msg, e);
    }
	
}
