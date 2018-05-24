package br.com.primum.certificado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import br.com.primum.bo.criptografia.Criptografar;
//import br.com.primum.bo.crud.LogPrimum;

public class CertificadoBO {

	private static final int RSAKEYSIZE = 1024;  

	public static PublicKey carregaChavePublica () throws IOException, ClassNotFoundException {

		File fPub = new File ("D:\\jboss-4.2.3.GA\\server\\default\\deploy\\Quality.war\\WEB-INF\\certificado\\chave.publica");
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream(fPub));  
        PublicKey ret = (PublicKey) ois.readObject();  
        ois.close();  
        return ret;  
    }  

	public static PrivateKey carregaChavePrivada (File fPvk) throws IOException, ClassNotFoundException {  
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fPvk));  
        PrivateKey ret = (PrivateKey) ois.readObject();  
        ois.close();  
        return ret;  
    }

    public void geraParChaves(File fPub, File fPvk)   
        throws IOException, NoSuchAlgorithmException,   
        InvalidAlgorithmParameterException,   
        CertificateException, KeyStoreException {  

        KeyPairGenerator kpg = KeyPairGenerator.getInstance ("RSA");  
        kpg.initialize (new RSAKeyGenParameterSpec(RSAKEYSIZE, RSAKeyGenParameterSpec.F4));  
        KeyPair kpr = kpg.generateKeyPair ();  
        PrivateKey priv = kpr.getPrivate();          
        PublicKey pub = kpr.getPublic();  

        //-- Gravando a chave pública em formato serializado  
        ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (fPub));  
        oos.writeObject (pub);  
        oos.close();  

        //-- Gravando a chave privada em formato serializado  
        //-- Não é a melhor forma (deveria ser guardada em um keystore, e protegida por senha),   
        //-- mas isto é só um exemplo  
        oos = new ObjectOutputStream (new FileOutputStream(fPvk));  
        oos.writeObject (priv);  
        oos.close();
    }
    
    @SuppressWarnings("unused")
	private void teste() throws ClassNotFoundException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
    	File fpk = new File ("D:\\jboss-4.2.3.GA\\server\\default\\deploy\\Quality.war\\WEB-INF\\certificado\\chave.privada");
		//this.getDao().geraParChaves(fpp, fpk);
		PrivateKey k = CertificadoBO.carregaChavePrivada(fpk);
		PublicKey p = CertificadoBO.carregaChavePublica();

		byte[][] oTeste = Criptografar.cifra("texto".getBytes());

		//LogPrimum.mensagem(new String(oTeste[0]));
		String texto = new String(oTeste[0]);

		byte[] txtDec = Criptografar.decifra(k, oTeste[0], oTeste[1]);
		//LogPrimum.mensagem(new String(txtDec));
    }
}