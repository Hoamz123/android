package com.example.crud_2;


import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud_2.Model.MyAdapter;
import com.example.crud_2.Model.Student;
import com.example.crud_2.Model.onMyItemListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements onMyItemListener {
    private List<Student> studentList;
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private EditText editTextName,editTextId;
    private Button btnAdd,btnUpdate;
    private RadioGroup radioGroup;
    private RadioButton radioButtonMale,radioButtonFemale;
    private int currPos;//luu lai chi so hien tai cua obj dang moon sua
    private SearchView searchView;

    protected void onCreate(Bundle savedInstanceState) {

        // Đinh Văn Hòa (hoamz)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();//du lieu dau vao
        initView();//anh xa qua ID
        adapter = new MyAdapter(this, studentList);
        recyclerView.setAdapter(adapter);

        //tao vach phan cach giua cac item trong recView
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        //giao tiep voi recycle view thong qua adapter
        adapter.setMyItemListener(this);

        //khoa dung man hinh
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //method khi nhan nut addStudent
        handleBtnAdd();
        handleBtnUpdate();
        method_Search();

    }

    private void method_Search() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
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
        searchView = findViewById(R.id.search);
    }

    private void initData() {
        studentList = new ArrayList<>();
        //them du lieu thu cong
        studentList.add(new Student("Đinh Văn Hòa","HHTB",Student.male));
        studentList.add(new Student("Hòa Đinh Văn","CT8B",Student.male));
        studentList.add(new Student("Đinh Văn Hòa","TB",Student.female));
        studentList.add(new Student("Đinh Văn Hòa","HN",Student.female));
        studentList.add(new Student("Đinh Văn Hòa","CT08",Student.female));
    }

    //trien khai method handleAdd();
    public void handleBtnAdd(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(returnInputOfUser() != null){
                    Student student = returnInputOfUser();
                    adapter.addStudent(student);
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
                    if(studentList.get(currPos) != null){
                        adapter.updateSt(currPos,student);
                    }else{
                        clearInputOfUser();
                    }
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
        editTextName.setText(studentList.get(pos).getName());
        editTextId.setText(studentList.get(pos).getIdStudent());

        if(Objects.equals(studentList.get(pos).getGentleStudent(), Student.male)){
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