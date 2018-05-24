package br.com.primum.bo.criptografia;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import br.com.primum.certificado.CertificadoBO;

public class Criptografar {

	public static String criptografarTexto(String t) {

		byte[][] cadeiaCifrada = null;
		try {
			cadeiaCifrada = Criptografar.cifra(t.getBytes());
		} catch (ClassNotFoundException | IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			

		/**
		 * cadeiaCifrada[0] = SENHA COM CRIPTOGRAFIA
		 * cadeiaCifrada[1] = CHAVE COM CRIPTOGRAFIA
		 */
		return new String(cadeiaCifrada[0]);
	}

    public static byte[][] cifra (byte[] textoClaro) throws NoSuchAlgorithmException,   
    NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,  
    BadPaddingException, InvalidAlgorithmParameterException, ClassNotFoundException, IOException {  
        byte[] textoCifrado = null;  
        byte[] chaveCifrada = null;  
        
        PublicKey p = CertificadoBO.carregaChavePublica();
        
        //-- A) Gerando uma chave simétrica de 128 bits  
        KeyGenerator kg = KeyGenerator.getInstance ("AES");  
        kg.init (128);  
        SecretKey sk = kg.generateKey ();  
        byte[] chave = sk.getEncoded();  
        //-- B) Cifrando o texto com a chave simétrica gerada  
        Cipher aescf = Cipher.getInstance ("AES/CBC/PKCS5Padding");  
        IvParameterSpec ivspec = new IvParameterSpec (new byte[16]);  
        aescf.init (Cipher.ENCRYPT_MODE, new SecretKeySpec(chave, "AES"), ivspec);  
        textoCifrado = aescf.doFinal (textoClaro);  
        //-- C) Cifrando a chave com a chave pública  
        Cipher rsacf = Cipher.getInstance ("RSA");  
        rsacf.init (Cipher.ENCRYPT_MODE, p);  
        chaveCifrada = rsacf.doFinal (chave);  

        return new byte[][] { textoCifrado, chaveCifrada };  
    } 

    public static byte[] decifra (PrivateKey pvk, byte[] textoCifrado, byte[] chaveCifrada) throws NoSuchAlgorithmException,   
    NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,  
    BadPaddingException, InvalidAlgorithmParameterException  {  
        byte[] textoDecifrado = null;  

        //-- A) Decifrando a chave simétrica com a chave privada  
        Cipher rsacf = Cipher.getInstance ("RSA");  
        rsacf.init (Cipher.DECRYPT_MODE, pvk);  
        byte[] chaveDecifrada = rsacf.doFinal (chaveCifrada);  
        //-- B) Decifrando o texto com a chave simétrica decifrada  
        Cipher aescf = Cipher.getInstance ("AES/CBC/PKCS5Padding");  
        IvParameterSpec ivspec = new IvParameterSpec (new byte[16]);  
        aescf.init (Cipher.DECRYPT_MODE, new SecretKeySpec (chaveDecifrada, "AES"), ivspec);  
        textoDecifrado = aescf.doFinal (textoCifrado);  

        return textoDecifrado;  
    }

    public static String cifraMD5(String t) {

    	String md5 = null;
    	try {  

            //Create MessageDigest object for MD5  
            MessageDigest digest = MessageDigest.getInstance("MD5");  
            //Update input string in message digest  
            digest.update(t.getBytes(), 0, t.length());  
            //Converts message digest value in base 16 (hex)   
            md5 = new BigInteger(1, digest.digest()).toString(16);  
        } catch (NoSuchAlgorithmException e) {  

            e.printStackTrace();  
        }

    	return md5;
    }

    public static byte[] serializarObjeto(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object deserializarObjeto(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}