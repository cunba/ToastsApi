package com.sanvalero.toastsapi.service;

import java.util.List;

import com.sanvalero.toastsapi.model.TeaType;
import com.sanvalero.toastsapi.repository.TeaTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeaTypeServiceImpl implements TeaTypeService {

    @Autowired
    private TeaTypeRepository ttr;

    @Override
    public List<TeaType> findAllTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TeaType findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TeaType addType(TeaType type) {
        return ttr.save(type);
    }

    @Override
    public TeaType deleteType(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TeaType modifyType(TeaType type, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
