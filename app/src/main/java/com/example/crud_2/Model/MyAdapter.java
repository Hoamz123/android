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

import java.util.List;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHodel> {
    //khai bao nhung list de do du lieu nen recycleView
    private final List<String> LnameStudent;//du lieu ten sinh vien
    private final List<String> LidStudent;//du lieu id sinh vien
    private final List<String> LgentleStudent;// du lieu gioi tinh sinh vien

    //interface nay de tu ben ngoai co the tac dong vao ben trong recycle view
    public onMyItemListener myItemListener;

    public void setMyItemListener(onMyItemListener myItemListener) {
        this.myItemListener = myItemListener;
    }

    public MyAdapter(List<String> nameStudent, List<String> IDStudent, List<String> gentleStudent) {
        this.LnameStudent = nameStudent;
        this.LidStudent = IDStudent;
        this.LgentleStudent = gentleStudent;
    }

    @NonNull
    @Override
    //method nay muc dich la tao ra mot doi tuong MyViewHodel moi
    public MyViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail,parent,false);
        return new MyViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodel holder, int position) {
        //qia hodel de tac dong den cac du lieu xuat hien tren recycleView
        int imgID = (Objects.equals(LgentleStudent.get(position), Student.male)) ? R.drawable.male : R.drawable.female;
        holder.imgView.setImageResource(imgID);
        holder.txtView.setText(String.format("%-10s %s", LnameStudent.get(position), LidStudent.get(position)));

        //xu li nut delete tren recycle view view thong qua hodel
        holder.btnDelete.setOnClickListener(v -> {
            deleteStudent(LnameStudent.get(position),LidStudent.get(position),LgentleStudent.get(position));
        });
        //xu li khi user chon vao cardView
        holder.cardView.setOnClickListener(v -> {
            myItemListener.doSt(position);
        });
    }

    //method nay tra ve kich co cua list muc dic la dung cho viec lap lai
    @Override
    public int getItemCount() {
        return  LgentleStudent.size();
    }

    public static class MyViewHodel extends RecyclerView.ViewHolder{
        //khai bao nhung thanh phan xuat hien tran recycle View
        private final CardView cardView;
        private final TextView txtView;
        private final ImageView imgView;
        private final Button btnDelete;

        public MyViewHodel(@NonNull View itemView) {
            super(itemView);
            //anh xa
            cardView = itemView.findViewById(R.id.cardview);
            txtView = itemView.findViewById(R.id.txtView);
            imgView = itemView.findViewById(R.id.imgView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    //method them sinh vien vao recycle view
    public void addStudent(String nameStudent,String idStudent,String gentleStudent){
        LnameStudent.add(nameStudent);
        LidStudent.add(idStudent);
        LgentleStudent.add(gentleStudent);
        notifyDataSetChanged();
    }

    //method xoa sinh vien khoi recycle view
    public void deleteStudent(String nameStudent,String idStudent,String gentleStudent){
        LnameStudent.remove(nameStudent);
        LidStudent.remove(idStudent);
        LgentleStudent.remove(gentleStudent);
        notifyDataSetChanged();
    }

    //method update sinh vien
    public void updateStudent(int position,String nameStudent,String idStudent,String gentleStudent){
        LnameStudent.set(position,nameStudent);
        LidStudent.set(position,idStudent);
        LgentleStudent.set(position,gentleStudent);
        notifyDataSetChanged();
    }
}