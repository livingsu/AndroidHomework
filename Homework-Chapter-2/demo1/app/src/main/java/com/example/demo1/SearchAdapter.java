package com.example.demo1;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<TextViewHolder> implements Filterable {
    private List<String> mSourceList=new ArrayList<>();
    private List<String> mFilterList=new ArrayList<>();

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TextViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.setText(mFilterList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }


    public void notifyItems(@NonNull List<String> items){
//        mSourceList.clear();
//        mSourceList.addAll(items);
//        mFilterList.clear();
//        mFilterList.addAll(items);

        mSourceList=items;
        mFilterList=items;
//        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String conString=constraint.toString();
                if(conString.isEmpty()){
                    mFilterList=mSourceList;
                }else{
                    List<String> newList=new ArrayList<>();
                    for(String str:mSourceList){
                        if(str.contains(conString)){
                            newList.add(str);
                        }
                    }
                    mFilterList=newList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=mFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilterList=(ArrayList<String>)results.values;
                notifyDataSetChanged();

            }
        };
    }
}
