package com.dicoding.clouds.kamusbahasa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.clouds.kamusbahasa.R;
import com.dicoding.clouds.kamusbahasa.models.WordModel;
import com.dicoding.clouds.kamusbahasa.utils.Clicked;
import com.dicoding.clouds.kamusbahasa.views.activities.DetailWord;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
    Context context;
    ArrayList<WordModel> wordList;
    Clicked clicked;

    public WordAdapter(Context context, ArrayList<WordModel> wordList, Clicked clicked) {
        this.context = context;
        this.wordList = wordList;
        this.clicked = clicked;
    }

    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.word_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordAdapter.ViewHolder holder, int position) {
        holder.bindItem(wordList.get(position), clicked);
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView kata, arti;
        private View view;
        public ViewHolder(final View itemView) {
            super(itemView);

            kata = itemView.findViewById(R.id.tv_word);
            arti = itemView.findViewById(R.id.tv_mean);
            view = itemView;
        }

        public void bindItem(WordModel model, final Clicked clicked){
            kata.setText(model.getKata());
            arti.setText(model.getArti());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicked.click(getPosition());
                }
            });
        }
    }
}
