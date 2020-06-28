package com.polatechno.awtozamenapp.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.polatechno.awtozamenapp.R;
import com.polatechno.awtozamenapp.database.RealmController;
import com.polatechno.awtozamenapp.database.model.DictionaryItem;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private ArrayList<DictionaryItem> mDataset;
    private Context context;
    private RealmController realmController;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public Button button;

        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textView);
            button = v.findViewById(R.id.button);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchAdapter(ArrayList<DictionaryItem> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
        this.realmController = RealmController.getInstance();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_words_list, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getWord());

        holder.button.setOnClickListener((View v) -> {

            try {
                realmController.deleteWord(mDataset.get(position).getWord());
            } catch (Exception e) {
                Log.d("Exception Error", String.valueOf(e));
            }

            mDataset.remove(position);
            this.notifyDataSetChanged();
            Toast.makeText(context, R.string.removed_word, Toast.LENGTH_LONG).show();
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}