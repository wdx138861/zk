package com.wdx.consumer.controller;

import com.wdx.consumer.bean.Depart;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: wdx
 * @Data: 2020/5/3 17:11
 */
@RestController
@RequestMapping("/depart/")
public class DepartController {

    @Autowired
    private RestTemplate template;

    @Value("${providerServer}")
    private String providerServer;

    @PostMapping
    public boolean saveHandler(@RequestBody Depart depart) {
        return template.postForObject(providerServer, depart, Boolean.class);
    }

    @DeleteMapping("/{id}")
    public void delHandler(@PathVariable("id") Integer id) {
        template.delete(providerServer + id);
    }

    @PutMapping
    public void updateHandler(@RequestBody Depart depart) {
        template.put(providerServer, depart);
    }

    @GetMapping("/{id}")
    public Depart findHandler(@PathVariable("id") Integer id) {
        return template.getForObject(providerServer + id, Depart.class);
    }

    @GetMapping
    public List<Depart> findAllHandler() {
        return template.getForObject(providerServer, List.class);
    }

}
