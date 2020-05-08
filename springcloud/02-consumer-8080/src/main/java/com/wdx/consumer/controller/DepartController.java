package com.wdx.consumer.controller;

import com.wdx.consumer.bean.Depart;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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

    @Autowired
    private DiscoveryClient client;

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

    @GetMapping("discovery")
    public Object discoverHandler() {
        List<String> services = client.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = client.getInstances(service);
            for (ServiceInstance instance : instances) {
                String serviceId = instance.getServiceId();
                URI uri = instance.getUri();
                String host = instance.getHost();
                int port = instance.getPort();
                System.out.println(serviceId + "：" + uri);
                System.out.println(host + "；" + port);
            }
        }
        return services;
    }

}
