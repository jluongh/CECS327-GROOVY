package services;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import data.constants.Security;

public class HashService {

	private int truncatedLength;
	
	/**
	 * 
	 */
	public HashService() {
		this.truncatedLength = 0;
	}
	
	/**
	 * 
	 * @param setDefaultTruncation
	 */
	public HashService(boolean setDefaultTruncation) {
		if (setDefaultTruncation)
			this.truncatedLength = Security.DEFAULT_TRUNCATION;
		else
			this.truncatedLength = 0;
	}
	
	/**
	 * 
	 * @param truncatedLength
	 */
	public HashService(int truncatedLength) {
		if (truncatedLength > 0)
			this.truncatedLength = truncatedLength;
		else this.truncatedLength = 0;
			
	}
	
	/**
	 * 
	 * @param plaintext
	 * @return
	 */
	public String sha1(String plaintext) {
		
		String decimalString = null;
		
		try {
			
			MessageDigest digest = MessageDigest.getInstance(Security.HASH_ALGORITHM);
			byte[] plaintextBytes = plaintext.getBytes(Security.ENCODING);
			digest.update(plaintextBytes, 0, plaintextBytes.length);
			byte[] hashBytes = digest.digest();
			
			decimalString = hexToDecimal(bytesToHexString(hashBytes));
			
			return truncate(decimalString);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return decimalString;
	}
	
	/**
	 * 
	 * @param plaintext
	 * @return
	 */
	public String sha1(byte[] plaintextBytes) {
		
		String decimalString = null;
		
		try {
			
			MessageDigest digest = MessageDigest.getInstance(Security.HASH_ALGORITHM);
			digest.update(plaintextBytes, 0, plaintextBytes.length);
			byte[] hashBytes = digest.digest();
			
			decimalString = hexToDecimal(bytesToHexString(hashBytes));
			
			return truncate(decimalString);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return decimalString;
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
	
	/**
	 * 
	 * @param hash
	 * @return
	 */
	private String truncate(String hash) {
		if (truncatedLength != 0)
			return hash.substring(0, truncatedLength);
		else
			return hash;
	}
}
