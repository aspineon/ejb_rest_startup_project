package eu.limontacolori.privatearea.auth;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.limontacolori.privatearea.exceptions.jaxrs.UnauthorizedUserException;
import eu.limontacolori.privatearea.rest.BaseRest;

@TrackUser
@Interceptor
@Priority(200)
public class TrackAuthUserInterceptor {
	
	Logger log = LogManager.getLogger();
	
	@AroundInvoke
	public Object trackUser(InvocationContext context) throws Exception {
		BaseRest endpoint = (BaseRest) context.getTarget();
		log.info("validating jwt & user...");
		endpoint.validateUser();
		if(endpoint.getLoggedUser() != null) {
			return context.proceed();
		} else {
			throw new UnauthorizedUserException("User must be logged in");
			//Response response = Response.status(Status.FORBIDDEN).build()
			//throw new WebApplicationException(response);
		}
		
	}
	
}
