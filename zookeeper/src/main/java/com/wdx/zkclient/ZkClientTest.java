package com.wdx.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

public class ZkClientTest {

    //指定zk集群
    private static final String CLUSTER = "121.42.13.24:2181";

    //指定节点名称
    private static final String PATH = "/mylog";

    public static void main(String[] args) {
        // 创建 zkClient
        ZkClient zkClient = new ZkClient(CLUSTER);
        // 为 zkClient 指定序列化器
        zkClient.setZkSerializer(new SerializableSerializer());

        // ---------------- 创建节点 -----------
        // 指定创建持久节点
        CreateMode mode = CreateMode.PERSISTENT;
        // 指定节点数据内容
        String data = "first log";
        // 创建节点
        String nodeName = zkClient.create(PATH, data, mode);
        System.out.println("新节点名称为： " + nodeName);

        // ---------------- 获取数据内容 -----------
        Object readData = zkClient.readData(PATH);
        System.out.println("节点数据为： " + readData);

        // ---------------- 注册 watcher -----------
        zkClient.subscribeDataChanges(PATH, new IZkDataListener() {
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("节点:" + dataPath + "的数据更新为： " + data);
            }

            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println(dataPath + "的数据内容被删除");
            }
        });

        zkClient.subscribeChildChanges(PATH, (parentPath, currentChilds) -> {

        });

        // ---------------- 更新数据内容 -----------
        zkClient.writeData(PATH, "second log");
        Object updatedData = zkClient.readData(PATH);
        System.out.println("更新后的数据为：" + updatedData);

        // ---------------- 更新数据内容 -----------
        zkClient.writeData(PATH, "third log");
        System.out.println("更新后的数据为：" + zkClient.readData(PATH));

        // ---------------- 删除节点 -----------
        zkClient.deleteRecursive(PATH);

        // ---------------- 判断节点存在性 -----------
        boolean exists = zkClient.exists(PATH);
        System.out.println("节点：" + PATH + "仍然存在吗？" + exists);
    }
}
