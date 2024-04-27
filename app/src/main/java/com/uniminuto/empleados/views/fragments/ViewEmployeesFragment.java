package com.uniminuto.empleados.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uniminuto.empleados.R;
import com.uniminuto.empleados.controllers.EmployeeRecyclerAdapter;
import com.uniminuto.empleados.models.UserModel;

import java.util.ArrayList;

public class ViewEmployeesFragment extends Fragment {
    ArrayList<UserModel> employeeArraylist;
    EmployeeRecyclerAdapter adapter;
    RecyclerView recycler;

    public static ViewEmployeesFragment newInstance(String param1, String param2) {
        ViewEmployeesFragment fragment = new ViewEmployeesFragment();
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
        View view = inflater.inflate(R.layout.fragment_view_employees, container, false);
        initData(view);
        setupUsersData();
        return view;
    }

    private void initData(View view) {
        employeeArraylist = new ArrayList<>();
        adapter = new EmployeeRecyclerAdapter(employeeArraylist, getActivity());
        recycler = view.findViewById(R.id.recyclerViewUserList);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupUsersData() {
        employeeArraylist.add(new UserModel("Maria", "Perez", "Administrador de empresas"));
        employeeArraylist.add(new UserModel("Claudia", "Sanchez", "Administrador de empresas"));
        employeeArraylist.add(new UserModel("Juan", "García", "Contador"));
        employeeArraylist.add(new UserModel("Ana", "López", "Marketing"));
        employeeArraylist.add(new UserModel("Carlos", "Martínez", "Diseñador"));
        employeeArraylist.add(new UserModel("Laura", "Rodríguez", "Contador"));
        employeeArraylist.add(new UserModel("Pedro", "Hernández", "Marketing"));
        employeeArraylist.add(new UserModel("Sofía", "Díaz", "Diseñador"));
        employeeArraylist.add(new UserModel("Luis", "Gómez", "Administrador de empresas"));
    }

    public void filterEmployees(String filtro) {
        ArrayList<UserModel> filteredList = new ArrayList<>();
        for (UserModel employee : employeeArraylist) {
            if (employee.getPosition().contains(filtro)) {
                filteredList.add(employee);
            }
        }

        // Actualiza el RecyclerView con la lista filtrada
        adapter.updateData(filteredList);
    }

    public void cleanFilter() {
        adapter.updateData(employeeArraylist);
    }
}