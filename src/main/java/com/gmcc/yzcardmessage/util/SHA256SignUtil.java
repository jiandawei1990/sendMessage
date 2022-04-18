package com.gmcc.yzcardmessage.util;

import com.alibaba.fastjson.JSON;
import org.apache.tomcat.util.codec.binary.Base64;
import org.assertj.core.util.Maps;


import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class SHA256SignUtil {

    private static final String ENCODING = "UTF-8";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String KEY_ALGORITHM = "RSA";
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArkLfPscB7FjmSgfzJ3wlMjE3zRPgGrfa7tgJnN6+oybUsHCLOdJC4qPLwlBwRC1mwGvLuMY58w+rGQ50Sym8l6XqaI9FidlqpB15aZ9qVmbccxv85n+rZn5mrNAl8st/tFG1l3Zwwcm+U2up7g+avjJYcuFNyQiMNl3JU+CslkhkBhC2j0UR0DxseSMhyCWA9VAls+ccSKmtP6YW6tY4jAWgQSwq3gOiPlkIcZOaX2JNYivhUXb8vaRFublqNC88jDJT4Ca911B6WgIuz3rv5VZ/VuPr+o3G2qaAi2bSmVMW0tyO89i2VnGfJgLm3i8ld3Wt3FtvVOCdrAhVwe6PiwIDAQAB";
    public static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCuQt8+xwHsWOZKB/MnfCUyMTfNE+Aat9ru2Amc3r6jJtSwcIs50kLio8vCUHBELWbAa8u4xjnzD6sZDnRLKbyXpepoj0WJ2WqkHXlpn2pWZtxzG/zmf6tmfmas0CXyy3+0UbWXdnDByb5Ta6nuD5q+Mlhy4U3JCIw2XclT4KyWSGQGELaPRRHQPGx5IyHIJYD1UCWz5xxIqa0/phbq1jiMBaBBLCreA6I+WQhxk5pfYk1iK+FRdvy9pEW5uWo0LzyMMlPgJr3XUHpaAi7Peu/lVn9W4+v6jcbapoCLZtKZUxbS3I7z2LZWcZ8mAubeLyV3da3cW29U4J2sCFXB7o+LAgMBAAECggEAKXNksQUbcwHOfYuYjircmizU7iYtJ43WOPwQhU0CocJp0zJiG7XlDQ4TOLxzh/QYveqmQeR9AlEWzNYAWaSq5JOlx4tU6i5aTIFqUPKHk9fR1luw848o0S998ttsO22midjpzgZxWddIO4WfnS3/DADjwyfOneQ3nfL/IcGOrAxVcKsDeTGgZOAwl7HaNri53kCJijKD6TsbEQcrrsvrryAG7FC1nTMoxXQaxhlbOS76rMtmtEXt9cqI6NMIrwxPX+M2cECQK/bo7MB1drrrq3F5N+VKv2cqOBJDna0Z8+lgJrRwejCtWvxUupml3UuJEFWyPBKuow70C9050UanAQKBgQDh8WcwNgKu4P66KWpejiFzcFiXWwewcuz55Xs5ucZF84Gzfnw1oZ+zuT7Q2xuBPtVrCV64Nt7JYc0QdVOG7pJPfh7pMFP8dhuT6EC5vx4wuYkDolg0HIMzMmJMbQZtGcpD9eNVGmI87yzKROCV23hynS8mtAw2Lq/K4WOOMDjPgQKBgQDFcWv0/t80kUQC4PtjMt+R+nEyLP4g1y6UxcadDGpPIc5+4FnooBuDC52e9brlml069nuNYYbF8dRfPElazhyER8n5EqqQbxkh9nAVYx89jC4ZM3Y2I81+Z09kWb8CPpB1dFcff53Lqnjghg1nYMmtKAlt679B/Qckn+SmOYAlCwKBgHz0GHEdLZaxb65cwSrRx8Yj15q9CR+YL150fxWJnBGLfataxC6U19F5HTg8NJXhvf2YDP4rukH9CH+Voz/4KIo14bpss8XnO5Es3L7c50FjGlccLVOEgTqLHrnNgcRMgwPatIKYOMQiMc8bEGlQGuin7S+mWyqL30SAKaK1IHYBAoGAZZdTng7aWFj6bqTGlKcjhCAhyFiANfOiYyhUZpBxzt+tOXlEqb5gCZCbfKQhk2xW1jTnI7rvtsAUrx/8+n0dDS9HDCJ/oquXefMsfsSu2WNcgNkK1Rac8JJFtnO7JXN9rwG6pllqntXLNnOjR/L0MmVsXKO1mEWSPFUNOD5T4jsCgYEA1EgBbtXycljQb823xAeMHc0cGIGqVyWDpJaYs1IIhFUbqa3dYa6nGCUYs2tRpiEnQBcnOM79f55b3ePMS3L1PquuU6b+twabaypDCO6V6P4X7HazOODchPFtjzvC4NHCb2AQ5GlcMA12m0i9/O1bi1doarthGvRPnRnXBI6ZaDQ=";
    public static final int KEY_SIZE = 2048;



    public static void main(String[] args) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException,
            SignatureException, UnsupportedEncodingException {

        PublicKey publicKey = restorePublicKey(decodeBase64(PUBLIC_KEY));
        // 待签名内容
        Map<String, Object> map = new HashMap<String,Object>();
//        map.put("transactTime", "20201225111022");

//        map.put("branchCompany", "扬州");
//        map.put("lineCode", "123");
//        map.put("busCode", "123");
//        map.put("driverCardNo", "123");
//        map.put("cardNo", "123");
//        map.put("startStationName", "123");
//        map.put("endStationName", "123");
//        map.put("direction", "123");

        map.put("uploadTime", "20210730133347");
        map.put("branchCompany", "01");
        map.put("lineCode", "0008");
        map.put("busCode", "030688");
        map.put("transactTime", "2021-06-28 11:47:48");
        map.put("driverCardNo", "12244748");
        map.put("consumeType", "busCard");
        map.put("cardNo", "12228700");

        //        map.put("xinpianka","abc-123");
       //    map.put("consumeType", "123");
        // 签名
        byte[] sign256 = sign256(JSON.toJSONString(map));
        String sign = encodeBase64(sign256);
        System.out.println("sign=" + sign);

        // 验签
        boolean result = verify256(JSON.toJSONString(map), decodeBase64(sign), publicKey);

//        String str = "62261012121121099FGHJKKLOP";
//        String t = str.replaceAll("FGHJKKLOP+$", "");
//        System.out.println(t);

        System.out.println(result);

    }

    /**
     * SHA256WithRSA 签名
     */
    public static byte[] sign256(String data) throws NoSuchAlgorithmException, InvalidKeySpecException,
            InvalidKeyException, SignatureException, UnsupportedEncodingException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        PrivateKey privateKey = restorePrivateKey(decodeBase64(PRIVATE_KEY));
        signature.initSign(privateKey);
        signature.update(data.getBytes(ENCODING));
        return signature.sign();
    }



    /**
     * SHA256WithRSA 验证签名
     */
    public static boolean verify256(String data, byte[] sign, PublicKey publicKey) {
        if (data == null || sign == null || publicKey == null) {
            return false;
        }
        try {
            Signature signetcheck = Signature.getInstance(SIGNATURE_ALGORITHM);
            signetcheck.initVerify(publicKey);
            signetcheck.update(data.getBytes("UTF-8"));
            return signetcheck.verify(sign);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 二进制数据编码为BASE64字符串
     */
    public static String encodeBase64(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     * BASE64字符串编码为二进制数据
     */
    public static byte[] decodeBase64(String base64String) {
        try {
            return Base64.decodeBase64(base64String.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成密钥对
     */
    public static Map<String, byte[]> generateKeyBytes() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原公钥
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory
                    .generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 16进制转10进制
     * @param content
     * @return
     */
    public static int covert(String content){
        int number=0;
        String [] HighLetter = {"A","B","C","D","E","F"};
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0;i <= 9;i++){
            map.put(i+"",i);
        }
        for(int j= 10;j<HighLetter.length+10;j++){
            map.put(HighLetter[j-10],j);
        }
        String[]str = new String[content.length()];
        for(int i = 0; i < str.length; i++){
            str[i] = content.substring(i,i+1);
        }
        for(int i = 0; i < str.length; i++){
            number += map.get(str[i])*Math.pow(16,str.length-1-i);
        }
        return number;
    }

    public static int covertTmp(String content1){
        int number = 0;
        String [] HighLetter = {"A","B","C","D","E","F"};
        Map<String,Integer>  map = new HashMap<>();
        for(int i = 0; i <= 9;i++){
            map.put(i+"",i);
        }
        for(int j=10;j<HighLetter.length+10;j++){
            map.put(HighLetter[j-10],j);
        }
        for(int i=0; i <=9; i++){
            map.put(i+"",i);
        }
        return  number;
    }



}
