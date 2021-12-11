package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.exception.NotFoundException;
import com.sanvalero.toastsapi.model.Tea;
import com.sanvalero.toastsapi.model.TeaType;
import com.sanvalero.toastsapi.model.dto.TeaDTO;

public interface TeaService extends ProductTemplateService<Tea> {
    List<Tea> findByType(TeaType teaType);

    List<Tea> findByTypes(List<TeaType> teaTypeList);

    Tea addTea(TeaDTO teaDTO) throws NotFoundException;

    Tea deleteTea(int id) throws NotFoundException;

    Tea modifyTea(Tea tea);
}
