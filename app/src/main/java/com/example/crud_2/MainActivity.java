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
    //khai bao nhung thu xuat hien tren giao dien chinh va can thao tac voi no
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private Button btnAddStudent,btnUpdateStudent;//hai nut them va sua
    private RadioGroup radioGroup;
    private RadioButton btnMale,btnFemale;//tich chon nam hay nu
    private EditText editTextName,editTextId;//hai cai edit text
    private int curPsiton;//luuu lai vi tri can phai thay thoi tren recycle View
    private List<String> nameList,idList,gentleList;


    protected void onCreate(Bundle savedInstanceState) {
        // Đinh Văn Hòa (hoamz)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();//anh xa cac thanh phan theo ID
        adapter = new MyAdapter(nameList,idList,gentleList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter.setMyItemListener(this);

        //goi ham xu ly tac vu them student
        handleBtnAdd();
        //goi ham xu ly tac vu update student
        handleUpdate();
    }


    //anh xa cac thanh phan tren giao dien thong qua ID
    private void initView() {
        recyclerView = findViewById(R.id.rcView);
        editTextId = findViewById(R.id.edTextId);
        editTextName = findViewById(R.id.edTextName);
        radioGroup = findViewById(R.id.radioGr);
        btnFemale = findViewById(R.id.radioBtnGirl);
        btnMale = findViewById(R.id.radioBtnBoy);
        btnAddStudent = findViewById(R.id.btnAddPle);
        btnUpdateStudent = findViewById(R.id.btnUpdatePle);
    }

    private void initData() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Dinh Van Hoa","CT08",Student.male));
        studentList.add(new Student("Dinh Van Hoa","CT08",Student.female));
        studentList.add(new Student("Dinh Van Hoa","CT08",Student.male));
        studentList.add(new Student("Dinh Van Hoa","CT08",Student.male));

        nameList = new ArrayList<>();
        idList = new ArrayList<>();
        gentleList = new ArrayList<>();
        //chuyen du lieu sang list
        for(Student student : studentList){
            nameList.add(student.getName());
            idList.add(student.getIdStudent());
            gentleList.add(student.getGentleStudent());
        }
    }

    private void handleBtnAdd() {
        btnAddStudent.setOnClickListener(v -> {
            if(returnDataFromInput() != null) {
                Student student = returnDataFromInput();
                adapter.addStudent(student.getName(), student.getIdStudent(), student.getGentleStudent());
                clearInput();
            }
        });
    }

    private void handleUpdate() {
        btnUpdateStudent.setOnClickListener(v -> {
            if(returnDataFromInput() != null) {
                Student student = returnDataFromInput();//tra ve mot sinh vien da lay duoc tren 2 cai edtit text va 1 cai gioi tinh lay duoc tren radio group
                adapter.updateStudent(curPsiton, student.getName(), student.getIdStudent(), student.getGentleStudent());
                clearInput();
                //sau khi update xong vao recycle view
                makeLightBtnAdd();//lam sang nut add
            }
        });
    }

    //viet mot method de lay du lieu nhap vaop gom 2 cai edit text va mot cai radio group
    public Student returnDataFromInput(){
        try {
            String nameStudent = editTextName.getText().toString();
            String idStudent = editTextId.getText().toString();
            int imgCheck = radioGroup.getCheckedRadioButtonId();
            String gentleStudent = (imgCheck == R.id.radioBtnBoy) ? Student.male : Student.female;
            if(nameStudent.isEmpty() || idStudent.isEmpty()){
                throw new IllegalArgumentException("Nhap thieu thong tin");
            }
            return new Student(nameStudent,idStudent,gentleStudent);
        }
        catch (IllegalArgumentException ignored) {
            Toast.makeText(MainActivity.this, "Nhap thieu thong tin", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    //clear edit text ve trong sau khi them
    public void clearInput(){
        editTextName.setText("");
        editTextId.setText("");
        btnMale.setChecked(true);
    }

    //lam sang nut aâ va tat nut update
    public void makeLightBtnAdd(){
        btnAddStudent.setEnabled(true);
        btnUpdateStudent.setEnabled(false);
    }
    //lam sang nut update va tat nut add student
    public void makeLightBtnUpdate(){
        btnAddStudent.setEnabled(false);
        btnUpdateStudent.setEnabled(true);
    }

    @Override
    //method khi nhap vao card view
    public void doSt(int position) {
        curPsiton = position;
        //do du lieu tu trong cardView nen edit text
        editTextName.setText(nameList.get(position));
        editTextId.setText(idList.get(position));
        if (Objects.equals(gentleList.get(position), Student.male))
            btnMale.setChecked(true);
        else btnFemale.setChecked(true);
        makeLightBtnUpdate();//lam sang nut update
    }
}