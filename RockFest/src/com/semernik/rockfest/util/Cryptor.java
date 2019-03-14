package com.semernik.rockfest.util;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class Cryptor.
 */
public class Cryptor {

	/** The instance. */
	private static Cryptor instance = new Cryptor();

	/** The char bound. */
	private final int CHAR_BOUND = 32768;

	/**
	 * Gets the single instance of Cryptor.
	 *
	 * @return single instance of Cryptor
	 */
	public static Cryptor getInstance(){
		return instance;
	}

	/**
	 * Find key.
	 *
	 * @param number the number
	 * @param string the string
	 * @return the string
	 */
	public String findKey(long number, String string){
		char[] chars = string.toCharArray();
		for (int i = 0;i<chars.length;i++){
			char ch = chars[i];
			int j = (int) ch;
			number = (number>>31) + j;
			ch = (char)(j * number);
			chars[i] = ch;
		}
		return new String(chars);
	}

	/**
	 * Encrypt.
	 *
	 * @param dataToEncrypt the data to encrypt
	 * @param key the key
	 * @return the string
	 */
	public String encrypt (String dataToEncrypt, String key){
		char [] dataChars = dataToEncrypt.toCharArray();
		int dataLength = dataChars.length;
		int keyHash = Math.abs(key.hashCode() % CHAR_BOUND);
		int keyLength = key.length();
		char encryptedChar;
		char randomChar;
		Random rand = new Random();
		char [] encryptedChars = new char[dataToEncrypt.length()*2];
		for (int i = 0, j = 0; i < encryptedChars.length-1; i +=2, j++){
			keyHash = (keyHash * keyLength) % CHAR_BOUND;
			randomChar = (char) rand.nextInt(CHAR_BOUND);
			encryptedChar = (char) (dataChars[j] ^ (keyHash));
			if ((keyHash + dataLength) % 2 == 0){
				encryptedChars[i] = encryptedChar;
				encryptedChars[i+1] = randomChar;
			} else {
				encryptedChars[i] = randomChar;
				encryptedChars[i+1] = encryptedChar;
			}
		}
		return new String(encryptedChars);
	}

	/**
	 * Decrypt.
	 *
	 * @param encryptedData the encrypted data
	 * @param key the key
	 * @return the string
	 */
	public String decrypt (String encryptedData, String key){
		char[] encryptedChars = encryptedData.toCharArray();
		int decryptedLength = encryptedChars.length / 2;
		char [] decryptedChars = new char [decryptedLength];
		int keyHash = Math.abs(key.hashCode() % CHAR_BOUND);
		int keyLength = key.length();
		char encryptedChar;
		for (int i = 0, j = 0; i < encryptedChars.length-1; i+=2, j++){
			keyHash = (keyHash * keyLength) % CHAR_BOUND;
			if ((keyHash + decryptedLength) % 2 == 0){
				encryptedChar = encryptedChars[i];
			} else {
				encryptedChar = encryptedChars[i+1];
			}
			decryptedChars[j] = (char) (encryptedChar ^ keyHash);
		}
		return new String(decryptedChars);
	}

}
