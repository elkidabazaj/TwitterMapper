package util;

import java.io.*;

/**
 * Read objects from a file
 */
public class ObjectReader {
    private ObjectInputStream inputStream;

    public ObjectReader(String filename)  {
        File file = new File(filename);
        try {
            inputStream = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object readObject() {
        Object object = null;
        try {
            object = inputStream.readObject();
        } catch (EOFException e) {
            // Do nothing, EOF is expected to happen eventually
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void close() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
