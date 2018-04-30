package com.gjf.validator.certification.school;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: GJF
 * @Date : 2018/04/29
 * Time   : 18:57
 */
public abstract class BaseSchool {

    private OutputStream outputStream;
    private InputStream inputStream;
    private Map<String, String> postParams;
    private String charset;
    private String url;

    Map<String, String> getPostParams() {
        return postParams;
    }

    void setPostParams(Map<String, String> postParams) {
        this.postParams = postParams;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    BaseSchool(String url) {
        this.url = url;
    }

    {
        postParams = new HashMap<>(10);
        outputStream = null;
        inputStream = null;
        charset = "UTF-8";
    }

    /**
     * 模拟登陆用于验证学生身份
     *
     * @param userId    学校教职工ID(工号)
     * @param password  对应密码
     * @return 用户姓名
     */
    public String simulateLogin(String userId, String password) {
        HttpURLConnection connection = null;
        String response;
        int resCode;
        try {
            do {
                if (connection != null) {
                    connection.disconnect();
                }
                connection = (HttpURLConnection) new URL(this.url).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("X-Requested-With","XMLHttpRequest");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(5000);
                putPostParams(userId, password);
                String postParam = encodeParameters();
//                System.out.println(postParam);
                byte[] bytes = postParam.getBytes(charset);

                connection.setDoInput(true);
                connection.setDoOutput(true);

                outputStream = connection.getOutputStream();
                outputStream.write(bytes);
                outputStream.flush();

                connection.connect();
                resCode = connection.getResponseCode();
                if (resCode < 400) {
                    inputStream = connection.getInputStream();
                } else {
                    inputStream = connection.getErrorStream();
                }
            } while (inputStream.available() <= 0);

            response = getResponseContent(inputStream);
            connection.disconnect();
            return getUserRealName(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 设置参数
     */
    protected abstract void putPostParams(String userId, String password);

    /**
     * 更具HTML解析真真实姓名
     *
     * @param response html
     * @return
     */
    protected abstract String getUserRealName(String response);

    private String encodeParameters() {
        StringBuilder buf = new StringBuilder();
        if (postParams != null && postParams.size() > 0) {

            for (Map.Entry<String, String> tmp : postParams.entrySet()) {
                try {
                    buf.append(URLEncoder.encode(tmp.getKey(), this.charset))
                            .append("=")
                            .append(URLEncoder.encode(tmp.getValue(), charset))
                            .append("&");
                } catch (java.io.UnsupportedEncodingException neverHappen) {
                    neverHappen.printStackTrace();
                }
            }
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    private String getResponseContent(InputStream ins) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(ins,
                charset));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
