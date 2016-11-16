package com.qefee.jcfs;

import com.qefee.jcfs.constant.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * App.
 * Created by tongjin on 2016/11/13.
 */
public class App {

    private static Map<Integer, AbstractConstantObj> constantPool = new HashMap<>();

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

            // 常量池从1开始
            for (int i = 1; i < constantPool.size(); i++) {
                AbstractConstantObj obj = constantPool.get(i);

                switch (obj.getTag()) {
                    case 8:

                        StringInfo stringInfo = (StringInfo) obj;

                        Utf8Info utf8Info = (Utf8Info) constantPool.get((int)stringInfo.getIndex());

                        echo(String.format(
                                "StringInfo(constantPoolIndex = %d) 的属性 index = %d 指向常量池的Utf8Info.bytes = %s",
                                obj.getConstantPoolIndex(),
                                utf8Info.getConstantPoolIndex(),
                                utf8Info.getBytes()));

                        break;
                    default:
                        break;
                }
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

    private static void readConstantPool(DataInputStream dis, short constantPoolCount) throws IOException {
        echo("*** 开始读取常量池 ***");

        // ***** 常量池计数从1开始 *****
        // ***** constant pool count start form 1 *****
        for (int i = 1; i < constantPoolCount; i++) {
            byte tag = dis.readByte();
            echo(String.format("index = %d, tag = %d", i, tag));

            switch (tag) {
                case 1: // utf8_info
                    short length = readLength(dis);
                    byte[] bytes = new byte[length];
                    dis.read(bytes);

                    String bytesStr = new String(bytes);
                    echo(String.format("bytes = %s", bytesStr));

                    Utf8Info utf8Info = new Utf8Info();

                    utf8Info.setBytes(bytesStr);
                    utf8Info.setLength(length);
                    utf8Info.setConstantPoolIndex(i);

                    constantPool.put(i, utf8Info);

                    break;
                case 3: // integer_info
                    int integer_info = readInt(dis, "integer_info");

                    IntegerInfo integerInfo = new IntegerInfo();
                    integerInfo.setBytes(integer_info);
                    integerInfo.setConstantPoolIndex(i);

                    constantPool.put(i, integerInfo);

                    break;
                case 4: // float_info
                    float float_info = readFloat(dis, "float_info");

                    FloatInfo floatInfo = new FloatInfo();

                    floatInfo.setBytes(float_info);
                    floatInfo.setConstantPoolIndex(i);

                    constantPool.put(i, floatInfo);

                    break;
                case 5: // long_info
                    long long_info = readLong(dis, "long_info");

                    LongInfo longInfo = new LongInfo();

                    longInfo.setBytes(long_info);
                    longInfo.setConstantPoolIndex(i);

                    constantPool.put(i, longInfo);

                    break;
                case 6: // double_info
                    double double_info = readDouble(dis, "double_info");

                    DoubleInfo doubleInfo = new DoubleInfo();

                    doubleInfo.setBytes(double_info);
                    doubleInfo.setConstantPoolIndex(i);

                    constantPool.put(i, doubleInfo);

                    break;
                case 7: // class_info
                    short class_info = readShort(dis, "class_info");

                    ClassInfo classInfo = new ClassInfo();

                    classInfo.setIndex(class_info);
                    classInfo.setConstantPoolIndex(i);

                    constantPool.put(i, classInfo);

                    break;
                case 8: // string_info
                    short string_info = readShort(dis, "string_info");

                    StringInfo stringInfo = new StringInfo();

                    stringInfo.setIndex(string_info);
                    stringInfo.setConstantPoolIndex(i);

                    constantPool.put(i, stringInfo);

//                    Utf8Info utf8Info1 = (Utf8Info) constantPool.get(string_info);
//
//                    echo(String.format("index = %d 指向 --> %s", string_info, utf8Info1.getBytes()));

                    break;
                case 9: // field_ref_info
                    short field_ref_info_index1 = readShort(dis, "field_ref_info_index1");
                    short field_ref_info_index2 = readShort(dis, "field_ref_info_index2");

                    FieldRefInfo fieldRefInfo = new FieldRefInfo();

                    fieldRefInfo.setIndex1(field_ref_info_index1);
                    fieldRefInfo.setIndex2(field_ref_info_index2);
                    fieldRefInfo.setConstantPoolIndex(i);

                    constantPool.put(i, fieldRefInfo);

                    break;
                case 10: // method_ref_info
                    short method_ref_info_index1 = readShort(dis, "method_ref_info_index1");
                    short method_ref_info_index2 = readShort(dis, "method_ref_info_index2");

                    MethodRefInfo methodRefInfo = new MethodRefInfo();

                    methodRefInfo.setIndex1(method_ref_info_index1);
                    methodRefInfo.setIndex2(method_ref_info_index2);
                    methodRefInfo.setConstantPoolIndex(i);

                    constantPool.put(i, methodRefInfo);

                    break;
                case 11: // interface_method_ref_info
                    short interface_method_ref_info_index1 = readShort(dis, "interface_method_ref_info_index1");
                    short interface_method_ref_info_index2 = readShort(dis, "interface_method_ref_info_index2");

                    InterfaceMethodRefInfo interfaceMethodRefInfo = new InterfaceMethodRefInfo();

                    interfaceMethodRefInfo.setIndex1(interface_method_ref_info_index1);
                    interfaceMethodRefInfo.setIndex2(interface_method_ref_info_index2);
                    interfaceMethodRefInfo.setConstantPoolIndex(i);

                    constantPool.put(i, interfaceMethodRefInfo);

                    break;
                case 12: // name_and_type_info
                    short name_and_type_info_index1 = readShort(dis, "name_and_type_info_index1");
                    short name_and_type_info_index2 = readShort(dis, "name_and_type_info_index2");

                    NameAndTypeInfo nameAndTypeInfo = new NameAndTypeInfo();

                    nameAndTypeInfo.setIndex1(name_and_type_info_index1);
                    nameAndTypeInfo.setIndex2(name_and_type_info_index2);
                    nameAndTypeInfo.setConstantPoolIndex(i);

                    constantPool.put(i, nameAndTypeInfo);

                    break;
                case 15: // method_handle_info
                    byte method_handle_info_reference_kind = readByte(dis, "method_handle_info_reference_kind");
                    short method_handle_info_reference_index = readShort(dis, "method_handle_info_reference_index");

                    MethodHandleInfo methodHandleInfo = new MethodHandleInfo();

                    methodHandleInfo.setReferenceKind(method_handle_info_reference_kind);
                    methodHandleInfo.setReferenceIndex(method_handle_info_reference_index);
                    methodHandleInfo.setConstantPoolIndex(i);

                    constantPool.put(i, methodHandleInfo);

                    break;
                case 16: // method_type_info
                    short method_type_info_descriptor_index = readShort(dis, "method_type_info_descriptor_index");

                    MethodTypeInfo methodTypeInfo = new MethodTypeInfo();

                    methodTypeInfo.setDescriptorIndex(method_type_info_descriptor_index);
                    methodTypeInfo.setConstantPoolIndex(i);

                    constantPool.put(i, methodTypeInfo);

                    break;
                case 18: // invoke_dynamic_info
                    short invoke_dynamic_info_bootstrap_method_attr_index = readShort(dis, "invoke_dynamic_info_bootstrap_method_attr_index");
                    short invoke_dynamic_info_name_and_type_index = readShort(dis, "invoke_dynamic_info_name_and_type_index");

                    InvokeDynamicInfo invokeDynamicInfo = new InvokeDynamicInfo();

                    invokeDynamicInfo.setBootstrapMethodAttrIndex(invoke_dynamic_info_bootstrap_method_attr_index);
                    invokeDynamicInfo.setNameAndTypeIndex(invoke_dynamic_info_name_and_type_index);
                    invokeDynamicInfo.setConstantPoolIndex(i);

                    constantPool.put(i, invokeDynamicInfo);

                    break;
                default:
                    echo("tag error");
                    break;
            }
        }

        echo("*** 结束读取常量池 ***");
    }

    private static byte readByte(DataInputStream dis, String what) throws IOException {
        byte v = dis.readByte();
        echo(String.format("%s : byte = %d", what, v));
        return v;
    }

    private static short readShort(DataInputStream dis, String what) throws IOException {
        short v = dis.readShort();
        echo(String.format("%s : short = %d", what, v));
        return v;
    }

    private static double readDouble(DataInputStream dis, String what) throws IOException {
        double v = dis.readDouble();
        echo(String.format("%s : double = %f", what, v));
        return v;
    }

    private static long readLong(DataInputStream dis, String what) throws IOException {
        long v = dis.readLong();
        echo(String.format("%s : long = %d", what, v));
        return v;
    }

    private static float readFloat(DataInputStream dis, String what) throws IOException {
        float v = dis.readFloat();
        echo(String.format("%s : float = %f", what, v));

        return v;
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
