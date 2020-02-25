import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Bob {
	private PrivateKey privateKey;
	public PublicKey publicKey;
	
	public Bob(int keysize) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		generateKeys(keysize); //generate public and private key of size keysize
	}

	
	public void generateKeys(int keysize){//public and private key creation for RSA
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			keygen.initialize(keysize);
			KeyPair pair = keygen.generateKeyPair();
			this.privateKey = pair.getPrivate();
			this.publicKey = pair.getPublic();
			
			//System.out.println(this.privateKey + " " + this.publicKey);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String decrypt(String ctext) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
		try {//decrypt message using bob private key
			Cipher ci = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
			ci.init(Cipher.DECRYPT_MODE, privateKey);
			return new String(ci.doFinal(Base64.getDecoder().decode(ctext.getBytes())));//return plaintext
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error in decryption";//if error
	}
	
	public PublicKey getPublicKey(){//allows people sending bob a message to grab his public key
		return publicKey;
	}
}
