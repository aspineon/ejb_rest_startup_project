package eu.limontacolori.privatearea.auth.jwt;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Lock;
import javax.ejb.Singleton;

@Singleton
@Lock
public class JwtStorageManager {
		
	private Set<String> issuedTokens = new HashSet<String>();
	
	public Set<String> getIssuedTokens() {
		return issuedTokens;
	}
	
	public Set<String> getIssuedTokensCopy() {
		return new HashSet<String>(issuedTokens);
	}

	public boolean addIssuedToken(String jwt) {
		return this.issuedTokens.add(jwt);
	}
	
	public boolean checkTokenIsValid(String jwt) {
		return this.issuedTokens.contains(jwt);
	}
	
	public boolean removeIssuedToken(String jwt) {
		return this.issuedTokens.remove(jwt);
	}
	
}
