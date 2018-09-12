package com.dicoding.clouds.kamusbahasa.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.dicoding.clouds.kamusbahasa.R;
import com.dicoding.clouds.kamusbahasa.models.WordModel;

public class DetailWord extends AppCompatActivity {

    public final static String WORD_LIST = "word_list";
    protected WordModel wordModel;
    private TextView tvWord, tvMean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_word);

        tvMean = findViewById(R.id.tv_dtl_mean);
        tvWord = findViewById(R.id.tv_dtl_word);

        wordModel = getIntent().getParcelableExtra(WORD_LIST);

        tvWord.setText(wordModel.getKata());
        tvMean.setText(wordModel.getArti());

        getSupportActionBar().setTitle(wordModel.getKata());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
