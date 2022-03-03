package com.example.demo.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;

public class PBEUtil {

    private static final String ALGORITHM = "PBEWITHMD5andDES";

    private static final int ITERATION_COUNT = 100;

    private static final byte[] INIT_SALT = {-11,3,5,'r','t','t','g','x'};// 盐

    private static final String PASSWORD = "qwertyuiop";// 秘钥



    /***
     * 转换密钥
     * @param password 密码
     * @return 密钥
     * @throws Exception
     */
    private static Key toKey(String password) throws Exception{
        //密钥材料
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        //实例化
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        //生成密钥
        return factory.generateSecret(keySpec);
    }

    /***
     * 加密
     * @param data 待加密数据
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data) throws Exception{
        //转换密钥
        Key key = toKey(PASSWORD);
        //实例化PBE参数材料
        PBEParameterSpec spec = new PBEParameterSpec(INIT_SALT, ITERATION_COUNT);
        //实例化
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        return cipher.doFinal(data);
    }


    /***
     * 解密
     * @param data 待解密数据
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data) throws Exception{
        //转换密钥
        Key key = toKey(PASSWORD);
        //实例化PBE参数材料
        PBEParameterSpec spec = new PBEParameterSpec(INIT_SALT, ITERATION_COUNT);
        //实例化
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        //执行操作
        return cipher.doFinal(data);
    }


    private static String showByteArray(byte[] data) {
        if(null == data) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(byte b : data) {
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("");
        return sb.toString();
    }


    public static void main(String[] args) throws Exception{

        String password = "1111";
        System.out.println("口令："+password);
        String data = "PBE数据";
        System.out.println("加密前数据：String:"+data);
        System.out.println("加密前数据：byte[]:"+showByteArray(data.getBytes()));

        byte[] encryptData = encrypt(data.getBytes());
        System.out.println("加密后数据：byte[]:"+showByteArray(encryptData));

        byte[] decryptData = decrypt(encryptData);
        System.out.println("解密后数据: string:"+new String(decryptData));
    }

}
