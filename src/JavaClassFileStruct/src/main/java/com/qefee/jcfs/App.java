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

            // ***** 常量池计数从1开始 *****
            for (int i = 1; i < constantPollCount; i++) {
                byte tag = dis.readByte();
                echo(String.format("tag = %d", tag));

                switch (tag) {
                    case 1: // utf8_info
                        short length = readLength(dis);
                        byte[] bytes = new byte[length];
                        dis.read(bytes);

                        String bytesStr = new String(bytes);
                        echo(String.format("bytes = %s", bytesStr));

                        break;
                    case 3: // integer_info
                        readInt(dis);
                        break;
                    case 4: // float_info
                        readFloat(dis);
                        break;
                    case 5: // long_info
                        readLong(dis);
                        break;
                    case 6: // double_info
                        readDouble(dis);
                        break;
                    case 7: // class_info
                        readShort(dis);
                        break;
                    case 8: // string_info
                        readShort(dis);
                        break;
                    case 9: // field_ref_info
                        readShort(dis);
                        readShort(dis);
                        break;
                    case 10: // method_ref_info
                        readShort(dis);
                        readShort(dis);
                        break;
                    case 11: // interface_method_ref_info
                        readShort(dis);
                        readShort(dis);
                        break;
                    case 12: // name_and_type_info
                        readShort(dis);
                        readShort(dis);
                        break;
                    case 15: // method_handle_info
                        readByte(dis);
                        readShort(dis);
                        break;
                    case 16: // method_type_info
                        readShort(dis);
                        break;
                    case 18: // invoke_dynamic_info
                        readShort(dis);
                        readShort(dis);
                        break;
                    default:
                        echo("tag error");
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void readByte(DataInputStream dis) throws IOException {
        byte v = dis.readByte();
        echo(String.format("byte = %d", v));
    }

    private static void readShort(DataInputStream dis) throws IOException {
        short v = dis.readShort();
        echo(String.format("short = %d", v));
    }

    private static void readDouble(DataInputStream dis) throws IOException {
        double v = dis.readDouble();
        echo(String.format("double = %f", v));
    }

    private static void readLong(DataInputStream dis) throws IOException {
        long v = dis.readLong();
        echo(String.format("long = %d", v));
    }

    private static void readFloat(DataInputStream dis) throws IOException {
        float v = dis.readFloat();
        echo(String.format("float = %f", v));
    }

    private static void readInt(DataInputStream dis) throws IOException {
        int v = dis.readInt();
        echo(String.format("int = %d", v));
    }

    private static short readLength(DataInputStream dis) throws IOException {
        short length = dis.readShort();
        echo(String.format("length = %d", length));
        return length;
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }

    public static void echo(String msg, Exception e) {
        System.out.println(msg);
        e.printStackTrace();
    }
}
