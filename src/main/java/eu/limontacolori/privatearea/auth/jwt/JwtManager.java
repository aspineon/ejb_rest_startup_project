package eu.limontacolori.privatearea.auth.jwt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import eu.limontacolori.privatearea.entities.enums.Role;
import eu.limontacolori.privatearea.exceptions.jaxrs.UnauthorizedUserException;
import eu.limontacolori.privatearea.rest.dto.LoggedUserDto;

@Singleton
@Lock
public class JwtManager {

	@Inject
	private LoggedUserDto userLogged;
	
	@Inject
	private JwtStorageManager jwtStorageManager;
	
	private Logger log = LogManager.getLogger();
	
	private String ISSUER = "eu.limontacolori";
	private String AUDIENCE = "users";
	private int EXPIRATION_TIME_MINS_IN_FUTURE = 30;
	private int NOT_BEFORE_MINS_IN_THE_PAST = 2;
	RsaJsonWebKey rsaJsonWebKey = null;
	
	public String generateJwtToken(LoggedUserDto loggedUser) throws JoseException {
		// Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
	    
		rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);

	    // Give the JWK a Key ID (kid), which is just the polite thing to do
	    rsaJsonWebKey.setKeyId("k1");

	    // Create the Claims, which will be the content of the JWT
	    JwtClaims claims = new JwtClaims();
	    claims.setIssuer(ISSUER);  // who creates the token and signs it
	    claims.setAudience(AUDIENCE); // to whom the token is intended to be sent
	    claims.setExpirationTimeMinutesInTheFuture(EXPIRATION_TIME_MINS_IN_FUTURE); // time when the token will expire (10 minutes from now)
	    claims.setGeneratedJwtId(); // a unique identifier for the token
	    claims.setIssuedAtToNow();  // when the token was issued/created (now)
	    claims.setNotBeforeMinutesInThePast(NOT_BEFORE_MINS_IN_THE_PAST); // time before which the token is not yet valid (2 minutes ago)
	    claims.setSubject(String.valueOf(loggedUser.getId())); // the subject/principal is whom the token is about
	    claims.setClaim("email",loggedUser.getEmail()); // additional claims/attributes about the subject can be added
	    
	    String[] roles = loggedUser.getRoles().stream().map(Role::getValue).toArray(String[]::new);
	    claims.setStringListClaim("roles", Arrays.asList(roles)); // multi-valued claims work too and will end up as a JSON array

	    // A JWT is a JWS and/or a JWE with JSON claims as the payload.
	    // In this example it is a JWS so we create a JsonWebSignature object.
	    JsonWebSignature jws = new JsonWebSignature();

	    // The payload of the JWS is JSON content of the JWT Claims
	    jws.setPayload(claims.toJson());

	    // The JWT is signed using the private key
	    jws.setKey(rsaJsonWebKey.getPrivateKey());

	    // Set the Key ID (kid) header because it's just the polite thing to do.
	    // We only have one key in this example but a using a Key ID helps
	    // facilitate a smooth key rollover process
	    jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

	    // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
	    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

	    // Sign the JWS and produce the compact serialization or the complete JWT/JWS
	    // representation, which is a string consisting of three dot ('.') separated
	    // base64url-encoded parts in the form Header.Payload.Signature
	    // If you wanted to encrypt it, you can simply set this jwt as the payload
	    // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
	    String jwt = "";
		jwt = jws.getCompactSerialization();


	    // Now you can do something with the JWT. Like send it to some other party
	    // over the clouds and through the interwebs.
	    log.info("JWT GENERATED: " + jwt);
	    
	    jwtStorageManager.addIssuedToken(jwt);
	    
	    return jwt;
	}
	
	public LoggedUserDto validateToken(String token) throws UnauthorizedUserException {
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
	        .setRequireExpirationTime() // the JWT must have an expiration time
	        .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
	        .setRequireSubject() // the JWT must have a subject claim
	        .setExpectedIssuer(ISSUER) // whom the JWT needs to have been issued by
	        .setExpectedAudience(AUDIENCE) // to whom the JWT is intended for
	        .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
	        .build(); // create the JwtConsumer instance

		try
		{
		    //  Validate the JWT and process it to the Claims
		    JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
		    log.info("JWT validation succeeded! " + jwtClaims);
		    
		    // Check if token is on the list of issued tokens
		    boolean isValid = jwtStorageManager.checkTokenIsValid(token);
		    if(isValid == false) {
		    	throw new UnauthorizedUserException("Token was not issued by server");
		    }
		    
		    List<String> userRoles = jwtClaims.getStringListClaimValue("roles");
		    Set<Role> rolesSet = new HashSet<Role>();
		    userRoles.stream().forEach(role -> {
			   if(role.equals(Role.ADMIN.getValue()))
				   rolesSet.add(Role.ADMIN);
			   if(role.equals(Role.SUPERUSER.getValue()))
				   rolesSet.add(Role.SUPERUSER);
			   if(role.equals(Role.USER.getValue()))
				   rolesSet.add(Role.USER);
		    });
		    userLogged.updateUser(Integer.parseInt(jwtClaims.getSubject()), jwtClaims.getStringClaimValue("username"), 
		    		jwtClaims.getStringClaimValue("name"), jwtClaims.getStringClaimValue("surname"),
		    		jwtClaims.getStringClaimValue("email"), rolesSet);
		    userLogged.setAccessToken(token);
		    return userLogged;
		}
		catch (InvalidJwtException e)
		{
		    // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
		    // Hopefully with meaningful explanations(s) about what went wrong.
		    log.error("Invalid JWT! " + e);
		    throw new UnauthorizedUserException("Invalid JWT");
		} catch (NumberFormatException e) {
			log.error("Number Format Exc JWT! " + e);
		    throw new UnauthorizedUserException("Invalid JWT");
		} catch (MalformedClaimException e) {
			log.error("MalformedClaim JWT! " + e);
		    throw new UnauthorizedUserException("Invalid JWT");
		}
	}
	
	public void invalidateToken(String token) throws UnauthorizedUserException {
		this.validateToken(token);
		jwtStorageManager.removeIssuedToken(token);
	}
}
