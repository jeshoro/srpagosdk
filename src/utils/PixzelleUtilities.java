/*
 * Copyright (c) 2014. Pixzelle S.R.L de C.V
 * @author Rodolfo Peña
 * @version 1.0 1/14/14 2:50 PM
 */

package sr.pago.sdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import android.util.Patterns;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Class for doing some operations without building objects
 *
 * @author Rodolfo Pena - * Sr. Pago All rights reserved.
 * @version 1.0
 * @since 2014-03-26
 */
public class PixzelleUtilities {

    private PixzelleUtilities(){

    }

    /**
     * @see SSLSocketFactory
     * Builds a secure socket layer with SSL.
     */
    private static class MySSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[]{tm}, null);
        }

        public MySSLSocketFactory(SSLContext context) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
            super(null);
            sslContext = context;
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException{
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

    /**
     * This method check if the device is conected to internet.
     * @param context
     * @return
     */
    public static boolean isConnectingToInternet(Context context) {
        if (context == null) {
            return true;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static String getDevice() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    /**
     * Builds a RESTFUL url for the web service
     *
     * @param url    Url where the web services are allocated.
     * @param method Method to be called from the web service.
     * @param params Parameters that web service needs.
     * @return The url built.
     */
    public static String BuildRestWcfUrl(String url, String method, String... params) {
        String finalUrl;

        finalUrl = String.format("%s%s/", url, method);

        for (String param : params)
            finalUrl = String.format(Locale.getDefault(), "%s%s,", finalUrl, param);

        finalUrl = finalUrl.substring(0, finalUrl.length() - 1);

        return finalUrl;
    }

    /**
     * Builds a QueryString url for the web service
     *
     * @param url    Url where the web services are allocated.
     * @param method Method to be called from the web service.
     * @param params Parameters that web service needs.
     * @return The url built.
     */
    public static String BuildQueryStringWcfUrl(String url, String method, Pair<String, String>... params) {
        String finalUrl;
        finalUrl = String.format("%s%s?", url, method);

        for (Pair<String, String> param : params)
            finalUrl = String.format(Locale.getDefault(), "%s%s=%s&", finalUrl, param.first, param.second);

        finalUrl = finalUrl.substring(0, finalUrl.length() - 1);

        return finalUrl;
    }

    /**
     * Save an image in the app directory
     *
     * @param context  Context where the app is running.
     * @param filename Name of the file to be opened.
     * @param content  Content to write in the file
     */
    public static void saveImage(Context context, String filename, byte[] content) throws IOException {
        FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        outputStream.write(content);
        outputStream.close();
    }

    /**
     * Write a text file in the app directory
     *
     * @param context  Context where the app is running.
     * @param filename Name of the directory to be opened.
     * @return The number of files contained in the directory.
     */
    public static int getFileCount(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);

        if (file.listFiles() == null) {
            file.mkdirs();
            return 0;
        }
        return file.listFiles().length;
    }

    public static boolean isValidUrl(String email) {
        Pattern pattern = Patterns.WEB_URL;
        return pattern.matcher(email).matches();
    }

    public static void saveFile(FileInputStream inputStream, String filename) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        int len = 0;

        byte[] buffer = new byte[bufferSize];

        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }

        fileOutputStream.write(byteArrayOutputStream.toByteArray());
    }

    public static String convertToBase64(File file) throws Exception {
        InputStream inputStream = null;//You can get an inputStream using any IO API
        inputStream = new FileInputStream(file.getAbsolutePath());
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Logger.logError(e);
        }
        bytes = output.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static Bitmap rotateBitmap(Bitmap source, int angle) {
        switch (angle) {
            case ExifInterface.ORIENTATION_NORMAL:
                angle = 0;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                angle = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                angle = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                angle = 270;
                break;
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public static void copyFile(File src, File dst) throws IOException{
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}
