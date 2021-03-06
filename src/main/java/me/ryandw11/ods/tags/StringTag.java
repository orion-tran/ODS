package me.ryandw11.ods.tags;

import me.ryandw11.ods.Tag;
import me.ryandw11.ods.io.CountingOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * The string tag.
 */
public class StringTag implements Tag<String> {
    private String value;
    private String name;

    public StringTag(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String s) {
        this.value = s;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
        tempDos.write(value.getBytes(StandardCharsets.UTF_8));

        dos.writeInt(cos.getCount());
        dos.write(os.toByteArray());
        cos.close();
        os.close();
        tempDos.close();
    }

    @Override
    public Tag<String> createFromData(ByteBuffer value, int length) {
        byte[] stringData = new byte[length];
        value.get(stringData);
        this.value = new String(stringData, StandardCharsets.UTF_8);
        return this;
    }

    @Override
    public byte getID() {
        return 1;
    }
}
