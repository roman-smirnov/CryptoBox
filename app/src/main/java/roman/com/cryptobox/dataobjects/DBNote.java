package roman.com.cryptobox.dataobjects;

import roman.com.cryptobox.encryption.CryptoManager;

/**
 * Created by avishai on 08/10/2016.
 */

public class DBNote {

    private String title;
    private String lastModified;
    private String content;

    private String key;

    public DBNote(String title, String lastModified, String content, String key){
        this.title = title;
        this.lastModified = lastModified;
        this.content = content;

        this.key = key;
    }

    public String getEncryptedTitle()
    {
        return CryptoManager.Symmetric.AES.encryptText(title, key);
    }

    public String getEncryptedLastModified(){
        return CryptoManager.Symmetric.AES.encryptText(lastModified, key);
    }

    public String getEncryptedContent(){
        return CryptoManager.Symmetric.AES.encryptText(content, key);
    }
}
