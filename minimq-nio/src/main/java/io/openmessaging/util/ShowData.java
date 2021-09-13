package io.openmessaging.util;

import java.nio.ByteBuffer;

/**
 *
 * 将 ByteBuffer 对象的数据展示出来
 *
 */

public class ShowData {

    public static String dataToString(ByteBuffer bf){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bf.limit(); i ++){
            sb.append(bf.get(i) + " ");
        }
        return sb.toString();
    }

}
