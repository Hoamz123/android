package com.example.crud_2.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.crud_2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    private List<Student> studentList;//do du lieu tu List nay ne recycleView
    private final List<Student> tmpStudentList;//trung gian phuc vu cho vc search
    private final Context context;
    //khai bao mot attribute co kieu du lieu la interface onMyItemListener
    private onMyItemListener myItemListener;

    public void setMyItemListener(onMyItemListener myItemListener) {
        this.myItemListener = myItemListener;
    }

    public MyAdapter(Context context,List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        this.tmpStudentList = new ArrayList<>(studentList);
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
        int imgId = (Objects.equals(studentList.get(position).getGentleStudent(), Student.male)) ? R.drawable.male : R.drawable.female;
        holder.img.setImageResource(imgId);
        //set text
        holder.txtView.setText(String.format("Name: %-10s\n\nID: %s",studentList.get(position).getName(),studentList.get(position).getIdStudent()));

        //do nut xoa nam trong recycle view nen thao tac duoc luon trong method nay
        holder.cardView.setOnLongClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);//truyen context ben giao dien chin hvao
            builder.setTitle("Notification");
            builder.setIcon(android.R.drawable.ic_menu_info_details);
            builder.setMessage("Do you want to delete ?");
            //case dong y xoa
            builder.setPositiveButton("Yes", (dialog, which) -> deleteSt(studentList.get(position)));
            //case khong dong y xoa
            builder.setNegativeButton("No", (dialog, which) -> {});
            //create
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        });

        holder.cardView.setOnClickListener(v -> myItemListener.doSt(position));

    }

    @Override
    public int getItemCount() {
        if(studentList != null) return studentList.size();
        return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //khai bao nhung thu xuat hien tren recycle view
        private final CardView cardView;
        private final TextView txtView;
        private final ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //anh xa qua ID
            cardView = itemView.findViewById(R.id.cardview);
            txtView = itemView.findViewById(R.id.txtView);
            img = itemView.findViewById(R.id.imgView);
        }
    }

    //viet method xoa 1 item trong list recycle view
    @SuppressLint("NotifyDataSetChanged")
    public void deleteSt(Student student){
        tmpStudentList.remove(student);
        studentList.remove(student);
        notifyDataSetChanged();
    }

    //viet method cho them mot item vao rcView
    @SuppressLint("NotifyDataSetChanged")
    public void addStudent(Student student){
        tmpStudentList.add(student);
        studentList.add(student);
        notifyDataSetChanged();
    }

    //viet method sua du lieu
    @SuppressLint("NotifyDataSetChanged")
    public void updateSt(int pos,Student student){
        int idx = tmpStudentList.indexOf(tmpStudentList.get(pos));
        if(idx != -1){
            tmpStudentList.set(pos,student);
        }
        studentList.set(pos,student);
        notifyDataSetChanged();
    }

    //thao tac cho search view
    @Override
    public Filter getFilter() {
        return new Filter() {
            //bai nay minh se search by name
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String userSearch = constraint.toString().toLowerCase();
                if(userSearch.isEmpty()){
                    studentList = new ArrayList<>(tmpStudentList);
                }else{
                    List<Student> stList = new ArrayList<>();
                    for(Student student : tmpStudentList){
                        if(student.getName().toLowerCase().contains(userSearch)){
                            stList.add(student);
                        }
                    }
                    studentList = stList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = studentList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                studentList = (List<Student>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}