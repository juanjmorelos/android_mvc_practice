package com.uniminuto.empleados.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniminuto.empleados.R;
import com.uniminuto.empleados.models.UserModel;

import java.util.ArrayList;

public class EmployeeRecyclerAdapter extends RecyclerView.Adapter<EmployeeRecyclerAdapter.ViewHolder> {
    ArrayList<UserModel> items;
    Context context;

    public EmployeeRecyclerAdapter(ArrayList<UserModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeRecyclerAdapter.ViewHolder holder, int position) {
        UserModel employee = items.get(position);
        String employeeName = String.format(context.getString(R.string.text_item_employee_complete_name),
                employee.getName(), employee.getLastName());
        String employeePosition = String.format(context.getString(R.string.text_item_employee_position),
                employee.getPosition());

        holder.tvEmployeeName.setText(employeeName);
        holder.tvPosition.setText(employeePosition);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(ArrayList<UserModel> filteredList) {
        this.items = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmployeeName, tvPosition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmployeeName = itemView.findViewById(R.id.textName);
            tvPosition = itemView.findViewById(R.id.position);
        }
    }
}
