package pres.swegnhan.packhelper.taskclass;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pres.swegnhan.packhelper.core.PushUserPackages;
import pres.swegnhan.packhelper.infrastructure.pushfilerepository.PushFileRepository;
import pres.swegnhan.packhelper.infrastructure.userrepository.UserQueryRepoditory;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.concurrent.LinkedBlockingQueue;

//@Component
//@Scope("prototype")
public class PushFileTask implements Runnable {
    @Value("${pres.swegnhan.packhelper.debhubpath}")
    public String PACK_HUB_PATH;
//    @Autowired
//    PushFileRepository pushFileRepository;
    public static PushFileRepository pushFileRepository = ApplicationContextProvider.getBean(PushFileRepository.class);
    public static LinkedBlockingQueue<PushUserPackages> linkedBlockingQeque =new LinkedBlockingQueue<PushUserPackages>();
//    MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
//
//    HttpClient client = new HttpClient(connectionManager);
//    new
//    public PushFileRepository pushFileRepository;
    @Override
    public void run() {
        String ip ="127.0.0.1";
        String fileName="";
        PushUserPackages pushUserPackage = null;
        while (true){
//            解析LinkBlockingQeque中的单个对象
            try {
                pushUserPackage = linkedBlockingQeque.take();
                ip = pushUserPackage.getUserIp();
                fileName = pushUserPackage.getPackName();
                System.out.println(fileName+"***************************");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("lalala******************************");
            }


            // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
//            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(50000).setSocketTimeout(50000).build();


            // 创建Post请求./packagefile
            HttpPost httpPost = new HttpPost("http://"+ip+":"+"1180");
//            HttpPost httpPost = new HttpPost("http://localhost:8080/packagefile");
//            File file = new File("/home/caixujie/IdeaProjects/packhub/"+fileName);
            httpPost.setConfig(requestConfig);
//            httpPost.setHeader("Content-Length", String.valueOf(file.length()));
//        User user = new User();
//        user.setName("潘晓婷");
//        user.setAge(18);
//        user.setGender("女");
//        user.setMotto("姿势要优雅~");
//        // 我这里利用阿里的fastjson，将Object转换为json字符串;
//        // (需要导入com.alibaba.fastjson.JSON包)
//        String jsonString = JSON.toJSONString(user);
            File file = new File("/home/caixujie/IdeaProjects/packhub/"+fileName);

//        StringEntity entity = new StringEntity(jsonString, "UTF-8");


            MultipartEntityBuilder mutiEntity = MultipartEntityBuilder.create();
            //File file = new File("C:\\Users\\\\Desktop\\20171002中士.mp4");
            //mutiEntity.addTextBody("filename","1问题.mp4");
//            mutiEntity.addPart("file", new FileBody(file));
            mutiEntity.addBinaryBody("file",file);
            mutiEntity.setCharset(Charset.forName("utf-8"));
            mutiEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//            mutiEntity.setContentType(ContentType.create("multipart/form-data","uft-8"));


//            mutiEntity.addBinaryBody("file",file);




            // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
//        httpPost.setEntity(entity);
            httpPost.setEntity(mutiEntity.build());
//            httpPost.setHeader("Content-Type", "multipart/form-data;charset=utf8");

//            httpPost.setHeader("Content-Length", String.valueOf(file.length()));


            // 响应模型
            CloseableHttpResponse response = null;
            try {
                // 由客户端执行(发送)Post请求
                response = httpClient.execute(httpPost);
                // 从响应模型中获取响应实体
                HttpEntity responseEntity = response.getEntity();

                System.out.println("响应状态为:" + response.getStatusLine());
                if (responseEntity != null) {
                    System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                    System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                 }
                //删除数据库中的undolist
                try{pushFileRepository.delete(pushUserPackage);
                    System.out.println("删除成功");}
                    catch (Exception e){
                    e.printStackTrace();
                        System.out.println("没有删除成功");
                    }


//            推送成功超时
            }catch (SocketTimeoutException e) {
                e.printStackTrace();
                System.out.println("推送成功！");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    System.out.println("兑现对象对象！！！！！！！！！！！！"+pushUserPackage.toString());
                    pushFileRepository.insert(pushUserPackage);
                }catch (Exception ec){
                    ec.printStackTrace();
                    System.out.println("不删除，无操作");
                }
            }
            //            不在线连接失败
//            catch (ConnectException e) {
//                e.printStackTrace();
//                pushFileRepository.insert(pushUserPackage);
//            }
            finally {
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
            Thread.yield();
        }






    }
}
