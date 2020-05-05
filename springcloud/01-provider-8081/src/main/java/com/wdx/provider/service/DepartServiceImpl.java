package com.wdx.provider.service;

import com.wdx.provider.bean.Depart;
import com.wdx.provider.repository.DepartRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wdx
 * @Data: 2020/5/3 16:13
 */
@Service
public class DepartServiceImpl implements DepartService {

    @Autowired
    private DepartRepository repository;

    @Override
    public boolean saveDepart(Depart depart) {
        Depart obj = repository.save(depart);
        return obj != null ? true : false;
    }

    @Override
    public boolean removeDepartById(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyDepart(Depart depart) {
        Depart obj = repository.save(depart);
        return obj != null ? true : false;
    }

    @Override
    public Depart getDepartById(Integer id) {
        if (repository.existsById(id)) {
            return repository.getOne(id);
        }
        Depart depart = new Depart();
        depart.setName("no this depart");
        return depart;
    }

    @Override
    public List<Depart> findAllDepartList() {
        return repository.findAll();
    }
}
