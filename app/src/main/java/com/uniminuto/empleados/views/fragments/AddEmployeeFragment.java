package com.uniminuto.empleados.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.uniminuto.empleados.R;
import com.uniminuto.empleados.models.PositionModel;

import java.util.ArrayList;

public class AddEmployeeFragment extends Fragment {

    TextInputEditText etName, etLastName;
    TextInputLayout tilName, tilLastName, tilPosition;
    AutoCompleteTextView employeePosition;
    MaterialButton btnRegister;
    ArrayList<PositionModel> positionArray;

    public static AddEmployeeFragment newInstance(String param1, String param2) {
        AddEmployeeFragment fragment = new AddEmployeeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_employee, container, false);
        initData(view);
        setupPositionData();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String position = employeePosition.getText().toString().trim();
                int empty = 0;

                if(name.isEmpty()) {
                    tilName.setErrorEnabled(true);
                    tilName.setError("Ingrese un nombre");
                    empty++;
                } else {
                    tilName.setErrorEnabled(false);
                    tilName.setError("");
                }

                if(lastName.isEmpty()) {
                    tilLastName.setErrorEnabled(true);
                    tilLastName.setError("Ingrese un apellido");
                    empty++;
                } else {
                    tilLastName.setErrorEnabled(false);
                    tilLastName.setError("");
                }

                if(position.isEmpty()) {
                    tilPosition.setErrorEnabled(true);
                    tilPosition.setError("Seleccione un cargo");
                    empty++;
                } else {
                    tilPosition.setErrorEnabled(false);
                    tilPosition.setError("");
                }

                if(empty == 0) {
                    MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(getActivity());
                    alert.setMessage("El empleado " + name + " " + lastName + " se agregó exitosamente");
                    alert.setPositiveButton("Aceptar", null);
                    alert.show();
                } else {
                    Snackbar.make(v, "Se encontraron campos vacíos", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void initData(View v) {
        etName = v.findViewById(R.id.employeeName);
        etLastName = v.findViewById(R.id.employeeLastName);
        employeePosition = v.findViewById(R.id.employeePosition);
        btnRegister = v.findViewById(R.id.btnRegisterEmployee);
        tilName = v.findViewById(R.id.inputLayout);
        tilLastName = v.findViewById(R.id.inputLayout2);
        tilPosition = v.findViewById(R.id.inputLayout3);
        positionArray = new ArrayList<>();
    }
    private void setupPositionData() {
        positionArray.add(new PositionModel("1", "Administrador de empresas"));
        positionArray.add(new PositionModel("2", "Contador"));
        positionArray.add(new PositionModel("3", "Diseñador"));
        positionArray.add(new PositionModel("4", "Marketing"));
        ArrayAdapter<PositionModel> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, positionArray);
        employeePosition.setAdapter(adapter);
    }
}