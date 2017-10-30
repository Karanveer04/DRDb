package user;
/**
 * This class is used to encrypt user passwords into Hashes before storing to database.
 * @author Karanveer Singh
 */

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

public class HashFunctions {
	// Available algorithms are : MD2, MD5 , SHA-1 , SHA-224 , SHA-256 , SHA-384, SHA-512

	public static String getHash(char[] inputBytes, String algorithm) {
		String hashValue = "";

		try {
			// create a msgdigest obj
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			// update msgdigest obj with bytes it needs to convert
			messageDigest.update((new String(inputBytes)).getBytes("UTF8"));
			// create another byte array which will give digestedbytes
			byte[] digestedBytes = messageDigest.digest();
			// create string out of digestedbytes
			hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hashValue;
	}
}
