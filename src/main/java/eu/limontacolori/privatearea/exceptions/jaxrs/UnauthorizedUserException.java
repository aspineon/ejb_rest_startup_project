package eu.limontacolori.privatearea.exceptions.jaxrs;

import java.io.Serializable;

public class UnauthorizedUserException extends Exception  implements Serializable {

	private static final long serialVersionUID = 1L;
    public UnauthorizedUserException() {
        super();
    }
    public UnauthorizedUserException(String msg)   {
        super(msg);
    }
    public UnauthorizedUserException(String msg, Exception e)  {
        super(msg, e);
    }
	
}
