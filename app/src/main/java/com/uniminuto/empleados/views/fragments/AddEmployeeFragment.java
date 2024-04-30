package com.uniminuto.empleados.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.uniminuto.empleados.R;
import com.uniminuto.empleados.controllers.FirebaseDatabaseController;
import com.uniminuto.empleados.controllers.ProgressDialog;
import com.uniminuto.empleados.controllers.Utils;
import com.uniminuto.empleados.models.FirebaseReponseListener;
import com.uniminuto.empleados.models.PositionModel;
import com.uniminuto.empleados.models.UserModel;

import java.util.ArrayList;

public class AddEmployeeFragment extends Fragment {

    TextInputEditText etName, etLastName;
    TextInputLayout tilName, tilLastName, tilPosition;
    AutoCompleteTextView employeePosition;
    MaterialButton btnRegister;
    String positionEmployee = "";
    ProgressDialog pd;
    FirebaseDatabaseController databaseController;

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

        employeePosition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof PositionModel){
                    PositionModel student = (PositionModel) item;
                    positionEmployee = String.valueOf(student.getPositionId());
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();

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

                if(positionEmployee.isEmpty()) {
                    tilPosition.setErrorEnabled(true);
                    tilPosition.setError("Seleccione un cargo");
                    empty++;
                } else {
                    tilPosition.setErrorEnabled(false);
                    tilPosition.setError("");
                }

                if(empty == 0) {
                    UserModel user = new UserModel();
                    user.setName(name);
                    user.setLastName(lastName);
                    user.setPosition(positionEmployee);

                    pd.showProgressDialog(getActivity(), "Registrando empleado...");
                    databaseController.registerNewUser(user, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            pd.hideProgressDialog();
                            if(error == null) {
                                Utils.showMessageInfo(
                                        "El empleado " + name + " " + lastName + " se agregó exitosamente",
                                        getActivity()
                                );
                                cleanForm();
                            } else {
                                Utils.showMessageInfo(
                                        "Ocurrió un error al intentar registrar el empleado, por favor intente nuevamente más tarde",
                                        getActivity()
                                );
                            }
                        }
                    });
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
        pd = new ProgressDialog();
        databaseController = new FirebaseDatabaseController();
    }
    private void setupPositionData() {
        pd.showProgressDialog(getActivity());
        databaseController.getRegisterPosition(new FirebaseReponseListener<PositionModel>() {
            @Override
            public void onDataReceived(ArrayList<PositionModel> data) {
                pd.hideProgressDialog();
                ArrayAdapter<PositionModel> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
                employeePosition.setAdapter(adapter);
            }

            @Override
            public void onCancelled(Exception error) {
                pd.hideProgressDialog();
            }
        });

    }

    private void cleanForm() {
        etName.setText("");
        etLastName.setText("");
        employeePosition.setText("");
        positionEmployee = "";
    }
}