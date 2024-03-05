package com.example.javaproject.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.javaproject.model.ResponseModel;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BasicTask extends AsyncTask<Void, Void, Void> {

    public interface BasicInterface {
        void onSuccesRetrofit(int code);
        void onFailedRetrofit(int code);
    }

    private BasicInterface delegate;

    public BasicTask(BasicInterface delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL("https://dog.ceo/api/breeds/list/all");
            urlConnection = (HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();
            if (code !=  200) {
                delegate.onFailedRetrofit(code);
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                ResponseModel data = parse(line);
                Log.i("data", data.toString());
            }

            delegate.onSuccesRetrofit(code);
        } catch (Exception e) {
            e.printStackTrace();
            delegate.onFailedRetrofit(0);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    private ResponseModel parse(String jsonString) {
        try {
            JSONObject main = new JSONObject(jsonString);

            Object message = main.get("message");
            String statut = main.getString("status");

            return new ResponseModel(message, statut);
        }
        catch (Exception e) {
            return null;
        }
    }
}
