package com.uniminuto.empleados.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.uniminuto.empleados.R;
import com.uniminuto.empleados.controllers.FirebaseDatabaseController;
import com.uniminuto.empleados.controllers.ProgressDialog;
import com.uniminuto.empleados.controllers.Utils;
import com.uniminuto.empleados.models.PositionModel;

public class AddPositionFragment extends Fragment {
    TextInputLayout tilPosition;
    TextInputEditText etPosition;
    MaterialButton btnRegisterPosition;
    ProgressDialog pd;

    public static AddPositionFragment newInstance(String param1, String param2) {
        AddPositionFragment fragment = new AddPositionFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_position, container, false);
        tilPosition = view.findViewById(R.id.textLayout);
        etPosition = view.findViewById(R.id.position);
        btnRegisterPosition = view.findViewById(R.id.registerPosition);
        pd = new ProgressDialog();

        btnRegisterPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String position = etPosition.getText().toString().trim();
                pd.showProgressDialog(getActivity(), "Registrando nuevo cargo...");

                if(position.isEmpty()) {
                    tilPosition.setErrorEnabled(true);
                    tilPosition.setError("Ingrese un cargo");
                    return;
                }
                tilPosition.setErrorEnabled(false);
                tilPosition.setError("");

                FirebaseDatabaseController databaseController = new FirebaseDatabaseController();
                PositionModel positionModel = new PositionModel();
                positionModel.setPositionName(position);

                databaseController.registerNewPosition(positionModel, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        pd.hideProgressDialog();
                        cleanForm();
                        if(error == null) {
                            Utils.showMessageInfo(
                                    "El cargo " + position.toLowerCase() + " se agregó exitosamente",
                                    getActivity()
                            );
                        } else {
                            Utils.showMessageInfo(
                                    "Ocurrió un error al intentar registrar el cargo",
                                    getActivity()
                            );
                        }
                    }
                });

            }
        });
        return view;
    }

    private void cleanForm() {
        etPosition.setText("");
    }
}