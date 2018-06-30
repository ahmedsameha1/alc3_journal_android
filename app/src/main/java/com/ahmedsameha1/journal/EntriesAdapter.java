package com.ahmedsameha1.journal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder> {
    private List<Entry> entries;
    ItemClickListener listener;

    public EntriesAdapter( ItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public EntriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EntriesViewHolder viewHolder = new EntriesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.entry, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EntriesViewHolder holder, int position) {
        holder.entry_text.setText(entries.get(position).getText());
    }

    @Override
    public int getItemCount() {
        if ( entries != null ) {
            return entries.size();
        }
        return 0;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    public List<Entry> getEntries() {
        return entries;
    }

    class EntriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView entry_text;
        public EntriesViewHolder(View itemView) {
            super(itemView);
            entry_text = itemView.findViewById(R.id.entry_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.itemClick(getAdapterPosition());
        }
    }
    public interface ItemClickListener {
        void itemClick( int position);
    }
}
