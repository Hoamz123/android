package com.example.crud_2;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud_2.Model.MyAdapter;
import com.example.crud_2.Model.Student;
import com.example.crud_2.Model.onMyItemListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements onMyItemListener {
    private List<String> gentleSt;
    private List<String> nameSt;
    private List<String> idSt;
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private EditText editTextName,editTextId;
    private Button btnAdd,btnUpdate;
    private RadioGroup radioGroup;
    private RadioButton radioButtonMale,radioButtonFemale;
    private int currPos;//luu lai chi so hien tai cua obj dang moon sua

    protected void onCreate(Bundle savedInstanceState) {

        // Đinh Văn Hòa (hoamz)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();//du lieu dau vao
        initView();//anh xa qua ID
        adapter = new MyAdapter(gentleSt,nameSt,idSt);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        //giao tiep voi recycle view thong qua adapter
        adapter.setMyItemListener(this);

        //khoa dung man hinh


        //method khi nhan nut addStudent
        handleBtnAdd();
        handleBtnUpdate();

    }

    private void initView() {
        recyclerView = findViewById(R.id.rcView);
        editTextName = findViewById(R.id.edTextName);
        editTextId = findViewById(R.id.edTextId);
        radioGroup = findViewById(R.id.radioGr);
        radioButtonMale = findViewById(R.id.radioBtnMale);
        radioButtonFemale = findViewById(R.id.radioBtnFemale);
        btnAdd = findViewById(R.id.btnAddPle);
        btnUpdate = findViewById(R.id.btnUpdatePle);
    }

    private void initData() {
        List<Student> studentList = new ArrayList<>();
        //them du lieu thu cong
        studentList.add(new Student("Đinh Văn Hòa","HHTB",Student.male));
        studentList.add(new Student("Đinh Hoà Văn","HHTB",Student.male));
        studentList.add(new Student("Văn Đinh Hòa","HHTB",Student.male));
        studentList.add(new Student("Văn Hòa Đinh","HHTB",Student.male));
        studentList.add(new Student("Hòa Đinh Văn","HHTB",Student.male));

        //khoi tao 3 list chua thong tin can do nen recycle view
        gentleSt = new ArrayList<>();
        nameSt = new ArrayList<>();
        idSt = new ArrayList<>();

        //do du lieu nen
        for(Student student : studentList){
            gentleSt.add(student.getGentleStudent());
            nameSt.add(student.getName());
            idSt.add(student.getIdStudent());
        }
    }

    //trien khai method handleAdd();
    public void handleBtnAdd(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(returnInputOfUser() != null){
                    Student student = returnInputOfUser();
                    adapter.addStudent(student.getName(),student.getIdStudent(),student.getGentleStudent());
                }
                //sau khi add xong thi xoa
                clearInputOfUser();
            }
        });
    }


    //trien khai method handleUpdate();
    public void handleBtnUpdate(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(returnInputOfUser() != null){
                    Student student = returnInputOfUser();
                    adapter.updateSt(currPos,student.getName(),student.getIdStudent(),student.getGentleStudent());
                }
                //lam sang nut add
                makeLightBtnAdd();
                //sau khi update xong thi xoa
                clearInputOfUser();
            }
        });
    }

    //method lay du lieu tu user input
    public Student returnInputOfUser(){
        try {
            String nameUser = editTextName.getText().toString();
            String idUser = editTextId.getText().toString();
            //check xem user nhan chon male or female
            int checkImgUser = radioGroup.getCheckedRadioButtonId();
            String gentleUser = (checkImgUser == R.id.radioBtnMale) ? Student.male : Student.female;

            if(nameUser.isEmpty() || idUser.isEmpty()){
                //neu user nhap thieu
                throw new IllegalArgumentException("Nhap thieu thong tin");
            }
            return new Student(nameUser,idUser,gentleUser);
        }
        catch (IllegalArgumentException ex) {
            Toast.makeText(this, "Nhap thieu thong tin", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public void doSt(int pos) {
        currPos = pos;
        //day du lieu tu recycle view nen edit text
        editTextName.setText(nameSt.get(pos));
        editTextId.setText(idSt.get(pos));

        if(Objects.equals(gentleSt.get(pos), Student.male)){
            radioButtonMale.setChecked(true);
        }
        else{
            radioButtonFemale.setChecked(true);
        }
        makeLightBtnUpdate();//lam snag nut update
    }

    //method lam sang nut add sau khi user nhap xong du lieu dau vao
    public void makeLightBtnAdd() {
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
    }

    //method lam sang nut update sau khi user bam vao card view
    public void makeLightBtnUpdate() {
        btnAdd.setEnabled(false);
        btnUpdate.setEnabled(true);
    }

    //viet method xoa tat ca du lieu tren edit text khi add xong hay update xong
    public void clearInputOfUser(){
        editTextName.setText("");
        editTextId.setText("");
        radioButtonMale.setChecked(true);
    }
}