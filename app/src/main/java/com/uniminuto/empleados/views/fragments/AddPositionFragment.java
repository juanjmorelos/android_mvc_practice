package com.uniminuto.empleados.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.uniminuto.empleados.R;

public class AddPositionFragment extends Fragment {
    TextInputLayout tilPosition;
    TextInputEditText etPosition;
    MaterialButton btnRegisterPosition;

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

        btnRegisterPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String position = etPosition.getText().toString().trim();
                if(position.isEmpty()) {
                    tilPosition.setErrorEnabled(true);
                    tilPosition.setError("Ingrese un cargo");
                    return;
                }
                tilPosition.setErrorEnabled(false);
                tilPosition.setError("");

                MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(getActivity());
                alert.setMessage("El cargo " + position.toLowerCase() + " se agregó exitosamente");
                alert.setPositiveButton("Aceptar", null);
                alert.show();

            }
        });
        return view;
    }
}