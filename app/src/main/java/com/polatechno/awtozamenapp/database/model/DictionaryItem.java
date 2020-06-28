package com.polatechno.awtozamenapp.database.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DictionaryItem extends RealmObject {

    @PrimaryKey
    private String word;
    private int frequency = 1;

    public DictionaryItem() {
    }


    public DictionaryItem(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public DictionaryItem(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
