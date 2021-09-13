package io.niolearn;

/**
 * 这个类用来测试写入
 */

import io.openmessaging.conf.Const;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class WriteTest {

    public static void main(String[] args) throws IOException, FileNotFoundException {

        FileOutputStream fos = new FileOutputStream(Const.BASE_PATH + "test.txt", true);
        FileChannel fc = fos.getChannel();
        long now = System.currentTimeMillis();
        for(int i = 0; i < 100000; i ++) {
            byte[] bs = new byte[]{ 11, 22, 33, 11, 55, 11, 22, 33, 11, 55,  11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55, 11, 22, 33, 11, 55,};

            fc.write(ByteBuffer.wrap(bs));
        }
        System.out.print(System.currentTimeMillis() - now);
        fos.close();
        fc.close();
    }

}
