package io.openmessaging;

import io.openmessaging.conf.Const;
import io.openmessaging.index.IndexMap;
import io.openmessaging.util.ReadData;
import io.openmessaging.util.WriteData;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这是一个简单的基于内存的实现，以方便选手理解题意；
 * 实际提交时，请维持包名和类名不变，把方法实现修改为自己的内容；
 */
public class DefaultMessageQueueImpl extends MessageQueue {

    /**
     * 记录当前文件末尾位置，用于写入和记录索引
     */
    volatile long tail = -1;

    /**
     * 打开写入DATA通道，向其中追加data
     */
    FileOutputStream fos_data = new FileOutputStream(Const.BASE_PATH + Const.DATA, true);

    /**
     * 使用 nio
     */
    volatile FileChannel fc = fos_data.getChannel();

    /**
     * 当索引改变后，确保写入data中，否则位置可能发生互换
     */
    Lock rlock = new ReentrantLock();


    public DefaultMessageQueueImpl() throws IOException, ClassNotFoundException {
        this.init();
//        IndexMap.save.start();
    }

    /**
     *
     * 初始化索引
     * 初始化尾指针
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void init() throws IOException, ClassNotFoundException {

        /**
         * 从索引文件中获取索引
         * 若为空，则新建一个索引 map
         */
        try {
            IndexMap.get();
            System.out.println("初始化索引成功【来自index】");
        } catch (Exception e) {
            IndexMap.map = new ConcurrentHashMap<>();
            System.out.println("初始化索引成功【new ConcurrentHashMap()】");
        }

        /**
         * 获取文件末尾
         */
        File index = new File(Const.BASE_PATH + Const.INDEX);
        File data = new File(Const.BASE_PATH + Const.DATA);
        index.createNewFile();
        data.createNewFile();
        this.tail = data.length();
//        System.out.println("尾指针初始化");
    }

    @Override
    public long append(String topic, int queueId, ByteBuffer data){

        /**
         * 两个if为非空判断结构
         *
         * topic 初始化容量 26（max = 100）
         */
        if(!IndexMap.map.containsKey(topic)){
            IndexMap.map.put(topic, new ConcurrentHashMap<>());
        }
        if(!IndexMap.map.get(topic).containsKey(queueId)){
            IndexMap.map.get(topic).put(queueId, new ConcurrentHashMap<>());
        }

        long size = IndexMap.map.get(topic).get(queueId).mappingCount();
        IndexMap.map.get(topic).get(queueId).put(size, new Long[2]);


        IndexMap.map.get(topic).get(queueId).get(size)[1] = (long)data.limit();

        //修改索引
        IndexMap.map.get(topic).get(queueId).get(size)[0] = tail;

        //写入 data 文件，更新 tail
        try {
            WriteData.Write(fc, data);
            this.tail += data.limit();
//            System.out.println("写入 DATA 成功");
        } catch (IOException e) {
            System.out.println("写入 DATA 异常");
        }

//        System.out.println(size);

        return size;
    }

    @Override
    public Map<Integer, ByteBuffer> getRange(String topic, int queueId, long offset, int fetchNum) {

        Map<Integer, ByteBuffer> ret = new HashMap<>();

        if(!IndexMap.map.containsKey(topic)){
            System.out.println("没有此 topic : " + topic);
            return ret;
        }
        if(!IndexMap.map.get(topic).containsKey(queueId)){
            System.out.println("topic : " + topic + "下没有此 queueId ： " + queueId);
            return ret;
        }

        long size = IndexMap.map.get(topic).get(queueId).mappingCount();

        for(int i = 0; i < fetchNum; i ++){
            if(!IndexMap.map.get(topic).get(queueId).containsKey(i + offset)){
                break;
            }

            long start = IndexMap.map.get(topic).get(queueId).get(i + offset)[0];
            int length = (int)(IndexMap.map.get(topic).get(queueId).get(i + offset)[1] + 0);

            FileInputStream  fis = null;

            try {
                fis = new FileInputStream(Const.BASE_PATH + Const.DATA);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //应该不会报异常
            }

            FileChannel fic = fis.getChannel();

            try {
                ByteBuffer bf = ReadData.read(fic, start, length);
                ret.put(ret.size(), bf);
            } catch (IOException e) {
                System.out.println("数据读取失败");
                //应该不会报异常
            }
        }

        return ret;
    }
}
