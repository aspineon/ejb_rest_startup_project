package it.restproj.exceptions.jaxRs;

import java.io.Serializable;

public class GenericRuntimeException extends Exception implements Serializable {
	
	private static final long serialVersionUID = 1L;
    public GenericRuntimeException() {
        super();
    }
    public GenericRuntimeException(String msg)   {
        super(msg);
    }
    public GenericRuntimeException(String msg, Exception e)  {
        super(msg, e);
    }
	
}
