package com.sw.beauty.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer, Boolean> {


    private static final String TAG = "DownloadTask";

    private DownloadCallback callback;

    public DownloadTask(DownloadCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(String... urls) {
        String fileUrl = urls[0];
        String destinationPath = urls[1];

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }

            int fileLength = connection.getContentLength();
            inputStream = new BufferedInputStream(connection.getInputStream());
            outputStream = new FileOutputStream(destinationPath);

            byte[] data = new byte[1024];
            int totalBytesRead = 0;
            int bytesRead;
            while ((bytesRead = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, bytesRead);
                totalBytesRead += bytesRead;

                // 计算下载进度并发布进度更新
                int progress = (int) ((totalBytesRead * 100L) / fileLength);
                publishProgress(progress);
            }

            // 下载完成
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error occurred during download", e);
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error occurred during resource cleanup", e);
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        callback.onProgressUpdate(progress);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            callback.onDownloadComplete();
        } else {
            callback.onDownloadFailed();
        }
    }
    public interface DownloadCallback {
        void onProgressUpdate(int progress);
        void onDownloadComplete();
        void onDownloadFailed();
    }
}
