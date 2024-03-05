package com.example.javaproject.network;

import android.os.AsyncTask;
import android.util.Log;

import com.example.javaproject.model.ResponseModel;

import retrofit2.Response;

public class RetrofitTask extends AsyncTask<Void, Void, Void> {

    public interface RetrofitInterface {
        void onSuccesRetrofit(int code);
        void onFailedRetrofit(int code);
    }

    private RetrofitInterface delegate;

    public RetrofitTask(RetrofitInterface delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String TAG = "Retrofit - Task";

        try {
            Response<ResponseModel> response = Api.getInstance().getService().getAll().execute();
            Log.i(TAG, response.toString());

            if(response.isSuccessful()) {
                delegate.onSuccesRetrofit(response.code());
            }
            else {
                delegate.onFailedRetrofit(response.code());
            }
        } catch (Exception e) {
            delegate.onFailedRetrofit(0);
        }

        return null;
    }
}
