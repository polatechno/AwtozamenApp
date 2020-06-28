package com.polatechno.awtozamenapp;

import android.app.Application;
import android.util.Log;

import com.polatechno.awtozamenapp.database.RealmController;
import com.polatechno.awtozamenapp.database.model.DictionaryItem;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.LongDef;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private RealmController realmController;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("MyAppOnCretae", "App Opened");

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("dictionary_words.realm")
                .build();
        Realm.setDefaultConfiguration(config);


        realmController = RealmController.with(this);

        RealmResults<DictionaryItem> dictionaryItems = realmController.getAllWords();
        if (dictionaryItems.size() <= 0) {
            realmController.prelaodDatabase();
        }

    }


}
