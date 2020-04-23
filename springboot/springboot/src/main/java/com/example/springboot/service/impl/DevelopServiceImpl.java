package com.example.springboot.service.impl;

import com.example.springboot.service.ISomeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author wdx
 */
@Service
@Profile("dev")
public class DevelopServiceImpl implements ISomeService {

    @Override
    public String send() {
        return "开发端";
    }
}
