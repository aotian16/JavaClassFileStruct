package com.qefee.jcfs;

import java.io.*;

/**
 * App.
 * Created by tongjin on 2016/11/13.
 */
public class App {
    public static void main(String[] args) {
        String filePath = "/Users/tongjin/mydir/tmp/jcfs/App.class";
        File file = new File(filePath);

        if (!file.exists()) {
            echo(String.format("file not exists, path = %s", filePath));
            return;
        }

        if (!file.isFile()) {
            echo(String.format("%s is not a file", filePath));
            return;
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);

            int magicNumber = dis.readInt();

            String magicNumberStr = Integer.toHexString(magicNumber);
            echo(String.format("magic number = %s", magicNumberStr));

            short minorVersion = dis.readShort();
            echo(String.format("minorVersion = %d", minorVersion));

            short majorVersion = dis.readShort();
            echo(String.format("majorVersion = %d", majorVersion));

            short constantPollCount = dis.readShort();
            echo(String.format("constantPollCount = %d", constantPollCount));

//            for (int i = 0; i < constantPollCount; i++) {
//                byte tag = dis.readByte();
//            }

            byte tag = dis.readByte();

            echo(String.format("tag = %d", tag));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void echo(String msg) {
        System.out.println(msg);
    }

    public static void echo(String msg, Exception e) {
        System.out.println(msg);
        e.printStackTrace();
    }
}
