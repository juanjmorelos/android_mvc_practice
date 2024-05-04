package com.uniminuto.empleados.controllers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniminuto.empleados.models.FirebaseReponseListener;
import com.uniminuto.empleados.models.PositionModel;
import com.uniminuto.empleados.models.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDatabaseController {
    private DatabaseReference mDatabase;

    public FirebaseDatabaseController() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void registerNewUser(UserModel user, DatabaseReference.CompletionListener listener) {
        DatabaseReference usersRef = mDatabase.child("employee");
        String userId = usersRef.push().getKey();
        usersRef.child(userId).setValue(user, listener);
    }

    public void registerNewPosition(PositionModel position, DatabaseReference.CompletionListener listener) {
        DatabaseReference positionRef = mDatabase.child("position");
        String positionId = positionRef.push().getKey();
        positionRef.child(positionId).setValue(position.getPositionName(), listener);
    }

    public void getRegisterPosition(FirebaseReponseListener<PositionModel> listener) {
        mDatabase.child("position").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot snapshot) {
                ArrayList<PositionModel> positionList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PositionModel positionModel = new PositionModel();
                    positionModel.setPositionName(dataSnapshot.getValue().toString());
                    positionModel.setPositionId(dataSnapshot.getKey());
                    positionList.add(positionModel);
                }
                listener.onDataReceived(positionList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onCancelled(e);
            }
        });
    }

    public void fetchEmployeeData(FirebaseReponseListener<UserModel> listener) {
        DatabaseReference employeeRef = mDatabase.child("employee");
        DatabaseReference positionRef = mDatabase.child("position");

        employeeRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot employeeSnapshot) {
                positionRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot positionSnapshot) {
                        Map<String, String> positionMap = new HashMap<>();
                        for (DataSnapshot positionData : positionSnapshot.getChildren()) {
                            String positionId = positionData.getKey();
                            String positionName = positionData.getValue(String.class);
                            positionMap.put(positionId, positionName);
                        }

                        ArrayList<UserModel> employees = new ArrayList<>();
                        for (DataSnapshot employeeData : employeeSnapshot.getChildren()) {
                            String name = employeeData.child("name").getValue(String.class);
                            String lastName = employeeData.child("lastName").getValue(String.class);
                            String positionId = employeeData.child("position").getValue(String.class);

                            String positionName = positionMap.get(positionId);

                            UserModel employee = new UserModel();
                            employee.setName(name);
                            employee.setLastName(lastName);
                            employee.setPosition(positionName);
                            employees.add(employee);
                        }
                        listener.onDataReceived(employees);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onCancelled(e);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onCancelled(e);
            }
        });
    }
}
