package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.model.TeaType;

public interface TeaTypeService {
    List<TeaType> findAllTypes();

    TeaType findById(int id);

    TeaType addType(TeaType type);

    TeaType deleteType(int id);

    TeaType modifyType(TeaType type, int id);
}
