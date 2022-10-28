/**
 * Class: CMSC203 
 * Instructor: Prof. Ping Wei Tsai
 * Description: This is a utility class that encrypts and decrypts a phrase using two
 * different approaches. The first approach is called the Caesar Cipher and is a
 * simple substitution cipher where characters in a message are replaced by a
 * substitute character. The second approach, due to Giovan Battista Bellaso,
 * uses a key word, where each character in the word specifies the offset for
 * the corresponding character in the message, with the key word wrapping around
 * as needed.
 * Due: 09/03/2022
 * Platform/compiler: Eclipse Java
 * I pledge that I have completed the programming 
 * assignment independently. I have not copied the code 
 * from a student or any source. I have not given my code 
 * to any student.
 * Name: Nicolas Negahdari
 */


import java.lang.Math;

public class CryptoManager {
	
	private static final char LOWER_RANGE = ' ';
	private static final char UPPER_RANGE = '_';
	private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
	

	/**
	 * This method determines if a string is within the allowable bounds of ASCII codes 
	 * according to the LOWER_RANGE and UPPER_RANGE characters
	 * @param plainText a string to be encrypted, if it is within the allowable bounds
	 * @return true if all characters are within the allowable bounds, false if any character is outside
	 */
	public static boolean isStringInBounds(String plainText) {
		for(char ch : plainText.toCharArray())
			if(ch > UPPER_RANGE || ch < LOWER_RANGE)
				return false;
		return true;
	}
	
	/**
	 * Shifts character ch by the integer amount key which results in another character 
	 * that is within the set of characters whose ASCII codes lie within the allowable range
	 * @param ch a character to be shifted within the set of characters
	 * @param key an integer amount by which to shift ch
	 * @return ch shifted by key, character within the allowable set
	 */
	private static char shiftChInRange(char ch, int key) {
		int keyInRange = key % RANGE; 
		char shiftedChInRange, shiftedCh = (char)(ch + keyInRange);
		if(shiftedCh > UPPER_RANGE) shiftedChInRange = (char)(shiftedCh - RANGE);
		else shiftedChInRange = (char)(shiftedCh);
		return shiftedChInRange;
	}
	
	/**
	 * Encrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in plainText is replaced by the character \"offset\" away from it 
	 * @param plainText an uppercase string to be encrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the encrypted string
	 */
    public static String caesarEncryption(String plainText, int key) {
    	String caesarKeyToBellasoStr = String.valueOf((char)key);
    	return bellasoEncryption(plainText, caesarKeyToBellasoStr);
	}
    
	/**
	 * Encrypts a string according the Bellaso Cipher.  Each character in plainText is offset 
	 * according to the ASCII value of the corresponding character in bellasoStr, which is repeated
	 * to correspond to the length of plainText
	 * @param plainText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the encrypted string
	 */
	public static String bellasoEncryption(String plainText, String bellasoStr) {
		if(!isStringInBounds(plainText)) return "The selected string is not in bounds, Try again.";
		
		int plTextLen = plainText.length(), belStrLen = bellasoStr.length();
		int countRepeat = (int)Math.ceil((double)plTextLen/belStrLen);
		
		String fullBelStr; 
		if(belStrLen < plTextLen) fullBelStr = bellasoStr.repeat(countRepeat).substring(0, plTextLen);
		else fullBelStr = bellasoStr.substring(0, plTextLen);
		
		char[] belEncrChArr = new char[plTextLen];
		for(int i = 0; i < belEncrChArr.length; i++)
			belEncrChArr[i] = shiftChInRange(plainText.charAt(i), fullBelStr.charAt(i));
		String bellasoEncryptedText = new String(belEncrChArr);
		
		return bellasoEncryptedText;
	}
	
	/**
	 * Decrypts a string according to the Caesar Cipher.  The integer key specifies an offset
	 * and each character in encryptedText is replaced by the character \"offset\" characters before it.
	 * This is the inverse of the encryptCaesar method.
	 * @param encryptedText an encrypted string to be decrypted.
	 * @param key an integer that specifies the offset of each character
	 * @return the plain text string
	 */
	public static String caesarDecryption(String encryptedText, int key) {
		return caesarEncryption(encryptedText, -key);
	}
	
	/**
	 * Decrypts a string according the Bellaso Cipher.  Each character in encryptedText is replaced by
	 * the character corresponding to the character in bellasoStr, which is repeated
	 * to correspond to the length of plainText.  This is the inverse of the encryptBellaso method.
	 * @param encryptedText an uppercase string to be encrypted.
	 * @param bellasoStr an uppercase string that specifies the offsets, character by character.
	 * @return the decrypted string
	 */
	public static String bellasoDecryption(String encryptedText, String bellasoStr) {
		int belStrLen = bellasoStr.length();
		char[] reverseBelChArr = new char[belStrLen];
		for(int i = 0; i < belStrLen; i++)
			reverseBelChArr[i] = (char)(RANGE - bellasoStr.charAt(i));
		String reverseBellasoStr = new String(reverseBelChArr);	
		String bellasoDecryptedText = bellasoEncryption(encryptedText, reverseBellasoStr);		
		return bellasoDecryptedText;	
	}
}
