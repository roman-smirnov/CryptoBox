package cryptobox.encryption;

import java.security.Security;

import cryptobox.encryption.aes.AES;

/**
 * Created by avishai on 01/10/2016.
 */
public final class CryptoManager {

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    private CryptoManager() {
    }

    public static class Symmetric {

        public static AES AES = new AES();
        //TBD more Symmetric algs
    }

    //TBD
    /*public static class Asymmetric{
    }*/
}
