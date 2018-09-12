package com.dicoding.clouds.kamusbahasa.views.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dicoding.clouds.kamusbahasa.R;
import com.dicoding.clouds.kamusbahasa.adapter.WordAdapter;
import com.dicoding.clouds.kamusbahasa.database.WordHelpers;
import com.dicoding.clouds.kamusbahasa.models.WordModel;
import com.dicoding.clouds.kamusbahasa.utils.Clicked;
import com.dicoding.clouds.kamusbahasa.views.activities.DetailWord;

import java.util.ArrayList;

public class IdEsFragment extends Fragment implements View.OnClickListener, Clicked{


    EditText edtWord;
    Button btnSearch;
    RecyclerView rcWord;
    WordAdapter wordAdapter;
    ArrayList<WordModel> wordList = new ArrayList<>();
    WordHelpers wordHelpers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_id_en, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtWord = view.findViewById(R.id.edt_word);
        btnSearch = view.findViewById(R.id.btn_search_id);
        btnSearch.setOnClickListener(this);
        rcWord = view.findViewById(R.id.rc_word);

        wordAdapter = new WordAdapter(getContext(), wordList, this);
        rcWord.setLayoutManager(new LinearLayoutManager(getContext()));
        rcWord.setAdapter(wordAdapter);

        wordHelpers = new WordHelpers(getContext());
        wordHelpers.open();

        showAll();
        edtWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(edtWord.getText())){
                    showAll();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(edtWord.getText())){
                    showAll();
                }
            }
        });
    }

    private void showAll(){
        wordList.clear();
        wordList.addAll(wordHelpers.getAllID());
        wordAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_search_id:
                loadData(edtWord.getText().toString());
                break;
        }
    }

    private void loadData(String word){
        wordList.clear();
        wordList.addAll(wordHelpers.getIdWord(word));
        wordAdapter.notifyDataSetChanged();
    }

    @Override
    public void click(int posisi) {
        Intent intent = new Intent(getContext(), DetailWord.class);
        intent.putExtra(DetailWord.WORD_LIST, wordList.get(posisi));
        startActivity(intent);
    }
}
