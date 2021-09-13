package io.test;

/**
 * 简单测试文件
 */

import io.openmessaging.DefaultMessageQueueImpl;
import io.openmessaging.conf.Const;
import io.openmessaging.util.ShowData;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class Test01 {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        DefaultMessageQueueImpl mq = new DefaultMessageQueueImpl();
        mq.append("a", 1001, ByteBuffer.wrap(new byte[]{42, 11, 22, 33}));
        mq.append("a", 1001, ByteBuffer.wrap(new byte[]{17, 11, 22, 73}));
        mq.append("a", 1002, ByteBuffer.wrap(new byte[]{12, 11, 72, 39}));
        mq.append("a", 1001, ByteBuffer.wrap(new byte[]{122, 11, 22, 33}));
        mq.append("b", 1002, ByteBuffer.wrap(new byte[]{12, 1, 22, 33}));
        mq.append("b", 1003, ByteBuffer.wrap(new byte[]{22, 11, 4, 33}));
        mq.append("b", 1001, ByteBuffer.wrap(new byte[]{12, 1, 2, 33}));
        Map map = mq.getRange("a", 1001,1,200);
        map.forEach((key,value)->{
            System.out.print(key + " : ");
            System.out.println(ShowData.dataToString((ByteBuffer)value));
        });
    }
}
