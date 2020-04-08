package utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @description: Http接口请求
 * @author: Fearon
 * @create: 2018/6/14 15:38
 **/
public class HttpRequestCommon {
    /**
     * get请求
     * @param uri
     * @param params "key=value&key1=value1……"
     * @return
     */
    public static String doGet(String uri, String params) {
        if(null == uri || uri.length() <= 0)
            System.err.println("请求地址不可为空！");
        StringBuilder realURL = new StringBuilder();
        realURL.append(uri);
        if(null != params && params.length() > 0) {
            realURL.append("?");
            realURL.append(params);
        }

        StringBuffer result = null;
        URL url;
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        int code;

        try {
            url = new URL(realURL.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置编码格式，默认为服务器操作系统的编码格式
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            // true -- 允许提交参数
            httpURLConnection.setDoOutput(true);
            // true -- 允许读取反馈
            httpURLConnection.setDoInput(true);
            // 数据提交格式
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置请求方法，默认为GET
            //httpURLConnection.setRequestMethod("GET");
            // 缓存禁用
            httpURLConnection.setUseCaches(false);
            //防止屏蔽程序抓取而返回403错误，若不需要开启虚拟浏览器功能则无需启动该设置
            //httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            httpURLConnection.connect();

            // 获取接口服务器及当前会话状态，保证获取到的服务器状态能正常读取
            code = httpURLConnection.getResponseCode();
            if (code == 404) {
                System.err.println("认证无效，找不到此次认证的会话信息！");
            }
            if (code == 500) {
                System.err.println("认证服务器发生内部错误！");
            }
            if (code != 200) {
                System.err.println("发生其它错误，认证服务器返回 " + code);
            }

            reader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            result = new StringBuffer();
            String line;
            while((line = reader.readLine()) != null){
                result.append(line);
            }
            reader.close();
        } catch (MalformedURLException e) {
            System.err.println("没有指定通信协议！");
        } catch (IOException e) {
            System.err.println("数据流开启异常！");
        } catch (NullPointerException e) {
            System.err.println("未读取到有效数据流！");
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("数据流关闭异常！");
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return result.toString();
    }

    /**
     * post请求
     * @param uri
     * @param params
     * @return
     */
    public static String doPost(String uri, String params) {
        if(null == uri || uri.length() <= 0)
            System.err.println("请求地址不可为空！");
        StringBuilder realURL = new StringBuilder();
        realURL.append(uri);

        StringBuffer result = null;
        URL url;
        HttpURLConnection httpURLConnection = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader reader = null;
        int code;

        try {
            url = new URL(realURL.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();

            /**
             * 封装header
             */
            // 设置编码格式，默认为服务器操作系统的编码格式
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            // 使用json数据进行请求
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("connection", "keep-alive");
            // 设置请求方法，默认为GET
            httpURLConnection.setRequestMethod("POST");
            // 设置不要缓存
            httpURLConnection.setUseCaches(false);
            // 自动重定向
            httpURLConnection.setInstanceFollowRedirects(true);
            // true -- 允许提交参数
            httpURLConnection.setDoOutput(true);
            // true -- 允许读取反馈
            httpURLConnection.setDoInput(true);
            //防止屏蔽程序抓取而返回403错误，若不需要开启虚拟浏览器功能则无需启动该设置
            //httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            httpURLConnection.connect();

            // 获取接口服务器及当前会话状态，保证获取到的服务器状态能正常读取
            code = httpURLConnection.getResponseCode();
            if (code == 404) {
                System.err.println("认证无效，找不到此次认证的会话信息！");
            }
            if (code == 500) {
                System.err.println("认证服务器发生内部错误！");
            }
            if (code != 200) {
                System.err.println("发生其它错误，认证服务器返回 " + code);
            }

            // 存在参数时，将参数进行提交
            if(null != params && params.length() > 0) {
                outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                outputStreamWriter.write(params);
                outputStreamWriter.flush();
            }

            reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            result = new StringBuffer();
            String line;
            while((line = reader.readLine()) != null){
                result.append(line);
            }
        } catch (MalformedURLException e) {
            System.err.println("没有指定通信协议！");
        } catch (IOException e) {
            System.err.println("数据流开启异常！");
        } catch (NullPointerException e) {
            System.err.println("未读取到有效数据流！");
        } finally {
            try {
                if(reader != null)
                    reader.close();
                if(outputStreamWriter != null)
                    outputStreamWriter.close();
            } catch (IOException e) {
                System.err.println("数据流关闭异常！");
            }
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
        }

        return result.toString();
    }
}
