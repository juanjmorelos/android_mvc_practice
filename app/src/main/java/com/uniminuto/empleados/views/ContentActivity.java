package com.uniminuto.empleados.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.uniminuto.empleados.R;
import com.uniminuto.empleados.controllers.Utils;
import com.uniminuto.empleados.models.FragmentsController;
import com.uniminuto.empleados.models.PositionModel;
import com.uniminuto.empleados.views.fragments.AddEmployeeFragment;
import com.uniminuto.empleados.views.fragments.AddPositionFragment;
import com.uniminuto.empleados.views.fragments.ViewEmployeesFragment;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {
    FragmentsController fragmentsController;
    ImageButton backButton;
    TextView tvTitle;
    ImageView filter;
    ViewEmployeesFragment viewEmployeesFragment;
    boolean isFiltered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        fragmentsController = Utils.getFragmentFromPutExtra(getIntent().getStringExtra("fragment"));
        backButton = findViewById(R.id.backButton);
        tvTitle = findViewById(R.id.textTitle);
        filter = findViewById(R.id.filter);

        String title = getTitleByFragment(fragmentsController);
        String formatTitle = String.format(getString(R.string.text_fragment_name), title);
        tvTitle.setText(formatTitle);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContent, getFragmentByController(fragmentsController))
                .commit();

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goBack();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFiltered) {
                    showDialogWhenIsFiltered(v);
                    return;
                }
                showFilterDialog(v);
            }
        });
    }

    private Fragment getFragmentByController(FragmentsController fragmentsController) {
        switch (fragmentsController) {
            case VIEW_EMPLOYEES:
                filter.setVisibility(View.VISIBLE);
                viewEmployeesFragment = new ViewEmployeesFragment();
                return viewEmployeesFragment;
            case ADD_EMPLOYEE:
                return new AddEmployeeFragment();
            case ADD_POSITION:
                return new AddPositionFragment();
        }
        return new ViewEmployeesFragment();
    }

    private String getTitleByFragment(FragmentsController fragmentsController) {
        switch (fragmentsController) {
            case VIEW_EMPLOYEES:
                return "Lista de empleados";
            case ADD_EMPLOYEE:
                return "Agregar empleado";
            case ADD_POSITION:
                return "Agregar cargo";
        }
        return "";
    }

    private void goBack() {
        Intent i = new Intent(ContentActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void showFilterDialog(View v) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.filter_dialog_view, null);

        builder.setView(view);

        AutoCompleteTextView atvPosition = view.findViewById(R.id.employeePosition);

        setupPositionData(atvPosition);

        builder.setTitle("Filtrar empleado por cargo");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String position = atvPosition.getText().toString().trim();
                if(position.isEmpty()) {
                    dialog.dismiss();
                    Snackbar.make(v, "Selecciona el cargo a filtrar", Snackbar.LENGTH_SHORT)
                            .setAction("Filtrar", new View.OnClickListener() {
                                @Override
                                public void onClick(View vi) {
                                    showFilterDialog(v);
                                }
                            })
                            .show();
                    return;
                }
                viewEmployeesFragment.filterEmployees(position);
                isFiltered = true;
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void setupPositionData(AutoCompleteTextView employeePosition) {
        ArrayList<PositionModel> positionArray = new ArrayList<>();
        positionArray.add(new PositionModel("1", "Administrador de empresas"));
        positionArray.add(new PositionModel("2", "Contador"));
        positionArray.add(new PositionModel("3", "Diseñador"));
        positionArray.add(new PositionModel("4", "Marketing"));
        ArrayAdapter<PositionModel> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, positionArray);
        employeePosition.setAdapter(adapter);
    }

    private void showDialogWhenIsFiltered(View v) {
        final String[] opciones = {"Nuevo filtro", "Limpiar filtro"};
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Seleccione una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        showFilterDialog(v);
                        break;
                    case 1:
                        viewEmployeesFragment.cleanFilter();
                        isFiltered = false;
                        break;
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }


}