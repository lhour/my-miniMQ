package io.openmessaging.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.rmi.server.ExportException;

public class WriteData {

    public static int Write(FileChannel fc, ByteBuffer data) throws IOException {
        try{
            fc.write(data);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
}
