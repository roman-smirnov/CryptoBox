package roman.com.cryptobox.filehandling;

import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.RSAKeyPairGenerator;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

/**
 * This class is responsible for handling file transactions
 * Created by roman on 9/17/16.
 */
public class FileManager {
    private fileNameDispenser mFileNameDispenser;
    private FileWriter mFileWriter;

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    /**
     * Main and only constructor
     */
    public FileManager() {
        mFileNameDispenser = new fileNameDispenser();
        mFileWriter = new FileWriter();
    }

    /**
     * writes given filename and content to an encrypted file
     * @param fileName
     * @param fileContent
     */
    public void writeEncryptedFile(String fileName, String fileContent) {
        try {
            String encryptedFilePath = mFileNameDispenser.getEncryptedFilePath(fileName);
            mFileWriter.writeEncryptedFile(encryptedFilePath,fileContent);
        } catch (IllegalAccessException e) {
            LogUtils.d("ERROR GETTING EXTERNAL FILE STORAGE WRITEABLE PATH");
            e.printStackTrace();
        }
    }

    /**
     * writes given filename and content to an unencrypted file
     * @param fileName
     * @param fileContent
     */
    public void writeUnencryptedFile(String fileName, String fileContent) {
        try {
            String unencryptedFilePath = mFileNameDispenser.getUnencryptedFilePath(fileName);
            mFileWriter.writeUnencryptedFile(unencryptedFilePath,fileContent);
        } catch (IllegalAccessException e) {
            LogUtils.d("ERROR GETTING EXTERNAL FILE STORAGE WRITEABLE PATH");
            e.printStackTrace();
        }
    }

    public void getRsaKeyPair(){
        try {
            ECGenParameterSpec ecParamSpec = new ECGenParameterSpec("secp224k1");
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDH","SC");
            kpg.initialize(ecParamSpec);

            KeyPair kpair=kpg.generateKeyPair();
            PublicKey pkey=kpair.getPublic();
            LogUtils.d(pkey.toString());
            PrivateKey skey=kpair.getPrivate();
            LogUtils.d(skey.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

