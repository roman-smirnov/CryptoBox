package cryptobox.dataobjects;

import cryptobox.encryption.CryptoManager;

/**
 * Created by avishai on 05/11/2016.
 */

public class ContentValueCryptWrapper extends ContentValueWrapper {

    private String keyToEncryptWith;

    public ContentValueCryptWrapper(String EncryptionKey) {
        super();

        keyToEncryptWith = EncryptionKey;
    }

    //TODO
    //functionality for non strings type is not implementd yet.
    //public void addEncryptedLongValue(String key, Long value) {

    //String encryptValue = CryptoManager.Symmetric.AES.encryptText(Long.toString(value), keyToEncryptWith);
    //super.(key, encryptValue);
    //valuesLongs.put(key, value);
    //}

    public void addEncryptedStringValue(String key, String value) {
        String encryptValue = CryptoManager.Symmetric.AES.encryptText(value, keyToEncryptWith);
        super.addStringValue(key, encryptValue);
    }
}
