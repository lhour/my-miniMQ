package io.openmessaging.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadData {

    public static ByteBuffer read(FileChannel fc, long start, int length) throws IOException {
        ByteBuffer re;
        if(start < 1000000){
            re = readShort(fc, start, length);
        }
        else{
            re = readLong(fc, start, length);
        }

        fc.close();
        return re;
    }

    /**
     * 这个方法中使用了 long 转 int，但是时已经判断过的大小的，一定不会溢出，
     * 或者说，只有不溢出的 long 才会调用此方法
     *
     * @throws IOException
     */
    public static ByteBuffer readShort(FileChannel fc, long start, int length) throws IOException {

        ByteBuffer bf = ByteBuffer.allocate((int)(start + length));

        fc.read(bf);

        byte[] bs = new byte[length];

        for(int i = 0; i < length; i ++){
            bs[i] = bf.get((int) (start + i));
        }
        bf.clear();
        return ByteBuffer.wrap(bs);
    }


    /**
     * 处理特别靠后的情况
     */

    public static ByteBuffer readLong(FileChannel fc, long start, int length) throws IOException {

        int num = (int) (start / 1000000);
        int remain = (int)(start % 1000000);

        ByteBuffer bf = ByteBuffer.allocate(1000000);
        ByteBuffer sp_bf = ByteBuffer.allocate(remain + length);
        byte[] bs = new byte[length];

        for(int i = 0; i < remain; i ++){
            fc.read(bf);
        }
        fc.read(sp_bf);

        for(int i = 0; i < length; i ++){
            bs[i] = sp_bf.get((int)(remain + i));
        }
        bf.clear();
        return ByteBuffer.wrap(bs);

    }
}
