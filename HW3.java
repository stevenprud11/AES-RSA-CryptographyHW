import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.time.Instant;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;
import java.util.Scanner;
import java.util.Timer;


class WriteFile{
	
	private String path;
	private boolean append_to_file = false;
	private SecretKey skey;
	
	public WriteFile(String path){
		this.path = path;
	}
	
	public void writeToFile(String textLine) throws IOException{
		FileWriter write = new FileWriter(path, append_to_file);
		PrintWriter print_line = new PrintWriter(write);
		print_line.printf("%s" + "%n", textLine);
		print_line.close();
	}
	
	public String readFromFile() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(path));
		return br.readLine();
	}
}


public class HW3 {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		// TODO Auto-generated method stub
		Scanner kb = new Scanner(System.in);
		WriteFile AESTest = new WriteFile("/Users/steven/Desktop/Crytography/HW3/src/AESctext.txt");
		WriteFile RSATest = new WriteFile("/Users/steven/Desktop/Crytography/HW3/src/RSActext.txt");
		
		/*
		 * all the following AES encryption follows the same pattern just with different key length
		 */
		
		//AES Encryption 128 bit key
		System.out.println("Beginning AES Encryption and Decryption Process (128 bit key)... Please input message.");
		String m = kb.nextLine(); //read in message
		AES aes128 = new AES(m, 128); //create AES object with 128 bit key
		Instant starts = Instant.now(); //start encryption timer
		String output = aes128.encrypt(); //encrypt message
		System.out.println("AES 128 bit key encryption time:: " + starts.getNano()); //print time
		try {
			System.out.println("CipherText:: " + new String(output)); //print cipher text
			AESTest.writeToFile(new String(output)); //write file of encrypted message
			starts = Instant.now(); //start decryption timer
			System.out.println("PlainText:: " + aes128.decrypt(AESTest.readFromFile())); //print decrypted message 
			System.out.println("AES 128 bit key decryption time::" + starts.getNano()); //print time
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		
		System.out.println("Beginning AES Encryption and Decryption Process (192 bit key)... Please input message.");
		AES aes192 = new AES(m, 192);
		starts = Instant.now();
		output = aes192.encrypt();
		System.out.println("AES 192 bit key encryption time:: " + starts.getNano());
		try {
			System.out.println("CipherText:: " + new String(output));
			AESTest.writeToFile(new String(output));
			starts = Instant.now();
			System.out.println("PlainText:: " + aes192.decrypt(AESTest.readFromFile()));
			System.out.println("AES 192 bit key decryption time::" + starts.getNano());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		
		System.out.println("Beginning AES Encryption and Decryption Process (256 bit key)... Please input message.");
		AES aes256 = new AES(m, 256);
		starts = Instant.now();
		output = aes256.encrypt();
		System.out.println("AES 256 bit key encryption time:: " + starts.getNano());
		try {
			System.out.println("CipherText:: " + new String(output));
			AESTest.writeToFile(new String(output));
			starts = Instant.now();
			System.out.println("PlainText:: " + aes256.decrypt(AESTest.readFromFile()));
			System.out.println("AES 192 bit key decryption time::" + starts.getNano());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("------------------------------------------------------------------------------------------");
		

		

		/*
		 * all the following RSA encryption and decryption follow the same pattern as one commented below just with different key lengths. 
		 */
		
		
		//RSA Encryption 1024 bit keys
		System.out.println("Beginning RSA Encryption and Decryption Process (1024 bit key)...");
		Bob bob = new Bob(1024); //create bob obj with 1024 bit public and private key
		Alice alice = new Alice(bob, 1024); //create alice with 1024 bit public and private key pass bob to her for her to grab his public key
		m = kb.nextLine();//input string
		starts = Instant.now();//start encryption timer
		String ctext = alice.encrypt(m);//encrypt message
		System.out.println("RSA 1024 bit key encryption time:: " + starts.getNano());//print encryption time
		System.out.println("CipherText:: " + new String(ctext));//print ciphertext
		try {
			RSATest.writeToFile(new String(ctext));//write to file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		starts = Instant.now();//start decryption timer
		System.out.println("PlainText:: " + new String (bob.decrypt(RSATest.readFromFile())));//decrypt cipher text
		System.out.println("RSA 1024 bit key decryption time::" + starts.getNano());//print decryption time

		
		System.out.println();
		
		System.out.println("Beginning RSA Encryption and Decryption Process (2048 bit key)...");
		bob = new Bob(2048);
		alice = new Alice(bob, 2048);
		starts = Instant.now();
		ctext = alice.encrypt(m);
		System.out.println("RSA 2048 bit key encryption time:: " + starts.getNano());
		System.out.println("CipherText:: " + new String(ctext));
		try {
			RSATest.writeToFile(new String(ctext));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		starts = Instant.now();
		System.out.println("PlainText:: " + new String (bob.decrypt(RSATest.readFromFile())));
		System.out.println("RSA 2048 bit key decryption time::" + starts.getNano());

		
		System.out.println();
		
		System.out.println("Beginning RSA Encryption and Decryption Process (4096 bit key)...");
		bob = new Bob(4096);
		alice = new Alice(bob, 4096);
		starts = Instant.now();
		ctext = alice.encrypt(m);
		System.out.println("RSA 2048 bit key encryption time:: " + starts.getNano());
		System.out.println("CipherText:: " + new String(ctext));
		try {
			RSATest.writeToFile(new String(ctext));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		starts = Instant.now();
		System.out.println("PlainText:: " + new String (bob.decrypt(RSATest.readFromFile())));
		System.out.println("RSA 4096 bit key decryption time::" + starts.getNano());

	}

}
