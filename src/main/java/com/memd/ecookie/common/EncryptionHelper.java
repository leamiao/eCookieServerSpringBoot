package com.memd.ecookie.common;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.memd.ecookie.exception.ServiceException;

public class EncryptionHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionHelper.class);
	
	private SecretKeySpec secretKey;
	private Cipher cipher;

	public EncryptionHelper(String secret, int length, String algorithm) {
		try {
			byte[] key = generateKey(secret, length);
			this.secretKey = new SecretKeySpec(key, algorithm);
			this.cipher = Cipher.getInstance(algorithm);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	private byte[] generateKey(String s, int length) throws UnsupportedEncodingException {
		if (s.length() < length) {
			int missingLength = length - s.length();
			for (int i = 0; i < missingLength; i++) {
				s += " ";
			}
		}
		return s.substring(0, length).getBytes(StandardCharsets.UTF_8);
	}

	public String encrypt(String message) {
		if (message == null) {
			return null;
		}
		
		try {
			LOGGER.info("Encrypted message: {}", message);
			this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
			
			byte[] output = this.cipher.doFinal(Base64.getEncoder().encode(message.getBytes()));
			
			return new String(Base64.getEncoder().encode(output));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public String decrypt(String message) {
		if (message == null) {
			return null;
		}
		
		try {
			LOGGER.info("Decrypted message: {}", message);
			this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
			
			byte[] output = this.cipher.doFinal(Base64.getDecoder().decode(message.getBytes()));
			
			return new String(Base64.getDecoder().decode(output));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		EncryptionHelper helper = new EncryptionHelper("FEDEXUPSSHOPIFY0", 16, "AES");
		
		String originalMessage = "Dallas!2345";
		String encrytedMessage = helper.encrypt(originalMessage);
		String descryptedMessage = helper.decrypt(encrytedMessage);
		
		LOGGER.info("final message: {}", descryptedMessage);
	}
}
