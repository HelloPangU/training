import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.nio.charset.Charset;


/**
 * java.net.SocketException: Software caused connection abort: recv failed
 */
public class HttpClientTest {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8802";
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println("响应状态为："+ response.getStatusLine());
            if(entity != null){
                System.out.println("响应内容长度为："+entity.getContentLength());
                System.out.println("" + EntityUtils.toString(entity, Charset.forName("utf-8")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(response != null){
                    response.close();
                }
                if(httpClient != null) {
                    httpClient.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}