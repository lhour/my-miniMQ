package io.test;

import io.openmessaging.DefaultMessageQueueImpl;
import io.openmessaging.util.ShowData;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class WriteThreadTest {

    static String s = "asasdadsafasfasfsdgsadfsfefasdasdfsagafwefadssdgsadfsd";
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        DefaultMessageQueueImpl mq = new DefaultMessageQueueImpl();

        for(int i = 0; i < 50; i ++){
            String temp = i + "";
            new Thread(
                    () -> {
                        String name = Thread.currentThread().getName();
                        long now = System.currentTimeMillis();
                        for(int j = 0; j < 10000; j ++) {
                            mq.append(temp,
                                    j % 5000,
                                    ByteBuffer.wrap(s.getBytes()));
                        }
                        System.out.println(name + " : " + (System.currentTimeMillis() - now) + " ms");
                    }
            , temp).start();
        }
    }
}
