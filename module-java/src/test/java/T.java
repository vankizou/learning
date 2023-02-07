import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @author zoufanqi
 * @version v1.0
 * @since 2023/1/6 17:16
 **/
public class T {

    public static String decryptRsa(String privateKey,String encrypted) {

        try{
            byte[] keyOrigin =  ZlibUtil.decodeBase64(privateKey.replaceAll(" ", "+"));

            PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(keyOrigin);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            Key key = keyFactory.generatePrivate(x509EncodedKeySpec);


            byte[] encryptedBytes = ZlibUtil.decodeBase64(encrypted.replaceAll(" ", "+"));

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(encryptedBytes);

            return Base64.getEncoder().encodeToString(result);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static byte[] decryptAES(String aesKey,String encrypted) {

        try{
            byte[] keyOrigin =  ZlibUtil.decodeBase64(aesKey.replaceAll(" ", "+"));

            IvParameterSpec iv = new IvParameterSpec(keyOrigin);
            SecretKeySpec key = new SecretKeySpec(keyOrigin, "AES");

            byte[] encryptedBytes = ZlibUtil.decodeBase64(encrypted.replaceAll(" ", "+"));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,key,iv);
            byte[] result = cipher.doFinal(encryptedBytes);

            return result;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String decryptAESAndDecodeZlib(String aesKey,String encrypted) {
        byte[] result = decryptAES(aesKey, encrypted);
        return ZlibUtil.decodeZlib(result);
    }



    public static void main(String[] args){
        String privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAnaPZ44pqFoyJER8ijs5Knevepr2aL4d05rxk4Qh5f0uJ3amY/srZgRCaZE/uv4RyPqiGRaNdxisMqYF/YaWq8QIDAQABAkAJKrUJ8UuYAhYaY8gbq81U/5lT8uSbbPIUVNI878Q47bE3OMR5Mh2+Efq8M/hkIO4J9v6tImDsqVkZligRDJptAiEAz7bwE77LAhRIWKhhnOu5S5nVq/JcFsq7d4c9UzEhEscCIQDCSPwLECHEvPCBzkoKFDEcGsdj45B+Mkx3coWflKEchwIgRUVdCzi1uxvIhMiixRJX2T6UY6OP0ooJuvL/IJ9hyEECIQC0qEEV2G5ODpJlYXkfXh88TvpRO+3EKulHuMnf1cjqSQIhAIm/hk4/DUvQ63ig9IyjYkQZoNeNjAViRX9KpZMbuu1r";
        String encrypted = "QEQmI0mU3/jxYIpt10jvrqgo0Ha+ZqbQUSxK1Czm8FGi5/kh+6eDftHIaBTucYp8RJIDoVSsAqqtW94PLZM3QQ==";
        String result = decryptRsa(privateKey,encrypted);
        String text = "eJxVUktOw0AMvQqysgCpKfOfTHZQYAkLloCqNJmWqiGpkkkRRT0CR+AMrLkPC46BPaUSRPl47Ge/ZzuvMPQd5K9QLSvIgV2cqcw6nU74+VWqLvlV6oTJUq7M5FywySWbSNiNIGwhF1nG6BpB+9x4LALbBYyg8rNhATm6ixVVtM54VxbeVlbNWZWVtqjMbD7zirFMMszo6waBy9Cgva7JbHs0h4CmYEKmjKfMHHGTC5VrRxxFKCC/Q9WE8ZtAmbGN5Knx5LtuG4/OpCRdx81Q1yf7Nzl97LX3RVc+zoYQ2mZa1styhbHp0PtuGuNca8OZktJxLYS2IlInGxIrxnLMBR1RKgq+uaXclX95bjvKpUhBwO+3j6/Pd7wjdhPLjtWYR23DgUdnUjnjhJIs08KaGKX0s/X66Da0Xexl35kipr32KHoalk/+d1L3p4zj829WSV9CbulLbNxY4RyycWUVOv8vMinDHwhi+GEiTRFJFvPtC+x2D7i0akXdSGxGaIjn+AektLzdD3v7oiQ=";
        String result2 = decryptAESAndDecodeZlib(result,text);
        System.out.println(result);
        System.out.println(result2);
        generate();
    }

    public static void generate(){
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            System.out.println(publicKey);
            System.out.println(privateKey);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
