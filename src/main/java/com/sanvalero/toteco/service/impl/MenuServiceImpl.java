package com.sanvalero.toteco.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanvalero.toteco.exception.NotFoundException;
import com.sanvalero.toteco.model.Menu;
import com.sanvalero.toteco.repository.MenuRepository;
import com.sanvalero.toteco.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository mr;

    @Override
    public Menu findById(UUID id) throws NotFoundException {
        return mr.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Menu> findAll() {
        return mr.findAll();
    }

    @Override
    public Menu save(Menu menu) {
        return mr.save(menu);
    }

    @Override
    public void update(Menu menu) {
        mr.save(menu);
    }

    @Override
    public void delete(Menu menu) {
        mr.delete(menu);
    }

    @Override
    public void deleteAll() {
        mr.deleteAll();
    }
}
