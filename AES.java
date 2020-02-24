import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

class AES{
	String ptext;
	String ctext;
	SecretKey skey;
	byte[] iv;
	IvParameterSpec ivspec;
	byte[] output;
	
	public AES(String text, int keysize){ //plaint text and keysize input
		ptext = text;
		
		try {
			createKey(keysize);
			createIV();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createIV(){ //create IV for CBC
		SecureRandom srandom = new SecureRandom();
		iv = new byte[16];//set to 16 bytes
		srandom.nextBytes(iv);
		ivspec = new IvParameterSpec(iv);
	}

	public void createKey(int keysize) throws NoSuchAlgorithmException{ //create AES key of keysize bits
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		keygen.init(keysize);
		skey = keygen.generateKey();
	}
	
	
	public byte[] encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException{
		Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding"); //using Cipher encryption library, encrypt using AES/CBC/PKCS5Padding
		ci.init(Cipher.ENCRYPT_MODE, skey, ivspec);
		byte[] input = ptext.getBytes("UTF-8");
		output = ci.doFinal(input);
		return output;//return ciphertext
	}
	
	public String decrypt(byte[] ciphertext) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
		Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding"); //using Cipher decryption library, decrypt using AES/CBC/PKCS5Padding
		ci.init(Cipher.DECRYPT_MODE,  skey, ivspec);
		return new String(ci.doFinal(ciphertext)); //return plain text
	} 
	
	public SecretKey getKey(){ //allows bob to get shared secret key
		return skey;
	}
}


