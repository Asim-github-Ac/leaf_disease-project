package com.example.saadi1.leafdetection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.myHolder> {

    Context context;
    List<Model> modelList;

    public DiseaseAdapter(Context context, List<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public DiseaseAdapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.saved_data,parent,false);
        return new DiseaseAdapter.myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseAdapter.myHolder holder, int position) {

        Model model=modelList.get(position);
        holder.tv_name.setText("Name : "+model.getName());
        holder.tv_ins1.setText(model.getIns1());
        holder.tv_ins2.setText(model.getIns2());
        holder.tv_ins3.setText(model.getIns3());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_ins1,tv_ins2,tv_ins3;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.diseasename_item);
            tv_ins1=itemView.findViewById(R.id.ins1);
            tv_ins2=itemView.findViewById(R.id.ins2);
            tv_ins3=itemView.findViewById(R.id.ins3);

        }
    }
}
