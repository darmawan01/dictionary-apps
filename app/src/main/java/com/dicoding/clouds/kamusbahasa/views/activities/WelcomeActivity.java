package com.dicoding.clouds.kamusbahasa.views.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.dicoding.clouds.kamusbahasa.R;
import com.dicoding.clouds.kamusbahasa.database.WordHelpers;
import com.dicoding.clouds.kamusbahasa.models.WordModel;
import com.dicoding.clouds.kamusbahasa.utils.AppPreference;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        progressBar = findViewById(R.id.progressbar);
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void>{

        final String TAG = LoadData.class.getSimpleName();
        WordHelpers wordHelpers;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            wordHelpers = new WordHelpers(WelcomeActivity.this);
            appPreference = new AppPreference(WelcomeActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firsRun = appPreference.getFirstRun();

            if (firsRun){
                ArrayList<WordModel> wordIdList = preLoadIDRaw();
                ArrayList<WordModel> wordEsList = preLoadESRaw();

                wordHelpers.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / wordIdList.size()+wordEsList.size();

                wordHelpers.beginTransaction();

                try {
                    for (WordModel model : wordIdList) {
//                        Log.w("FOR ID", "Data: "+model.getKata());
                        wordHelpers.insertIDTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                    for (WordModel model : wordEsList) {
//                        Log.w("FOR ES", "Data: "+model.getKata());
                        wordHelpers.insertESTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                    wordHelpers.setTransactionSuccess();
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: "+e.getMessage());
                }
                wordHelpers.endTransaction();
                wordHelpers.close();
                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        /*
        Setelah proses selesai
        Berjalan di Main Thread
        */
        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(WelcomeActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<WordModel> preLoadIDRaw() {
        ArrayList<WordModel> wordList = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("id_es.txt")));
            int count = 0;
            do {
                line = reader.readLine();
//                Log.i("Load File ID", "Data: "+line);
                if (line == null)continue;

                String[] splitstr = line.split("\t");
                WordModel wordModel;
                wordModel = new WordModel(splitstr[0], splitstr[1]);
                wordList.add(wordModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.w("LIST ID", "Data: "+wordList);
        return wordList;
    }

    public ArrayList<WordModel> preLoadESRaw() {
        ArrayList<WordModel> wordList = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("es_id.txt")));
            int count = 0;
            do {
                line = reader.readLine();
//                Log.i("Load File ES", "Data: "+line);
                if (line == null) continue;

                String[] splitstr = line.split("\t");
                WordModel wordModel;
                wordModel = new WordModel(splitstr[0], splitstr[1]);
                wordList.add(wordModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.w("LIST ES", "Data: "+wordList);
        return wordList;
    }
}
