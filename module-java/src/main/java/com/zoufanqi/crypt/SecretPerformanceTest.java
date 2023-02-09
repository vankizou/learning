package com.zoufanqi.crypt;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import org.junit.Test;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import static com.zoufanqi.crypt.SecretData.DATA1;
import static com.zoufanqi.crypt.SecretData.DATA2;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/2/7 17:40
 **/
public class SecretPerformanceTest {


    @Test
    public void testAes1() {
        doForeach(1, i -> {
            final String key = randomKey(32);
            final AES aes = genAes(key);
            final String encryptData = encryptAesData(aes, DATA1);
            final String data = decryptAesData(aes, encryptData);
            System.out.println(key);
            System.out.println(encryptData);
            System.out.println(data);
        });
    }

    @Test
    public void testAes2() {
        doForeach(100000, i -> {
            final AES aes = genAes(randomKey(32));
            decryptAesData(aes, encryptAesData(aes, DATA2));
        });
    }

    @Test
    public void testRsa1() {
        doForeach(1, i -> {
            final RSA rsa = genRsa();
            final String encryptData = encryptRsaData(rsa, DATA1);
            final String data = decryptRsaData(rsa, encryptData);
            System.out.println(encryptData);
            System.out.println(data);
        });
    }

    @Test
    public void testRsa2() {
        final RSA rsa = genRsa();
        doForeach(10000, i -> {
            decryptRsaData(rsa, encryptRsaData(rsa, DATA2));
        });
    }

    @Test
    public void testAesRsa1() {
        final RSA rsa = genRsa();
        doForeach(1, i -> {
            final String key = randomKey(32);
            final AES aes = genAes(key);
            final String encryptData = encryptAesData(aes, DATA1);
            final String data = decryptAesData(aes, encryptData);

            final String encryptKeyData = encryptRsaData(rsa, key);
            final String keyData = decryptRsaData(rsa, encryptKeyData);

            System.out.println(encryptData);
            System.out.println(data);
            System.out.println(encryptKeyData);
            System.out.println(keyData);
        });
    }

    @Test
    public void testAesRsa2() {
        final RSA rsa = genRsa();
        doForeach(1000000, i -> {
            final String key = randomKey(32);
            final AES aes = genAes(key);
            decryptAesData(aes, encryptAesData(aes, DATA2));
            decryptRsaData(rsa, encryptRsaData(rsa, key));
        });
    }

    private void doForeach(int num, Consumer worker) {
        System.out.printf("开始执行%s次（%s）...", num, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        final long s = System.currentTimeMillis();
        System.out.println();
        for (int i = 0; i < num; i++) {
            worker.accept(i);
        }
        System.out.printf("耗时：%s 毫秒\n", (System.currentTimeMillis() - s));
    }

    private String encryptAesData(AES aes, String data) {
        return aes.encryptBase64(data);
    }

    private String decryptAesData(AES aes, String encryptData) {
        return aes.decryptStr(encryptData);
    }

    private AES genAes(String key) {
        return SecureUtil.aes(key.getBytes(Charset.defaultCharset()));
    }

    private String randomKey(int len) {
        return RandomUtil.randomString(len);
    }

    private String encryptRsaData(RSA rsa, String data) {
        return rsa.encryptBase64(data, Charset.defaultCharset(), KeyType.PublicKey);
    }

    private String decryptRsaData(RSA rsa, String encryptData) {
        return new String(rsa.decrypt(encryptData, KeyType.PrivateKey), Charset.defaultCharset());
    }

    private RSA genRsa() {
        return SecureUtil.rsa();
    }


}
