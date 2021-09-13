package io.niolearn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import io.openmessaging.conf.Const;

/**
 * 这个文件用来测试选择的速度
 */
public class ReadTest {
    public static void main(String[] args) throws IOException, FileNotFoundException {

//        ByteBuffer bf = ByteBuffer.allocate(1000000000);
//        FileInputStream fos = new FileInputStream("D:\\文件\\alibaba\\mq-sample-master-63da3467e3951d4a7be289e4a6a39f5c5b49f6f9mqrace2021.git\\src\\main\\java\\io\\test.txt");
//        FileChannel fc = fos.getChannel();
//        long now = System.currentTimeMillis();
//        fc.read(bf);
//        System.out.println(bf.get(1000000));
//        System.out.println(System.currentTimeMillis() - now);//511mm

        ByteBuffer bf = ByteBuffer.allocate(9999);
        FileInputStream fos = new FileInputStream("D:\\文件\\alibaba\\mq-sample-master-63da3467e3951d4a7be289e4a6a39f5c5b49f6f9mqrace2021.git\\src\\main\\java\\io\\" + "test.txt");
        FileChannel fc = fos.getChannel();
        long now = System.currentTimeMillis();
        for(int i = 0; i < 4; i ++) {
            int l = fc.read(bf);
//            bf.flip();
//            bf.clear();
//            System.out.println(l);
        }
        System.out.println(bf.get(1));
        System.out.println(bf);
        System.out.println(System.currentTimeMillis() - now);

        fos.close();
        fc.close();
    }
}
