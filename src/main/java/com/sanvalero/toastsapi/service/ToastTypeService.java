package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.model.ToastType;

public interface ToastTypeService {
    List<ToastType> findAllTypes();

    ToastType findById(int id);

    ToastType addType(ToastType type);

    ToastType deleteType(int id);

    ToastType modifyType(ToastType type, int id);
}
