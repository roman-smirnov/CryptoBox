package roman.com.cryptobox.encryption;

/**
 * Created by avishai on 01/10/2016.
 */
public interface SymmetricAlg {

    public String encryptText(String textToEncrypt, String key);
    public String decryptText(String textToDecrypt, String key);

}
