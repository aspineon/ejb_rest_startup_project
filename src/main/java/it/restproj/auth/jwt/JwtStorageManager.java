package it.restproj.auth.jwt;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Singleton;

@Singleton
public class JwtStorageManager {
	
	// TODO: Da mettere in cache distribuita assolutamente!!!
	
	private Set<String> issuedTokens = new HashSet<String>();
	
	public Set<String> getIssuedTokens() {
		return issuedTokens;
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
