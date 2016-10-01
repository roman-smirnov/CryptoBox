package roman.com.cryptobox.encryption.Hash;

/**
 * Created by avishai on 17/09/2016.
 */

import org.spongycastle.crypto.Digest;

import java.security.Security;

public final class HashManager {

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    private HashManager(){

    }

    //Default parameter is sha 256
    public static String stringToHash(String strToHash){
        return stringToHash(strToHash, HashAlgorithmTypes.SHA_256);
    }

    public static String stringToHash(String strToHash, HashAlgorithmTypes hashType){

        Digest hashAlgDigest = DigestFactory.getDigest(hashType);

        byte [] output = new byte[hashAlgDigest.getDigestSize()];
        byte [] strByteArray = strToHash.getBytes();

        hashAlgDigest.update(strByteArray, 0, strByteArray.length);
        hashAlgDigest.doFinal(output, 0);

        String hexaBaseString = String.format("%064x", new java.math.BigInteger(1, output));
        return hexaBaseString;
    }

    public static Boolean compareHashToString(String hash1, String textBeforeHash, HashAlgorithmTypes hashType){
        return stringToHash(textBeforeHash, hashType).equals(hash1);
    }

}
