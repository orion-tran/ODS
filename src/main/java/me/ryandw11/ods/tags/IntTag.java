package me.ryandw11.ods.tags;

import me.ryandw11.ods.Tag;
import org.apache.commons.io.output.CountingOutputStream;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class IntTag implements Tag<Integer> {
    private int value;
    private String name;

    public IntTag(String name, int value){
        this.name = name;
        this.value = value;
    }

    @Override
    public void setValue(Integer s) {
        this.value = s;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void writeData(DataOutputStream dos) throws IOException {
        // Indicates the string
        dos.write(getID());
        //Create a new DataOutputStream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        CountingOutputStream cos = new CountingOutputStream(os);
        DataOutputStream tempDos = new DataOutputStream(cos);

        tempDos.writeShort(name.getBytes(StandardCharsets.UTF_8).length);
        tempDos.write(name.getBytes(StandardCharsets.UTF_8));
        tempDos.writeInt(value);

        dos.writeInt(cos.getCount());
        dos.write(os.toByteArray());
        tempDos.close();
    }

    @Override
    public Tag<Integer> createFromData(byte[] value) {
        ByteBuffer wrappedInt = ByteBuffer.wrap(value);
        this.value = wrappedInt.getInt();
        wrappedInt.clear();
        return this;
    }

    @Override
    public byte getID() {
        return 2;
    }
}
