package com.uniminuto.empleados.models;

import androidx.annotation.NonNull;

public class PositionModel {
    String positionId;
    String positionName;

    public PositionModel() {

    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @NonNull
    @Override
    public String toString() {
        return getPositionName();
    }
}
