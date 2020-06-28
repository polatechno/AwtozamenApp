package com.polatechno.awtozamenapp.database;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.polatechno.awtozamenapp.database.model.DictionaryItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmController {

    private static final String TAG = "RealmController";

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }


    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    /**
     * Initially preloads database with programming terminology list
     * Initially only these words will be autocorrected
     */
    public void prelaodDatabase() {
        final List<DictionaryItem> programmingTerminList = Arrays.asList(
                new DictionaryItem("java"),
                new DictionaryItem("kotlin"),
                new DictionaryItem("php"),
                new DictionaryItem("swift"),
                new DictionaryItem("android"),
                new DictionaryItem("ios"),
                new DictionaryItem("css"),
                new DictionaryItem("html"),
                new DictionaryItem("groovy"),
                new DictionaryItem("grails"),
                new DictionaryItem("mysql"),
                new DictionaryItem("python"),
                new DictionaryItem("django"),
                new DictionaryItem("c++")
        );

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                bgRealm.insertOrUpdate(programmingTerminList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });

    }

    /**
     * Save word when it is written for the first time
     *
     * @param newWord
     */
    public void insertNewRecord(final String newWord) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                bgRealm.insert(new DictionaryItem(newWord));
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.d(TAG, "onSuccess: Succesfully inserted ");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d(TAG, "onError: Error while inserting: " + error.getMessage());
            }
        });
    }

    /**
     * Increments frequency for word already exists
     *
     * @param existingWord
     * @param freq
     */
    public void updateRecord(String existingWord, int freq) {

        DictionaryItem toEdit = realm.where(DictionaryItem.class).equalTo("word", existingWord).findFirst();
        realm.beginTransaction();
        toEdit.setFrequency(freq);
        realm.commitTransaction();


    }

    /**
     * returns all words in the db
     *
     * @return
     */
    public RealmResults<DictionaryItem> getAllWords() {
        return realm.where(DictionaryItem.class).findAll();
    }

    public int getWordFrequency(String word) {
        DictionaryItem item = realm.where(DictionaryItem.class).equalTo("word", word).findFirst();

        return (item != null) ? item.getFrequency() : 0;

    }

    /**
     * The method returns the list of words to appear as suggestions.
     *
     * @return wordList
     */
    public ArrayList<String> getCandidateWords(String query) {

        ArrayList<String> wordlist = new ArrayList<>();

        RealmResults<DictionaryItem> dictionaryItemRealmResults = realm.where(DictionaryItem.class).beginsWith("word", query).limit(10).findAllAsync().sort("frequency", Sort.DESCENDING);
        for (DictionaryItem item :
                dictionaryItemRealmResults) {
            wordlist.add(item.getWord());
        }

        return wordlist;
    }

    /**
     * The method returns the list of words to appear as suggestions.
     *
     * @return wordList
     */
    public ArrayList<DictionaryItem> getWordsByQueery(String query) {

        ArrayList<DictionaryItem> wordlist = new ArrayList<>();
        RealmResults<DictionaryItem> dictionaryItemRealmResults;

        if (TextUtils.isEmpty(query)) {
            dictionaryItemRealmResults = realm.where(DictionaryItem.class).findAllAsync();
        } else {
            dictionaryItemRealmResults = realm.where(DictionaryItem.class).contains("word", query).findAllAsync().sort("frequency", Sort.DESCENDING);
        }

        wordlist.addAll(dictionaryItemRealmResults);
        return wordlist;
    }

    public void deleteWord(final String word) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DictionaryItem> rows = realm.where(DictionaryItem.class).equalTo("word", word).findAll();
                rows.deleteAllFromRealm();
            }
        });
    }


}