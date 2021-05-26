package com;

import com.mysql.protocol.Position;
import com.mysql.protocol.util.MysqlNumberUtil;
import org.junit.Test;

import javax.xml.transform.Source;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2021-05-27 00:02
 */
public class MysqlTest {
    @Test
    public void test() {
        byte[] bs = {1,2,3,4};
        Position pos = Position.factory();
        int i = MysqlNumberUtil.readByte2Int(bs, pos, 3);
        int j = MysqlNumberUtil.readByte2Int(bs, pos, 1);
        System.out.println(i+"=="+j);
        System.out.println(pos.getPos());

    }

}
