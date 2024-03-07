package com.example.javaproject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class MyAsyncTask extends AsyncTask<Void, Void, Long> {

    public interface MyInterface {
        void doit();
    }

    //private ProgressBar progressBar;
    private WeakReference<ProgressBar> progressBar;
    private WeakReference<MyInterface> delegate;

    public MyAsyncTask(MyInterface delegate, ProgressBar progressBar) {
        this.progressBar = new WeakReference<>(progressBar);
        this.delegate = new WeakReference<>(delegate);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressBar.get().setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        this.progressBar.get().setVisibility(View.GONE);
        this.delegate.get().doit();
    }

    @Override
    protected Long doInBackground(Void... voids) {
        return Utils.executeLongActionDuring7seconds();
    }
}
