import java.security.*;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Alice {
	private PrivateKey privateKey;
	public PublicKey publicKey;
	private Bob bob;
	
	public Alice(Bob bob, int keysize){ //save bob obj and create key size
		this.bob = bob;
		generateKeys(keysize);
	}

	
	public void generateKeys(int keysize){//create public and private key for RSA of size keysize
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
	
	
	public String encrypt(String text) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		PublicKey bob_PB = bob.getPublicKey();//obtain bob public key
		try {
			Cipher ci = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding"); //RSA encryptino
			ci.init(Cipher.ENCRYPT_MODE, bob_PB);
			return Base64.getEncoder().encodeToString(ci.doFinal(text.getBytes())); //return cipher text
			//Base64.getEncoder().encodeToString(ci.doFinal(input));
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error in encryption"; //if error
	}
	
	public PublicKey getPublicKey(){ //so bob can get public key if need be
		return publicKey;
	}
	
	
}
