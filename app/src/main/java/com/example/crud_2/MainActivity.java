package com.example.crud_2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud_2.Model.MyAdapter;
import com.example.crud_2.Model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private List<String> nameList,idList,gentleList;
    private RecyclerView recyclerView;
    private EditText edText1,edText2;
    private RadioGroup radioGroup;
    private RadioButton radioButtonMale,radioButtonFemale;
    private Button btnAdd,btnUpdate;
    private MyAdapter myAdapter;
    private int currPositon;//vi tri hien tai
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Đinh Văn Hòa (hoamz)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        myAdapter = new MyAdapter(idList,nameList,gentleList);
        myAdapter.setOnMyItemListener(new MyAdapter.onMyItemListener() {
            @Override
            public void doSt(int position) {
                currPositon = position;//gan vi tri hien tai
                //hien thi noi dung nen edText1,2
                edText1.setText(myAdapter.listName.get(position));
                edText2.setText(myAdapter.listID.get(position));
                if(Objects.equals(myAdapter.listGentle.get(position), Student.male)){
                    radioButtonMale.setChecked(true);
                }
                else radioButtonFemale.setChecked(true);
                //lam sang nut update
                BtnUpdateLight();
            }
        });
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));//set layout cho recyclerView
        handleAddClick();
        handleUpdateClick();
    }

    //reset du lieu o text name va text ID ve null (ca radiobutton mac dinh ve male
    public void clearEditText(){
        edText1.setText("");
        edText2.setText("");
        radioButtonMale.setChecked(true);//sau khi goi ham nay thi thanh nhap ten va thanh nhap ID se reset,nut chon mac dinh dat vao male
    }
    private void handleAddClick() {
        btnAdd.setOnClickListener(v -> {
            Student student = pickDataFromForm();
            myAdapter.addStudent(student.getName(),student.getIdStudent(),student.getGentleStudent());
            clearEditText();
        });
    }

    //method update dulieu nen form
    private void handleUpdateClick(){
        btnUpdate.setOnClickListener(v -> {
            Student student = pickDataFromForm();
            myAdapter.updateStudent(currPositon,student.getName(),student.getIdStudent(),student.getGentleStudent());
            clearEditText();
            BtnAddLight();//lam sang nut add
        });
    }


    private Student pickDataFromForm() {
        //lay du lieu tren 2 cai edit text va tren cai radio group->image phu hop vs gioi tinh
        String name = edText1.getText().toString();
        String ID = edText2.getText().toString();
        int check = radioGroup.getCheckedRadioButtonId();
        //dung toan tu 3 ngoi
        String gentleStudent = (check == R.id.radioBtnBoy) ? Student.male : Student.female;
        return new Student(name,ID,gentleStudent);
    }

    private void initView() {
        //anh xa nhung thu tren main chinh
        recyclerView = findViewById(R.id.rcView);
        edText1 = findViewById(R.id.edTextName);
        edText2 = findViewById(R.id.edTextId);
        radioGroup = findViewById(R.id.radioGr);
        radioButtonMale = findViewById(R.id.radioBtnBoy);
        radioButtonFemale = findViewById(R.id.radioBtnGirl);
        btnAdd = findViewById(R.id.btnAddPle);
        btnUpdate = findViewById(R.id.btnUpdatePle);
    }

    //method de nut add tat va nut update sang
    private void BtnUpdateLight(){
        btnAdd.setEnabled(false);
        btnUpdate.setEnabled(true);
    }
    //method de nut add sang va nut update tat
    private void BtnAddLight(){
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(false);
    }

    private void initData() {
        //ham nhan du lieu
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("hoamz","CT08",Student.female));
        studentList.add(new Student("Anh","CT08",Student.male));
        studentList.add(new Student("Ngoc","CT08",Student.female));
        studentList.add(new Student("Linh","CT08",Student.female));
        studentList.add(new Student("Tuan","CT08",Student.male));
        studentList.add(new Student("Hoa","CT08",Student.female));

        //tao 3 list tuong ung voi ten,ID,gioi tinh
        nameList = new ArrayList<>();
        idList = new ArrayList<>();
        gentleList = new ArrayList<>();

        for(Student st : studentList){
            //do du lieu vao ba list
            nameList.add(st.getName());
            idList.add(st.getIdStudent());
            gentleList.add(st.getGentleStudent());
        }
    }

}