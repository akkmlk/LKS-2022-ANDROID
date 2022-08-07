package com.lks.rumahsakit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {
    String url = null, method = "GET", token = null, response = null, data = null;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResponse() {
        try{
            URL sUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) sUrl.openConnection();
            conn.setRequestMethod(method);
            if(method != "GET"){
                conn.setDoOutput(true);
            }
            if(token != null){
                conn.setRequestProperty("Authorization","Bearer "+token);
            }
            if(data != null){
                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();
            }
            if(conn.getResponseCode() == 200){
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null){
                    sb.append(line);
                }
                response = sb.toString();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }
}
