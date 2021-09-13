package io.openmessaging.index;

import io.openmessaging.conf.Const;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 *
 * 这个类提供了索引结构
 * 以及两个方法：
 *      将索引写入 index
 *      将索引从 index 中读出来
 *
 *
 */
public class IndexMap {


    /**
     * 索引结构：
     *
     * topic - Map
     *          queue - Map
     *                  offset - Map
     *                          开始位置 - 字节长度
     *
     */
    public static ConcurrentHashMap<String,
            ConcurrentHashMap<Integer,
                    ConcurrentHashMap<Long,
                            Long[]>>> map;

    /**
     * 实现结束保存
     */
    public static Thread save = new Thread(

            () -> {
                int i = 10;
                while(i > 3){
                    i = Thread.currentThread().getThreadGroup().activeCount();
                }
                try {
                    IndexMap.write();
                    System.out.println("INDEX 保存成功");
                } catch (IOException e) {
                    System.out.println("INDEX 保存失败");
                }
            }
    );


    /**
     *
     * 将缓存索引写入磁盘文件
     *
     * @throws IOException
     */
    public static void write() throws IOException {
        FileOutputStream fop = new FileOutputStream(Const.BASE_PATH + Const.INDEX);
        ObjectOutputStream oop = new ObjectOutputStream(fop);
        FileChannel fc = fop.getChannel();
        fc.write(ByteBuffer.wrap(IndexMap.map.toString().getBytes()));
        fop.close();
        fc.close();
    }


    /**
     *
     * 从磁盘读取索引文件
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void get() throws IOException, ClassNotFoundException {
        FileInputStream fip = new FileInputStream(Const.BASE_PATH + Const.INDEX);
        ObjectInputStream oip = new ObjectInputStream(fip);
        IndexMap.map = (ConcurrentHashMap<String,
                ConcurrentHashMap<Integer,
                        ConcurrentHashMap<Long,
                                Long[]>>>)oip.readObject();
        fip.close();
        oip.close();
    }

}
