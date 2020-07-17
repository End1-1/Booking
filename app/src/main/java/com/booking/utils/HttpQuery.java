package com.booking.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.booking.interfaces.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HttpQuery extends AsyncTask<Void, Void, Integer> {

    public HttpResponse mWebResponse = null;

    public static final String mMethodGet = "GET";
    public static final String mMethodPost = "POST";
    public static final String mMethodPut = "PUT";

    private String mUrl;
    private String mMethod;
    private String mResult;
    private int mResultCode;
    private int mWebResponseCode;
    private Map<String, String> mParameters = new LinkedHashMap<>();
    private Map<String, String> mHeaders = new LinkedHashMap<>();
    boolean mIsSSL = false;

    public HttpQuery(String url, String method, int resultCode) {
        if (url.contains("http")) {
            mUrl = url;
            if (url.contains("https://")) {
                mIsSSL = true;
                initSSLNoVerify();
            }
        } else {
            mUrl = "https://" + url;
            mIsSSL = true;
        }
        mMethod = method;
        mResultCode = resultCode;
    }

    public void setParameter(String key, String value) {
        mParameters.put(key, value);
    }

    public void setHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    public void request() {
        execute();
    }

    void initSSLNoVerify() {
        try {
            TrustManager[] victimizedManager = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, victimizedManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestGET() {
        try {
            String urlParameters = new String();
            for (Map.Entry<String, String> e: mParameters.entrySet()) {
                if (!urlParameters.isEmpty()) {
                    urlParameters += "&";
                }
                urlParameters += e.getKey() + "=" + e.getValue();
            }
            if (urlParameters.length() > 0) {
                mUrl += "?" + urlParameters;
            }
            URL url = new URL(mUrl);
            HttpURLConnection con = null;
            HttpsURLConnection cons = null;
            BufferedReader br;
            Log.d("GET", mUrl);
            if (mIsSSL) {
                cons = (HttpsURLConnection) url.openConnection();
                for (Map.Entry<String, String> e: mHeaders.entrySet()) {
                    cons.setRequestProperty(e.getKey(), e.getValue());
                    Log.d("GET HEADER", String.format("%s: %s", e.getKey(), e.getValue()));
                }
                mWebResponseCode = cons.getResponseCode();
                if (mWebResponseCode <= 400) {
                    br = new BufferedReader(new InputStreamReader(cons.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(cons.getErrorStream()));
                }
            } else {
                con = (HttpURLConnection) url.openConnection();
                for (Map.Entry<String, String> e: mHeaders.entrySet()) {
                    con.setRequestProperty(e.getKey(), e.getValue());
                    Log.d("GET HEADER", String.format("%s: %s", e.getKey(), e.getValue()));
                }
//                con.setDoInput(true);
//                con.setDoOutput(true);
                mWebResponseCode = con.getResponseCode();
                if (mWebResponseCode < 400) {
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
            }
            String input;
            mResult = new String();
            while ((input = br.readLine()) != null){
                mResult += input;
            }
            br.close();
            if (mWebResponseCode >= 400) {
                Log.d("INTERNAL SERVER ERROR MESSAGE: ", mResult);
                Log.d("INTERNAL SERVER ERROR CODE", String.format("%d", mWebResponseCode));
            }
            if (cons != null) {
                cons.disconnect();
            }
            if (con != null) {
                con.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            mResult = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            mResult = e.getMessage();
        }
    }

    private void requestPost() {
        String urlParameters = new String();
        for (Map.Entry<String, String> e: mParameters.entrySet()) {
            if (!urlParameters.isEmpty()) {
                urlParameters += "&";
            }
            urlParameters += e.getKey() + "=" + e.getValue();
        }
        Log.d("HTTP POST", mUrl + " --- " + urlParameters);
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(4000);
            conn.setRequestMethod(mMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            for (Map.Entry<String, String> e: mHeaders.entrySet()) {
                conn.setRequestProperty(e.getKey(), e.getValue());
                Log.d("POST HEADER", String.format("%s: %s", e.getKey(), e.getValue()));
            }
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes(StandardCharsets.UTF_8).length));
            OutputStream out = conn.getOutputStream();
            //Log.d("URL PARAMETRS", urlParameters);
            out.write(urlParameters.getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
            mWebResponseCode = conn.getResponseCode();
            InputStream is = null;
            if (mWebResponseCode < 400) {
                is =  conn.getInputStream();
            } else {
                is = conn.getErrorStream();
                Log.d("INTERNAL SERVER ERROR CODE", String.format("%d, %s", mWebResponseCode, mUrl + " --- " + urlParameters));
            }
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            StringBuilder sb = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            mResult = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            mResult = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            mResult = e.getMessage();
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        if (mMethod.equals(mMethodGet)) {
            requestGET();
        } else if (mMethod.equals(mMethodPost) || mMethod.equals(mMethodPut)) {
            requestPost();
        }
        return mResultCode;
    }

    @Override
    protected void onPostExecute(Integer aVoid) {
        super.onPostExecute(aVoid);
        Log.d(String.format("HttpQuery: %d -> %d", mResultCode, mWebResponseCode), mResult);
        if (mWebResponseCode == 0) {
            mWebResponseCode = 600;
        }
        if (mWebResponse != null) {
            mWebResponse.webResponse(aVoid, mWebResponseCode, mResult);
        }
    }
}