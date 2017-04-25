package eu.limontacolori.privatearea.crossdomain;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;


@CrossDomain
@Interceptor
public class CrossDomainInterceptor {
	
	@Inject
    HttpServletRequest request;

    @AroundInvoke
    protected Object invoke(InvocationContext ctx) throws Exception {
        ctx.getParameters();
        if(request.getHeader("Origin") != null /* && {Put your domain restriction logic here} */) {
            return crossDomainResponse((Response) ctx.proceed(), request.getHeader("Origin"));
        } else {
            return ctx.proceed();
        }
    }
	
    public static Response crossDomainResponse(Response response, String origin) {
        if(origin != null) {
            return Response
                    .fromResponse(response)
                    .header("Access-Control-Allow-Origin", origin)
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Expose-Headers","EXAMPLE-CUSTOM-HEADER")
                    .build();
        }
        return response;
    }
    
}
