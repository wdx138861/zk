package com.wdx.provider.controller;

import com.wdx.provider.bean.Depart;
import com.wdx.provider.service.DepartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wdx
 * @Data: 2020/5/3 16:28
 */
@RestController
@RequestMapping("/depart/")
public class DepartController {

    @Autowired
    private DepartService service;

    @PostMapping
    public boolean saveHandler(@RequestBody Depart depart) {
        return service.saveDepart(depart);
    }

    @DeleteMapping("/{id}")
    public boolean delHandler(@PathVariable("id") Integer id) {
        return service.removeDepartById(id);
    }

    @PutMapping
    public boolean updateHandler(@RequestBody Depart depart) {
        return service.modifyDepart(depart);
    }

    @GetMapping("/{id}")
    public Depart findHandler(@PathVariable("id") Integer id) {
        return service.getDepartById(id);
    }

    @GetMapping
    public List<Depart> findAllHandler() {
        return service.findAllDepartList();
    }
}
