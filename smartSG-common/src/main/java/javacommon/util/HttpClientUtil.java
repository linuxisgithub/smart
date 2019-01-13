package javacommon.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 类描述:
 *
 * @author Andy
 * @since 2016/4/23
 */
public class HttpClientUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private static CloseableHttpClient getHttpClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        //指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        //设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        //构建客户端
        return HttpClientBuilder.create().setConnectionManager(connManager).build();
    }


    public static String post(String url, Map<String, Object> param) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> qparams = new ArrayList<>();
        for (Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> next =  it.next();
            qparams.add(new BasicNameValuePair(next.getKey(), next.getValue().toString()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(qparams, "UTF-8");
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity response2 = response.getEntity();
        return EntityUtils.toString(response2, "UTF-8");
    }

    public static String postSsl(String url, Map<String, Object> param) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000).build();//设置请求和传输超时时间
        post.setConfig(requestConfig);
        post.setHeader("Accept-Language", "en-US;q=0.6");
        List<NameValuePair> qparams = new ArrayList<>();
        for (Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Object> next = it.next();
            qparams.add(new BasicNameValuePair(next.getKey(), next.getValue().toString()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(qparams, "UTF-8");
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity response2 = response.getEntity();
        return EntityUtils.toString(response2, "UTF-8");
    }

    public static String put(String url, Map<String, Object> param) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut put = new HttpPut(url);
        if (param != null) {
            List<NameValuePair> qparams = new ArrayList<>();
            for (Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Object> next =  it.next();
                qparams.add(new BasicNameValuePair(next.getKey(), next.getValue().toString()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(qparams, "UTF-8");
            put.setEntity(entity);
        }
        CloseableHttpResponse response = httpClient.execute(put);
        HttpEntity response2 = response.getEntity();
        return EntityUtils.toString(response2, "UTF-8");
    }

    public static String get(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity response2 = response.getEntity();
        return EntityUtils.toString(response2, "UTF-8");
    }
    
    public static String delete(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete delete = new HttpDelete(url);
        CloseableHttpResponse response = httpClient.execute(delete);
        HttpEntity response2 = response.getEntity();
        return EntityUtils.toString(response2, "UTF-8");
    }

    public static String getSsl(String url) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity response2 = response.getEntity();
        return EntityUtils.toString(response2, "UTF-8");
    }

    public static void main(String[] args) {
        try {
        	String url = "https://m.kuaidi100.com/query?type=shunfeng&postid=035584255736&id=1&valicode=&temp=0.8183302706928051";
            String response = get(url);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
