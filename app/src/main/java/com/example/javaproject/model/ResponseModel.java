package com.example.javaproject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResponseModel {
    private Object message;
    private String status;

    public ResponseModel(Object message, String status) {
        this.message = message;
        this.status = status;
    }
}
