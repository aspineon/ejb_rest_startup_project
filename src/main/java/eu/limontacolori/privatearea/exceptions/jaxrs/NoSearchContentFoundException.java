package eu.limontacolori.privatearea.exceptions.jaxrs;

import java.io.Serializable;

public class NoSearchContentFoundException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
    public NoSearchContentFoundException() {
        super();
    }
    public NoSearchContentFoundException(String msg)   {
        super(msg);
    }
    public NoSearchContentFoundException(String msg, Exception e)  {
        super(msg, e);
    }
}
