package util;


import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import entity.MyBlackList;
import entity.User;

public class BlackList {
    public static String blackList(MyBlackList myBlackList){   //添加BlackList
        //HttpURLConnection connection = null;
        //InputStream in = null;
        StringBuffer sb = new StringBuffer();
        String jsonStringFromAndroid = JSON.toJSONString(myBlackList);
        String param = null;
        try {
//            System.out.println("jsonStringFromAndroid:"+jsonStringFromAndroid);
            param = URLEncoder.encode(jsonStringFromAndroid, "UTF-8");
//            System.out.println("param1:"+param);
//            System.out.println("param2:"+ URLDecoder.decode(param,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String urlPath = "http://49.234.72.60:8080/wemail_master_war_exploded/AndroidSetBlacklistServlet" ;        //服务器

        //建立连接
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection httpConn = null;


        try {
            if (url != null) {
                httpConn = (HttpURLConnection) url.openConnection();
            }
            else {
                System.out.println("url is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "0";
        }

        System.out.println("连接已开启");


        //设置参数
        httpConn.setDoOutput(true);     //需要输出
        httpConn.setDoInput(true);      //需要输入
        httpConn.setUseCaches(false);   //不允许缓存
        try {
            httpConn.setRequestMethod("POST");      //设置POST方式连接
        } catch (ProtocolException e) {
            e.printStackTrace();
        }


        //设置请求属性
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  //post的表单传输
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");

        //建立输入流，向指向的URL传入参数
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(httpConn.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获得响应状态
        int resultCode = 0;
        try {
            resultCode = httpConn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (HttpURLConnection.HTTP_OK == resultCode) {

            String readLine = new String();
            BufferedReader responseReader = null;
            try {
                responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("");
//
                }
                responseReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            System.out.println("连接失败" + resultCode);
            return "0";
        }

//        System.out.println(sb.toString());

        return sb.toString();
    }
}
