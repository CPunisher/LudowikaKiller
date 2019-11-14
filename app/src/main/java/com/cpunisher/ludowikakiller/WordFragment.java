package com.cpunisher.ludowikakiller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class WordFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText searchEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_word, null);

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new WordRecycleViewAdapter(WordDao.getInstance(getContext()).getAllWord()));

        searchEdit = view.findViewById(R.id.search_edit);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((WordRecycleViewAdapter) recyclerView.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private class WordRecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

        private Filter filter;
        private List<Word> sourceList;
        private List<Word> filterList;

        public WordRecycleViewAdapter(List<Word> list) {
            this.sourceList = list;
            this.filterList = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(filterList.get(position).getWord());
            holder.meaning1 = filterList.get(position).getMeaning1();
            holder.meaning2 = filterList.get(position).getMeaning2();
        }

        @Override
        public int getItemCount() {
            return filterList.size();
        }

        @Override
        public Filter getFilter() {
            if (filter == null) {
                filter = new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        String s = constraint.toString();
                        if (s.isEmpty()) {
                            filterList = sourceList;
                        } else {
                            filterList = sourceList.stream().filter(w -> w.getWord().contains(s)).collect(Collectors.toList());
                        }
                        FilterResults filterResults = new FilterResults();
                        filterResults.values = filterList;
                        return filterResults;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        filterList = (List<Word>) results.values;
                        notifyDataSetChanged();
                    }
                };
            }
            return filter;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private String meaning1;
        private String meaning2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), meaning1, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
