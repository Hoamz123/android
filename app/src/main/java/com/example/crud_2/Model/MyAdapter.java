package com.example.crud_2.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud_2.R;

import java.io.PipedOutputStream;
import java.util.List;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHodel> {
    public interface onMyItemListener{
        void doSt(int position);
    }

    private onMyItemListener onMyItemListener;

    public void setOnMyItemListener(MyAdapter.onMyItemListener onMyItemListener) {
        this.onMyItemListener = onMyItemListener;
    }

    public final List<String> listName;
    public final List<String> listGentle;
    public final List<String> listID;

    public MyAdapter(List<String> listID, List<String> listName, List<String> listGentle) {
        this.listName = listName;
        this.listGentle = listGentle;
        this.listID = listID;
    }

    @NonNull
    @Override
    public MyViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail,parent,false);
        return new MyViewHodel(view);
    }

    //for(int position = 0;position < listGentle.size();position++)
    @Override
    public void onBindViewHolder(@NonNull MyViewHodel holder, int position) {
        //neu position la nam thi truyen anh nam,else thi truyen anh nu
        int imgId = (Objects.equals(listGentle.get(position), Student.male)) ? R.drawable.male : R.drawable.female;
        holder.imageView.setImageResource(imgId);
        holder.textView.setText(String.format("%-20s %s", listName.get(position), listID.get(position)));

        holder.btnDelete.setOnClickListener(v -> {
            String nameRemove = listName.get(position);
            String idRemove = listID.get(position);
            String gentleRemove = listGentle.get(position);
            deleteItem(nameRemove,idRemove, gentleRemove);
        });
        holder.cardView.setOnClickListener(v -> {
            onMyItemListener.doSt(position);
        });
    }


    @Override
    public int getItemCount() {
        return listGentle.size();
    }

    public static class MyViewHodel extends RecyclerView.ViewHolder {
        //khai bao nhung cai xuat hien tren recyclerview
        private final CardView cardView;
        private final ImageView imageView;
        private final TextView textView;
        private final Button btnDelete;
        public MyViewHodel(@NonNull View itemView) {
            super(itemView);
            //anh xa thong qua itemView nhung cai xuat hien trong item trong recycleView
            cardView = itemView.findViewById(R.id.cardview);
            textView = itemView.findViewById(R.id.txtView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            imageView = itemView.findViewById(R.id.imgView);
        }
    }

    //them student
    public void addStudent(String name, String ID, String gentle){
        listName.add(name);
        listID.add(ID);
        listGentle.add(gentle);
        notifyDataSetChanged();//thay doi du lieu
    }

    //xoa student
    public void deleteItem(String name,String ID,String gentle){
        listName.remove(name);
        listID.remove(ID);
        listGentle.remove(gentle);
        notifyDataSetChanged();
    }

    //update student
    public void updateStudent(int position,String name,String ID,String gentle){
        //dat lai du lieu
        listName.set(position,name);
        listID.set(position,ID);
        listGentle.set(position,gentle);
        notifyDataSetChanged();
    }
}
