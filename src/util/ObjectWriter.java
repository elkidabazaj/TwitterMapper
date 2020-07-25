package util;

import java.io.*;


public class ObjectWriter {
    private ObjectOutputStream outputStream;

    public ObjectWriter(String filename) {
        try {
            File file = new File(filename);
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeObject(Object object) {
        try {
            outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
