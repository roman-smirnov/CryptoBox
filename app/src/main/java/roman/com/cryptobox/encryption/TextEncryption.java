package roman.com.cryptobox.encryption;

/**
 * Created by avishai on 17/09/2016.
 */
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.Security;

import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.engines.AESFastEngine;
import org.spongycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;

import android.util.Base64;

public class TextEncryption {


    /**
     * The string encoding to use when converting strings to bytes
     */
    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * number of times the password & salt are hashed during key creation.
     */
    private static final int NUMBER_OF_ITERATIONS = 1024;

    /**
     * Key length.
     */
    private static final int KEY_LENGTH = 256;

    /**
     * Initialization vector length.
     */
    private static final int IV_LENGTH = 128;

    /**
     * The length of the salt.
     */
    private static final int SALT_LENGTH = 8;

    /**
     * OpenSSL salted prefix text.
     */
    private static final String OPENSSL_SALTED_TEXT = "Salted__";

    /**
     * OpenSSL salted prefix bytes - also used as magic number for encrypted key file.
     */
    private static final byte[] OPENSSL_SALTED_BYTES = OPENSSL_SALTED_TEXT.getBytes(UTF8);

    private static final int NUMBER_OF_CHARACTERS_TO_MATCH_IN_OPENSSL_MAGIC_TEXT = 10;

    /**
     * Magic text that appears at the beginning of every OpenSSL encrypted file. Used in identifying encrypted key
     * files.
     */
    private static final String OPENSSL_MAGIC_TEXT = new String(Base64.encode(OPENSSL_SALTED_TEXT.getBytes(UTF8), Base64.DEFAULT),
            UTF8).substring(0, NUMBER_OF_CHARACTERS_TO_MATCH_IN_OPENSSL_MAGIC_TEXT);



    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Get password and generate key and iv.
     *
     * @param password
     *            The password to use in key generation
     * @param salt
     *            The salt to use in key generation
     * @return The CipherParameters containing the created key
     */
    private static CipherParameters getAESPasswordKey(final char[] password, final byte[] salt)
    {
        final PBEParametersGenerator generator = new OpenSSLPBEParametersGenerator();
        generator.init(PBEParametersGenerator.PKCS5PasswordToBytes(password), salt, NUMBER_OF_ITERATIONS);

        final ParametersWithIV key = (ParametersWithIV) generator.generateDerivedParameters(KEY_LENGTH, IV_LENGTH);

        return key;
    }

    /**
     * Password based encryption using AES - CBC 256 bits.
     *
     * @param plainText
     *            The text to encrypt
     * @param password
     *            The password to use for encryption
     * @return The encrypted string
     * @throws IOException
     */
    public static String encrypt(final String plainText, final char[] password) throws IOException
    {
        final byte[] plainTextAsBytes = plainText.getBytes(UTF8);

        final byte[] encryptedBytes = encrypt(plainTextAsBytes, password);

        // OpenSSL prefixes the salt bytes + encryptedBytes with Salted___ and then base64 encodes it
        final byte[] encryptedBytesPlusSaltedText = concat(OPENSSL_SALTED_BYTES, encryptedBytes);

        return new String(Base64.encode(encryptedBytesPlusSaltedText, Base64.DEFAULT), UTF8);
    }

    /**
     * Password based encryption using AES - CBC 256 bits.
     *
     * @param plainBytes
     *            The bytes to encrypt
     * @param password
     *            The password to use for encryption
     * @return SALT_LENGTH bytes of salt followed by the encrypted bytes.
     * @throws IOException
     */
    private static byte[] encrypt(final byte[] plainTextAsBytes, final char[] password) throws IOException
    {
        try
        {
            // Generate salt - each encryption call has a different salt.
            final byte[] salt = new byte[SALT_LENGTH];
            secureRandom.nextBytes(salt);

            final ParametersWithIV key = (ParametersWithIV) getAESPasswordKey(password, salt);

            // The following code uses an AES cipher to encrypt the message.
            final BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
            cipher.init(true, key);
            final byte[] encryptedBytes = new byte[cipher.getOutputSize(plainTextAsBytes.length)];
            final int length = cipher.processBytes(plainTextAsBytes, 0, plainTextAsBytes.length, encryptedBytes, 0);

            cipher.doFinal(encryptedBytes, length);

            // The result bytes are the SALT_LENGTH bytes followed by the encrypted bytes.
            return concat(salt, encryptedBytes);
        }
        catch (final InvalidCipherTextException x)
        {
            throw new IOException("Could not encrypt bytes", x);
        }
    }

