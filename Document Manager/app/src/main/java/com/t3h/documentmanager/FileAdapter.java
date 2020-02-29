package com.t3h.documentmanager;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.t3h.documentmanager.model.ItemFile;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private ArrayList<ItemFile> data;
    private LayoutInflater inflater;
    private ItemClickListener listener;

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public FileAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public ArrayList<ItemFile> getData() {
        return data;
    }

    public void setData(ArrayList<ItemFile> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater,R.layout.item_file,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int position) {
        ItemFile file = data.get(position);
        viewHolder.binding.setVariable(BR.item,file);
        viewHolder.binding.setVariable(BR.listener,listener);
        viewHolder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface ItemClickListener{
    }

}
