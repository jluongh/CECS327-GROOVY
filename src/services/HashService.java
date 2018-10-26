package services;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import data.constants.Security;

public class HashService {

	public HashService() {
		//
	}
	
	/**
	 * 
	 * @param plaintext
	 * @return
	 */
	public String sha1(String plaintext) {
		
		try {
			
			MessageDigest digest = MessageDigest.getInstance(Security.HASH_ALGORITHM);
			byte[] plaintextBytes = plaintext.getBytes(Security.ENCODING);
			digest.update(plaintextBytes, 0, plaintextBytes.length);
			byte[] hashBytes = digest.digest();
			
			return hexToDecimal(bytesToHexString(hashBytes));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param bytes
	 * @return
	 */
	private String bytesToHexString(byte[] bytes) {
	    StringBuilder hexString = new StringBuilder();

	    for (int i = 0; i < bytes.length; i++) {
	        String hex = Integer.toHexString(0xFF & bytes[i]);
	        if (hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }

	    return hexString.toString();
	}
	
	/**
	 * 
	 * @param hex
	 * @return
	 */
	private String hexToDecimal(String hex) {
		BigInteger decimal = new BigInteger(hex,16);
		return decimal.toString();
	}
}
