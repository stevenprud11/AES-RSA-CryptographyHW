# AES-RSA-CryptographyHW
HW assignment in Cryptography class where I implement AES and RSA encryptions.

This homework assignment was to implement a program where Alice and Bob could send encrypted messages. Part 1 of the assignment was to implement AES encryption/decryption with a 128, 196, and 256 bit key.
I utilized the Cipher Java Library to utilize this. I ran into an issue where I had to download 2 other .jar files to allow 
the AES key size to be larger than 128 bits. (This part is in the AES java file)

Second part of this homework assignment was to implement another encryption/decryption process using RSA with 1024, 2048, 
and 4096 bit keys. (Found in the Alice and Bob java files)

Third part of this homework assignment was to measure the time it took to encrypt and decrypt each encryption algorithm 
for the different key lengths. This is way in the Main java file there are so many calls.
