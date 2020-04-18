package com.wdx.dubbo.balance;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance implements LoadBalance {
    @Override
    public String choose(List<String> serviceList) {
        int index = new Random().nextInt(serviceList.size());
        return serviceList.get(index);
    }
}
