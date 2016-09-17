package roman.com.cryptobox.encryption;

/**
 * Created by avishai on 17/09/2016.
 */

import org.spongycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;

public class HashManager {

    private static final int KEY_SIZE_1024 = 1024;
    private static final int KEY_SIZE_2048 = 2048;

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    public HashManager(){

    }

    public String hashStr(String str){




        return "";
    }

    public String hashStr(String str, String hashType){




        return "";
    }




}
