package lerrain.tool;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class CipherUtil
{
    public static final String  KEY_ALGORITHM       = "RSA";
    public static final String  SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY          = "RSAPublicKey";
    private static final String PRIVATE_KEY         = "RSAPrivateKey";
    private static final int    MAX_ENCRYPT_BLOCK   = 117;
    private static final int    MAX_DECRYPT_BLOCK   = 128;

    static char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    static SecretKey key;

    static Cipher enc, dec;

    public static void initiate(String pwd) throws Exception
    {
        DESKeySpec desKeySpec = new DESKeySpec(pwd.getBytes());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        key = factory.generateSecret(desKeySpec);

        enc = Cipher.getInstance("DES/ECB/PKCS5Padding");  //算法类型/工作方式/填充方式
        enc.init(Cipher.ENCRYPT_MODE, key);   //指定为加密模式

        dec = Cipher.getInstance("DES/ECB/PKCS5Padding");  //算法类型/工作方式/填充方式
        dec.init(Cipher.DECRYPT_MODE, key);  //相同密钥，指定为解密模式
    }

    public static String decode(String dst)
    {
        if (dst == null)
            return null;

        try
        {
            return new String(dec.doFinal(decodeHex(dst)), "UTF-8");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String encode(Object src)
    {
        if (src == null)
            return null;

        return encode(src.toString());
    }

    public static String encode(String src)
    {
        if (src == null)
            return null;

        try
        {
            return encodeHex(enc.doFinal(src.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decodeHex(String str)
    {
        char[] data = str.toCharArray();
        int len = data.length;
        if ((len & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for(int j = 0; j < len; ++i) {
                int f = Character.digit(data[j], 16) << 4;
                ++j;
                f |= Character.digit(data[j], 16);
                ++j;
                out[i] = (byte)(f & 255);
            }

            return out;
        }
    }

    public static String encodeHex(byte[] data)
    {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for(int var5 = 0; i < l; ++i) {
            out[var5++] = DIGITS_LOWER[(240 & data[i]) >>> 4];
            out[var5++] = DIGITS_LOWER[15 & data[i]];
        }

        return new String(out);
    }

    public static Map<String, Object> genKeyPair() throws Exception
    {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static String sign(byte[] data, String privateKey) throws Exception
    {
        byte[] keyBytes = Common.decodeBase64ToByte(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Common.encodeBase64(signature.sign());
    }

    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception
    {
        byte[] keyBytes = Common.decodeBase64ToByte(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Common.decodeBase64ToByte(sign));
    }

    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception
    {
        byte[] keyBytes = Common.decodeBase64ToByte(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK)
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            else
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception
    {
        byte[] keyBytes = Common.decodeBase64ToByte(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK)
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            else
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception
    {
        byte[] keyBytes = Common.decodeBase64ToByte(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            else
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception
    {
        byte[] keyBytes = Common.decodeBase64ToByte(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            else
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception
    {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Common.encodeBase64(key.getEncoded());
    }

    public static String getPublicKey(Map<String, Object> keyMap) throws Exception
    {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Common.encodeBase64(key.getEncoded());
    }


}
