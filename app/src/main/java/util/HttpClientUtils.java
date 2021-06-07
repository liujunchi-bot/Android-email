package util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//实现与服务器连接
public class HttpClientUtils {
    private static HttpURLConnection connection = null;
    public HttpClientUtils(){
    }

    private static String SendAndReceive(String httpUrl,String param) {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);// 通过远程url连接对象打开连接
           // byte[] byteBuffer = param.getBytes("utf-8");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");// 设置连接请求方式
            connection.setConnectTimeout(15000);// 设置连接主机服务器超时时间：15000毫秒
            connection.setReadTimeout(60000);// 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setDoOutput(true);// 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoInput(true);// 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");// 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            outputStream = connection.getOutputStream();// 通过连接对象获取一个输出流
            outputStream.write(param.toString().getBytes());// 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            //outputStream.write(byteBuffer);
            if (connection.getResponseCode() == 200) {//连接成功
                inputStream = connection.getInputStream();// 通过连接对象获取一个输入流，向远程读取
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));// 对输入流对象进行包装:charset根据工作项目组的要求来设置

                StringBuffer stringBuffer = new StringBuffer();//
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) // 循环遍历一行一行读取数据
                {
                    stringBuffer.append(temp);
                    stringBuffer.append("\r\n");
                }
                System.out.println("?"+stringBuffer.toString()+"?");
                int length=stringBuffer.length();
                if(length>=2){
                    stringBuffer.delete(length-2,length);
                }
                result = stringBuffer.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect(); // 断开与远程地址url的连接
        }
        return result;
    }
    private static String getData(String httpUrl) {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);// 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");// 设置连接请求方式
            connection.setConnectTimeout(15000);// 设置连接主机服务器超时时间：15000毫秒
            connection.setReadTimeout(60000);// 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setDoInput(true);// 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");// 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0

            if (connection.getResponseCode() == 200) {//连接成功
                inputStream = connection.getInputStream();// 通过连接对象获取一个输入流，向远程读取
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));// 对输入流对象进行包装:charset根据工作项目组的要求来设置

                StringBuffer stringBuffer = new StringBuffer();//
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) // 循环遍历一行一行读取数据
                {
                    stringBuffer.append(temp);
                    stringBuffer.append("\r\n");
                }
                int length=stringBuffer.length();
                stringBuffer.delete(length-2,length);//删除最后一个\r\n
                result = stringBuffer.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect(); // 断开与远程地址url的连接
        }
        return result;
    }
    public static String test(String url,JSONObject json) throws JSONException {
        String result=null;
        result= HttpClientUtils.SendAndReceive(url,json.toString());
        return result;
    }
    public static String test(String url, JSONArray jsonArray) throws JSONException {
        String result=null;
        result= HttpClientUtils.SendAndReceive(url,jsonArray.toString());
        return result;
    }
    public static String get(String url) throws JSONException {
        String result=null;
        result= HttpClientUtils.getData(url);
        return result;
    }
}
