package com.wdx.dubbo.server;

import com.wdx.dubbo.balance.RandomLoadBalance;
import com.wdx.dubbo.constant.ZKConstant;
import java.util.List;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZKServiceDiscovery implements ServiceDiscovery {

    private CuratorFramework client;

    private List<String> serviceList;

    public ZKServiceDiscovery() {
        client = CuratorFrameworkFactory.builder()
                .connectString(ZKConstant.ZK_CLUSTER)
                .connectionTimeoutMs(10000)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        client.start();
    }

    @Override
    public String discovery(String serviceName) throws Exception {
        String servicePath = ZKConstant.ZK_DUBBO_ROOT_PATH + "/" + serviceName;
        serviceList = client.getChildren()
                .usingWatcher( (CuratorWatcher)  event -> {
                    serviceList = client.getChildren().forPath(servicePath);
                }).forPath(servicePath);
        return new RandomLoadBalance().choose(serviceList);
    }
}
