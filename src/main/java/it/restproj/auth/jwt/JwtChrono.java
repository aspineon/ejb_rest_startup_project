package it.restproj.auth.jwt;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class JwtChrono {
	
	@Inject
	JwtStorageManager jwtStorageManager;
	
	@Inject
	JwtManager jwtManager;
	
	@Lock(LockType.READ)
	@Schedule(minute = "*/10", hour = "*")
	private void removeInvalidTokens() {
		jwtStorageManager.getIssuedTokens().forEach((singleToken) -> {
			try {
				jwtManager.validateToken(singleToken);
			} catch (Exception exc) {
				jwtStorageManager.removeIssuedToken(singleToken);
			}
		});
	}
	
}
