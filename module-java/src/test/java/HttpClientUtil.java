package com.ldbox.opensea.Request;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class HttpClientUtil {

//	static Logger //log=LoggerFactory.getLogger(HttpClientUtil.class);

    public static String doGet(String url) {
        //创建post请求对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "*/*");
        httpGet.addHeader("Connection", "keep-alive");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.addHeader("User-Agent", "PostmanRuntime/7.29.0");
        httpGet.addHeader("Cookie", "__cf_bm=22lBA7Rjj2un0jziDN4jcow_ZLAeFx_Gv23f6MHnth8-1654522701-0-AWRSrBw/boyt0PQIdZuw8KP4kL+V8DWOGYtaBi4XO7fZbefCcy3w9GHul5U5gm98EvxeSqcVcngYgKxLbiwbn08=; sessionid=eyJzZXNzaW9uSWQiOiIxOTI4MTk4NC05ZjI2LTQxMTktYmRmMy0wYjJhNDZlYjdiMzIifQ:1nyCw1:xlju-IfcWgidT9bmbK1yBrJx7wsRLY4E3U103IUw77g");
        httpGet.addHeader("Content-Type", "application/json");

        //创建CloseableHttpClient对象（忽略证书的重点）
        CloseableHttpClient client = null;
        try {
            SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(
                    SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
                    NoopHostnameVerifier.INSTANCE);
            client = HttpClients.custom().setSSLSocketFactory(scsf).build();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            //设置请求头
            //使用CloseableHttpClient发送请求
            trustEveryone();
            CloseableHttpResponse response = client.execute(httpGet);
            //获取返回code
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            //根据返回code进行处理
            if (statusCode == 200) {
                //log.info("请求：" + statusCode);
                //获取响应结果
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            } else {
                //log.info("请求" + statusCode);
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            //log.info("请求失败");
            //log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String doGet2(String url) {
        String content = "";

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "*/*");
        httpGet.addHeader("Connection", "keep-alive");
        httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
        httpGet.addHeader("User-Agent", "PostmanRuntime/7.29.0");
        httpGet.addHeader("Cookie", "__cf_bm=22lBA7Rjj2un0jziDN4jcow_ZLAeFx_Gv23f6MHnth8-1654522701-0-AWRSrBw/boyt0PQIdZuw8KP4kL+V8DWOGYtaBi4XO7fZbefCcy3w9GHul5U5gm98EvxeSqcVcngYgKxLbiwbn08=; sessionid=eyJzZXNzaW9uSWQiOiIxOTI4MTk4NC05ZjI2LTQxMTktYmRmMy0wYjJhNDZlYjdiMzIifQ:1nyCw1:xlju-IfcWgidT9bmbK1yBrJx7wsRLY4E3U103IUw77g");
        httpGet.addHeader("Content-Type", "application/json");


        // 响应模型
        CloseableHttpResponse response = null;
        try {
//            trustEveryone();
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                content = EntityUtils.toString(responseEntity);
                System.out.println("响应内容为:" + content);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    public static String doGet3(String uri) {
        HttpURLConnection connection = null;
        try {
            trustEveryone();
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            System.out.println(connection.getResponseCode());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static JSONObject doPost(String url, String data) {
        //创建post请求对象
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = new JSONObject();
        //创建CloseableHttpClient对象（忽略证书的重点）
        CloseableHttpClient client = null;
        try {
            SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(
                    SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
                    NoopHostnameVerifier.INSTANCE);
            client = HttpClients.custom().setSSLSocketFactory(scsf).build();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            //设置请求头
            httpPost.setEntity(new StringEntity(data, StandardCharsets.UTF_8));
            //使用CloseableHttpClient发送请求
            CloseableHttpResponse response = client.execute(httpPost);
            //获取返回code
            int statusCode = response.getStatusLine().getStatusCode();
            //根据返回code进行处理
            if (statusCode == 200) {
                //log.info("请求：" + statusCode);
                //获取响应结果
                HttpEntity entity = response.getEntity();
                jsonObject = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
            } else {
                HttpEntity entity = response.getEntity();
                jsonObject = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));

                //log.info("请求" + statusCode);

                return jsonObject;
            }
        } catch (IOException e) {
            //log.info("请求失败");
            //log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static void main(String[] args) {

//        String url = "https://testnets-api.opensea.io/api/v1/assets?format=json&include_orders=false&limit=1&offset=0&order_direction=desc";
        String url = "https://www.google.com/search?q=java%E7%BF%BB%E5%A2%99%E8%B0%83%E6%8E%A5%E5%8F%A3&oq=java%E7%BF%BB%E5%A2%99%E8%B0%83%E6%8E%A5%E5%8F%A3&aqs=chrome..69i57.5765j0j7&sourceid=chrome&ie=UTF-8";
        String data = HttpClientUtil.doGet2(url);
        System.out.println(data);

    }


}


