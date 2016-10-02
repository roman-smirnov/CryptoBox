package roman.com.cryptobox.encryption.has;

/**
 * Created by avishai on 01/10/2016.
 */

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.digests.MD5Digest;


public final class DigestFactory {

    public static Digest getDigest(HashAlgorithmTypes shaType){


        if (shaType.equals(HashAlgorithmTypes.SHA_1)) {
            return new SHA1Digest();
        }
        if (shaType.equals(HashAlgorithmTypes.MD_5)) {
            return new MD5Digest();
        }
        if (shaType.equals(HashAlgorithmTypes.SHA_256)) {
            return new SHA256Digest();
        }
        if (shaType.equals(HashAlgorithmTypes.SHA_384)) {
            return new SHA384Digest();
        }
        if (shaType.equals(HashAlgorithmTypes.SHA_512)) {
            return new SHA512Digest();
        }
        return null;
    }
}
