package it.restproj.exceptions.jaxRs;

import java.io.Serializable;

public class IncorrectUserRoleException extends Exception implements Serializable {
	
	private static final long serialVersionUID = 1L;
    public IncorrectUserRoleException() {
        super();
    }
    public IncorrectUserRoleException(String msg)   {
        super(msg);
    }
    public IncorrectUserRoleException(String msg, Exception e)  {
        super(msg, e);
    }
	
}
