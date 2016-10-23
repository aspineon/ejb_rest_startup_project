package it.restproj.auth;

import it.restproj.entities.enums.Role;
import it.restproj.exceptions.jaxRs.IncorrectUserRoleException;
import it.restproj.exceptions.jaxRs.UnauthorizedUserException;
import it.restproj.rest.BaseRest;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredRole
@Interceptor
@Priority(400)
public class RequiredRoleInterceptor {
	
	Logger log = LogManager.getLogger();
	
	@AroundInvoke
	public Object checkLoggedIn(InvocationContext context) throws Exception {
		BaseRest endpoint = (BaseRest) context.getTarget();
		log.info("checking if user is logged in and is authorized");
		if(endpoint.getLoggedUser() == null) {
			log.info("jwt token not valid, no authenticated user");
			endpoint.getHttpResponse().setStatus(403); // TODO: E' CORRETTO???
			throw new UnauthorizedUserException("User not logged in, token not valid");
		} else {
			log.info("user is correctly logged and autorized");
			RequiredRole requiredRole = (RequiredRole) context.getMethod().getAnnotation(RequiredRole.class);
			if (requiredRole.value().length > 0 && !userHasNeededRole(endpoint, requiredRole))
				throw new IncorrectUserRoleException("User can't do this operation (specific role needed).");
			return context.proceed();
		}
	}
	
	private boolean userHasNeededRole(BaseRest rest, RequiredRole requiredRole) {

		for (Role rIn : rest.getCurrentUserRole()) {
			for (Role rNeed : requiredRole.value()) {
				if (rIn.equals(rNeed)) {
					return true;
				}
			}
		}

		return false;
	}
	
}
