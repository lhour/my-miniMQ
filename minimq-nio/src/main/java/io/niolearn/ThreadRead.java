package io.niolearn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ThreadRead {
    public static void main(String[] args) throws IOException, FileNotFoundException {
        for(int j = 0; j < 50; j ++){
            new Thread(
                    () -> {
                        ByteBuffer bf = ByteBuffer.allocate(10000000);
                        FileInputStream fos = null;
                        try {
                            fos = new FileInputStream("D:\\文件\\alibaba\\mq-sample-master-63da3467e3951d4a7be289e4a6a39f5c5b49f6f9mqrace2021.git\\src\\main\\java\\io\\test.txt");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        FileChannel fc = fos.getChannel();
                        long now = System.currentTimeMillis();
                        for(int i = 0; i < 10000; i ++) {
                            try {
                                fc.read(bf);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + " : " + bf.get(1));
                        System.out.println(System.currentTimeMillis() - now);//26ms

                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            fc.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            , j + "").start();
        }


    }
}
