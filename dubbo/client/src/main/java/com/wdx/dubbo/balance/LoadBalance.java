package com.wdx.dubbo.balance;

import java.util.List;

public interface LoadBalance {

    String choose(List<String> serviceList);
}
