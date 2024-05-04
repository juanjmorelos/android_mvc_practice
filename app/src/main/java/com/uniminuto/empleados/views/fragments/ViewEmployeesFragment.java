package com.uniminuto.empleados.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uniminuto.empleados.R;
import com.uniminuto.empleados.controllers.EmployeeRecyclerAdapter;
import com.uniminuto.empleados.controllers.FirebaseDatabaseController;
import com.uniminuto.empleados.controllers.ProgressDialog;
import com.uniminuto.empleados.models.FirebaseReponseListener;
import com.uniminuto.empleados.models.UserModel;

import java.util.ArrayList;

public class ViewEmployeesFragment extends Fragment {
    ArrayList<UserModel> employeeArraylist;
    EmployeeRecyclerAdapter adapter;
    RecyclerView recycler;
    ProgressDialog pd;
    FirebaseDatabaseController firebaseDatabaseController;
    TextView emptyText;

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
        firebaseDatabaseController = new FirebaseDatabaseController();
        pd = new ProgressDialog();
        emptyText = view.findViewById(R.id.textEmployeeEmpty);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupUsersData() {
        pd.showProgressDialog(getActivity());
        firebaseDatabaseController.fetchEmployeeData(new FirebaseReponseListener<UserModel>() {
            @Override
            public void onDataReceived(ArrayList<UserModel> data) {
                pd.hideProgressDialog();
                employeeArraylist = data;
                adapter.updateData(employeeArraylist);
                showEmptyMessage(employeeArraylist.isEmpty());
            }

            @Override
            public void onCancelled(Exception error) {

            }
        });
    }

    public void filterEmployees(String filtro) {
        ArrayList<UserModel> filteredList = new ArrayList<>();
        for (UserModel employee : employeeArraylist) {
            if (employee.getPosition().contains(filtro)) {
                filteredList.add(employee);
            }
        }
        adapter.updateData(filteredList);
        showEmptyMessage(filteredList.isEmpty(), "No se encontraron empleados registrados con el cargo: " + filtro.toLowerCase());
    }

    public void cleanFilter() {
        adapter.updateData(employeeArraylist);
        showEmptyMessage(employeeArraylist.isEmpty());
    }

    private void showEmptyMessage(boolean show, String message) {
        if(show) {
            recycler.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(message);
            return;
        }
        recycler.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);
    }
    private void showEmptyMessage(boolean show) {
        if(show) {
            recycler.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
            return;
        }
        recycler.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);
    }
}