    /**
     * Decrypt text previously encrypted with this class.
     *
     * @param textToDecode
     *            The code to decrypt
     * @param passwordbThe
     *            password to use for decryption
     * @return The decrypted text
     * @throws IOException
     */
    public static String decrypt(final String textToDecode, final char[] password) throws IOException
    {
        final byte[] decodeTextAsBytes = Base64.decode(textToDecode.getBytes(UTF8), Base64.DEFAULT);

        // Strip off the bytes due to the OPENSSL_SALTED_TEXT prefix text.
        final int saltPrefixTextLength = OPENSSL_SALTED_BYTES.length;

        final byte[] cipherBytes = new byte[decodeTextAsBytes.length - saltPrefixTextLength];
        System.arraycopy(decodeTextAsBytes, saltPrefixTextLength, cipherBytes, 0, decodeTextAsBytes.length - saltPrefixTextLength);

        final byte[] decryptedBytes = decrypt(cipherBytes, password);

        return new String(decryptedBytes, UTF8).trim();
    }

    /**
     * Decrypt bytes previously encrypted with this class.
     *
     * @param bytesToDecode
     *            The bytes to decrypt
     * @param passwordbThe
     *            password to use for decryption
     * @return The decrypted bytes
     * @throws IOException
     */
    private static byte[] decrypt(final byte[] bytesToDecode, final char[] password) throws IOException
    {
        try
        {
            // separate the salt and bytes to decrypt
            final byte[] salt = new byte[SALT_LENGTH];

            System.arraycopy(bytesToDecode, 0, salt, 0, SALT_LENGTH);

            final byte[] cipherBytes = new byte[bytesToDecode.length - SALT_LENGTH];
            System.arraycopy(bytesToDecode, SALT_LENGTH, cipherBytes, 0, bytesToDecode.length - SALT_LENGTH);

            final ParametersWithIV key = (ParametersWithIV) getAESPasswordKey(password, salt);

            // decrypt the message
            final BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
            cipher.init(false, key);

            final byte[] decryptedBytes = new byte[cipher.getOutputSize(cipherBytes.length)];
            final int length = cipher.processBytes(cipherBytes, 0, cipherBytes.length, decryptedBytes, 0);

            cipher.doFinal(decryptedBytes, length);

            return decryptedBytes;
        }
        catch (final InvalidCipherTextException x)
        {
            throw new IOException("Could not decrypt input string", x);
        }
    }

    /**
     * Concatenate two byte arrays.
     */
    private static byte[] concat(final byte[] arrayA, final byte[] arrayB)
    {
        final byte[] result = new byte[arrayA.length + arrayB.length];
        System.arraycopy(arrayA, 0, result, 0, arrayA.length);
        System.arraycopy(arrayB, 0, result, arrayA.length, arrayB.length);

        return result;
    }

    public final static FileFilter OPENSSL_FILE_FILTER = new FileFilter()
    {
        final char[] buf = new char[OPENSSL_MAGIC_TEXT.length()];

        public boolean accept(final File file)
        {
            Reader in = null;
            try
            {
                in = new FileReader(file);
                if (in.read(buf) == -1)
                    return false;
                final String str = new String(buf);
                if (!str.toString().equals(OPENSSL_MAGIC_TEXT))
                    return false;
                return true;
            }
            catch (final IOException x)
            {
                return false;
            }
            finally
            {
                if (in != null)
                {
                    try
                    {
                        in.close();
                    }
                    catch (final IOException x2)
                    {
                    }
                }
            }
        }
    };


    /**
     * Encrypt string with AES.
     *
     * @param textToEncrypt
     *          The text you want to encrypt
     * @param key
     *          The provided secret key. (user password)
     * @return
     *          Encrypted text.
     */
    public String encryptText(String textToEncrypt, String key)
    {
        String encryptedText = "";

        try {
            encryptedText = encrypt(textToEncrypt, key.toCharArray());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return encryptedText;
    }

    /**
     * Decrypt string that was encrypted with AES.
     *
     * @param textToDecrypt
     *          The encrypted text to decrypt.
     * @param key
     *          The secret key that HAS TO MATCH the key that used in the encryption process.
     * @return
     *          Decrypted text.
     */
    public String decryptText(String textToDecrypt, String key)
    {
        String decryptedText = "";

        try {
            decryptedText = decrypt(textToDecrypt, key.toCharArray());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return decryptedText;
    }


}
