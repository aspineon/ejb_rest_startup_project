package it.restproj.utils.hashpsw;

import it.restproj.utils.hashpsw.exceptions.GeneratingHashErrorException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

public class HashingPasswordUtils {
	
	public static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }

	public static String bytetoString(byte[] input) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(input);
    }

	public static byte[] getHashWithSalt(String input, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.reset();
        digest.update(salt);
        byte[] hashedBytes = digest.digest(stringToByte(input));
        return hashedBytes;
    }
	
	public static byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);

        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }
	
	// genera la password hashata per nuovi utenti
	public static String generateHashedPassword(String password, byte[] salt) throws GeneratingHashErrorException {
		try {
			return bytetoString(getHashWithSalt(password,salt));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new GeneratingHashErrorException();
		}
	}
	
	// valida la password inserita dall'utente con l'hash salvato sul db nel campo password
	public static boolean validatePassword(String password, String salt, String hashToVerify) {
		
		try {
			String calculatedHash = bytetoString(getHashWithSalt(password, stringToByte(salt)));
			if(calculatedHash.equals(hashToVerify))
				return true;
			else
				return false;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
