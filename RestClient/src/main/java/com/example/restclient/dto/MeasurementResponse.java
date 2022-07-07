package com.example.restclient.dto;

import java.util.List;

public class MeasurementResponse {
    private List<MeasurementsDTO> measurementsDTOList;


    public List<MeasurementsDTO> getMeasurementsDTOList() {
        return measurementsDTOList;
    }

    public void setMeasurementsDTOList(List<MeasurementsDTO> measurementsDTOList) {
        this.measurementsDTOList = measurementsDTOList;
    }
}
