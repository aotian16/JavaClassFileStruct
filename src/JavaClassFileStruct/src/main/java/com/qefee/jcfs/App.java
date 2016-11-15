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

            readShort(dis, "minorVersion");

            readShort(dis, "majorVersion");

            short constantPollCount = dis.readShort();
            echo(String.format("constantPollCount = %d", constantPollCount));

            readConstantPool(dis, constantPollCount);

            readShort(dis, "access_flags");

            readShort(dis, "this_class");

            readShort(dis, "super_class");

            short interfaces_count = readLength(dis);

            for (int i = 0; i < interfaces_count; i++) {
                readShort(dis, String.format("interface index = %d", i));
            }

            readMembers(dis, "filed_info");

            readMembers(dis, "method_info");

            readAttributes(dis, "class_attribute_info");

            int read = dis.read();

            if (read == -1) {
                echo("确实读取完毕了");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readMembers(DataInputStream dis, String what) throws IOException {
        short count = readLength(dis);

        for (int i = 0; i < count; i++) {
            readShort(dis, String.format("%s index = %d, access_flags", what, i));
            readShort(dis, String.format("%s index = %d, name_index", what, i));
            readShort(dis, String.format("%s index = %d, descriptor_index", what, i));
            readAttributes(dis, what);
        }
    }

    private static void readAttributes(DataInputStream dis, String what) throws IOException {
        short attributes_count = readLength(dis);
        for (int j = 0; j < attributes_count; j++) {
            readShort(dis, String.format("%s attribute_name_index", what));
            int attribute_length = readInt(dis, String.format("%s attribute_length", what));
            byte[] bytes = new byte[attribute_length];
            dis.read(bytes);
        }
    }

    private static void readConstantPool(DataInputStream dis, short constantPollCount) throws IOException {
        echo("*** 开始读取常量池 ***");

        // ***** 常量池计数从1开始 *****
        // ***** constant pool count start form 1 *****
        for (int i = 1; i < constantPollCount; i++) {
            byte tag = dis.readByte();
            echo(String.format("index = %d, tag = %d", i, tag));

            switch (tag) {
                case 1: // utf8_info
                    short length = readLength(dis);
                    byte[] bytes = new byte[length];
                    dis.read(bytes);

                    String bytesStr = new String(bytes);
                    echo(String.format("bytes = %s", bytesStr));

                    break;
                case 3: // integer_info
                    readInt(dis, "integer_info");
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
                    readShort(dis, "class_info");
                    break;
                case 8: // string_info
                    readShort(dis, "string_info");
                    break;
                case 9: // field_ref_info
                    readShort(dis, "field_ref_info");
                    readShort(dis, "field_ref_info");
                    break;
                case 10: // method_ref_info
                    readShort(dis, "method_ref_info");
                    readShort(dis, "method_ref_info");
                    break;
                case 11: // interface_method_ref_info
                    readShort(dis, "interface_method_ref_info");
                    readShort(dis, "interface_method_ref_info");
                    break;
                case 12: // name_and_type_info
                    readShort(dis, "name_and_type_info");
                    readShort(dis, "name_and_type_info");
                    break;
                case 15: // method_handle_info
                    readByte(dis);
                    readShort(dis, "method_handle_info");
                    break;
                case 16: // method_type_info
                    readShort(dis, "method_type_info");
                    break;
                case 18: // invoke_dynamic_info
                    readShort(dis, "invoke_dynamic_info");
                    readShort(dis, "invoke_dynamic_info");
                    break;
                default:
                    echo("tag error");
                    break;
            }
        }

        echo("*** 结束读取常量池 ***");
    }

    private static void readByte(DataInputStream dis) throws IOException {
        byte v = dis.readByte();
        echo(String.format("byte = %d", v));
    }

    private static void readShort(DataInputStream dis, String what) throws IOException {
        short v = dis.readShort();
        echo(String.format("%s : short = %d", what, v));
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

    private static int readInt(DataInputStream dis, String what) throws IOException {
        int v = dis.readInt();
        echo(String.format("%s : int = %d", what, v));
        return v;
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
