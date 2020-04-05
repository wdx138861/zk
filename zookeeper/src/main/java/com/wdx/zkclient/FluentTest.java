package com.wdx.zkclient;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

public class FluentTest {

    //指定zk集群
    private static final String CLUSTER = "121.42.13.24:2181";

    //指定节点名称
    private static final String PATH = "/mylog";

    public static void main(String[] args) throws Exception  {
        // ---------------- 创建会话 -----------
        // 创建重试策略对象：第 1 秒重试 1 次，最多重试 3 次
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //创建客户端   链式编程
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(CLUSTER).sessionTimeoutMs(15000)
                .connectionTimeoutMs(13000).retryPolicy(retryPolicy).namespace("logs").build();

        //开启客户端
        client.start();

        // 指定要创建和操作的节点，注意，其是相对于/logs 节点的
        String nodePath = "/host";

        // ---------------- 创建节点 -----------
        String nodeName = client.create().forPath(nodePath, "myhost".getBytes());
        System.out.println("新创建的节点名称为：" + nodeName);

        // ---------------- 获取数据内容并注册 watcher -----------
        byte[] data = client.getData().usingWatcher((CuratorWatcher) event -> {
            System.out.println(event.getPath() + "数据内容发生变化");
        }).forPath(nodePath);
        System.out.println("节点的数据内容为：" + new String(data));

        // ---------------- 更新数据内容 -----------
        client.setData().forPath(nodePath, "newHost".getBytes());
        // 获取更新过的数据内容
        byte[] newData = client.getData().forPath(nodePath);
        System.out.println("更新过的数据内容为：" + new String(newData));

        client.delete().forPath(nodePath);

        Stat stat = client.checkExists().forPath(nodePath);
        boolean flag = true;
        if (stat == null) {
            flag = false;
        }
        System.out.println(nodePath + "节点仍存在吗？" + flag);
    }
}
