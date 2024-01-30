package com.xw.cloud.Utils;


import lombok.SneakyThrows;
import lombok.extern.java.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Log
public class RemoteVMUtils {

    // Connection
    @SneakyThrows
    public static StringBuilder httputil(String httpurl){
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        URL url = new URL(httpurl);
        System.out.println(url);
        // 创建HTTP连接
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(30 * 60000);
        conn.setReadTimeout(30 * 60000);
        // 发送请求并获取响应
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            // 处理响应
            return response;
        }
            return null;
    }

}
