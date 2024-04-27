package com.uniminuto.empleados.views;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.uniminuto.empleados.R;
import com.uniminuto.empleados.controllers.Utils;
import com.uniminuto.empleados.models.FragmentsController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CardView viewEmployee, addEmployee, addPosition;
    TextView lastLogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        viewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goToFragment(FragmentsController.VIEW_EMPLOYEES, MainActivity.this);
            }
        });

        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goToFragment(FragmentsController.ADD_EMPLOYEE, MainActivity.this);
            }
        });

        addPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goToFragment(FragmentsController.ADD_POSITION, MainActivity.this);
            }
        });

        getLastLogged();

    }

    public void initComponents(){
        viewEmployee = findViewById(R.id.viewEmployee);
        addEmployee = findViewById(R.id.addEmployee);
        addPosition = findViewById(R.id.addPosition);
        lastLogged = findViewById(R.id.lastLogin);
    }

    public void getLastLogged() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);
        String lastLog = String.format(getString(R.string.text_subtitle), formattedDate);
        lastLogged.setText(lastLog);
    }
}