package br.net.omegasistemas.padrao.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
/**
 * @author Kiver - 01/07/2017
 * Create class to simple encode payload in JWT, only work with HS256 (yet)
 */
public class SimpleJWT {

	public static String encode(Map<String, Object> payload, String key) {
		try {	
			String header = header();
			String payloadStr = toBase64(toJSON(payload));
			String signature = header + "." + payloadStr;

			return header + "." + payloadStr + "." + sha256(signature, key);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private static String header() {
		HashMap<String, Object> header = new HashMap<String, Object>();
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		return toBase64(toJSON(header));
	} 

	private static String sha256(String value, String secret)
			throws InvalidKeyException, NoSuchAlgorithmException {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		return toBase64(sha256_HMAC.doFinal(value.getBytes()));
	}

	private static String toBase64(String value) {
		return toBase64(value.getBytes());
	}

	private static String toBase64(byte[] value) {
		byte[] hash = Base64.encodeBase64(value);
		String hashStr = new String(hash);
		return base64Escape(hashStr);
	}

	private static String base64Escape(String value) {
		return value.replace("=", "").replace("/", "_").replace("+", "-");
	}
	
	private static String toJSON(Map<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
