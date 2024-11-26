package com.example.crud_2.Model;

import android.annotation.SuppressLint;
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
import java.util.List;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final List<String> gentleStudent;
    private final List<String> nameStudent;
    private final List<String> idStudent;
    //khai bao mot attribute co kieu du lieu la interface onMyItemListener
    private onMyItemListener myItemListener;

    public void setMyItemListener(onMyItemListener myItemListener) {
        this.myItemListener = myItemListener;
    }

    public MyAdapter(List<String> gentleStudent, List<String> nameStudent, List<String> idStudent) {
        this.gentleStudent = gentleStudent;
        this.nameStudent = nameStudent;
        this.idStudent = idStudent;
    }

    //method nay se tao ra mot view holder moi
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //day du lieu nen recycle view tai vi tri position
        int imgId = (Objects.equals(gentleStudent.get(position), Student.male)) ? R.drawable.male : R.drawable.female;
        holder.img.setImageResource(imgId);
        //set text
        holder.txtView.setText(String.format("%-10s %s",nameStudent.get(position),idStudent.get(position)));

        //do nut xoa nam trong recycle view ne thao tac duoc luon trong method nay
        holder.btnDelete.setOnClickListener(v -> {
            //day du lieu can xoa vao ham xoa
            deleteSt(nameStudent.get(position),idStudent.get(position),gentleStudent.get(position));
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myItemListener.doSt(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return gentleStudent.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //khai bao nhung thu xuat hien tren recycle view
        private CardView cardView;
        private final TextView txtView;
        private Button btnDelete;
        private final ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //anh xa qua ID
            cardView = itemView.findViewById(R.id.cardview);
            txtView = itemView.findViewById(R.id.txtView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            img = itemView.findViewById(R.id.imgView);
        }
    }

    //viet method xoa 1 item trong list recycle view
    @SuppressLint("NotifyDataSetChanged")
    public void deleteSt(String nameSt, String idSt, String gentleSt){
        nameStudent.remove(nameSt);
        idStudent.remove(idSt);
        gentleStudent.remove(gentleSt);
        notifyDataSetChanged();
    }

    //viet method cho them mot item vao rcView
    @SuppressLint("NotifyDataSetChanged")
    public void addStudent(String nameSt, String idSt, String gentleSt){
        nameStudent.add(nameSt);
        idStudent.add(idSt);
        gentleStudent.add(gentleSt);
        notifyDataSetChanged();
    }

    //viet method sua du lieu
    @SuppressLint("NotifyDataSetChanged")
    public void updateSt(int pos, String nameSt, String idSt, String gentleSt){
        nameStudent.set(pos,nameSt);
        idStudent.set(pos,idSt);
        gentleStudent.set(pos,gentleSt);
        notifyDataSetChanged();
    }
}