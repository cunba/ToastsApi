package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Toast;
import com.sanvalero.toastsapi.model.ToastType;
import com.sanvalero.toastsapi.model.dto.ToastDTO;

public interface ToastService extends ProductTemplateService<Toast> {
    List<Toast> findByType(ToastType toastType);

    List<Toast> findByTypes(List<ToastType> toastTypeList);

    Toast addToast(ToastDTO toastDTO) throws NotFoundException;

    Toast deleteToast(int id) throws NotFoundException;

    Toast modifyToast(Toast toast);
}
