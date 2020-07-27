package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton that caches images loaded from twitter urls.
 */
public class ImageCache {
    private static final ImageCache INSTANCE = new ImageCache();
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private Map<String, BufferedImage> imageCache;
    private Map<String, String> pathCache;

    private ImageCache() {
        imageCache = new HashMap<>();
        pathCache = new HashMap<>();
    }

    public static ImageCache getInstance() {
        return INSTANCE;
    }

    public BufferedImage loadImage(String url) {
        BufferedImage imageFromCache = imageCache.get(url);
        if (imageFromCache == null) {
            imageFromCache = Util.imageFromURL(url);
            imageCache.put(url, imageFromCache);
        }
        return imageFromCache;
    }

    public void storeImageToCache(String url) {
        if (!imageCache.containsKey(url)) {
            Thread t = new Thread(() -> {
                BufferedImage imageFromURL = Util.imageFromURL(url);
                SwingUtilities.invokeLater(() -> imageCache.put(url, imageFromURL));
            });
            t.run();
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    private String sha256(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = data.getBytes();
            md.update(bytes);
            byte[] hash = md.digest();
            return bytesToHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't find SHA-256");
        }
    }

    private String hashURL(String url) {
        String hash = pathCache.get(url);
        if (hash == null) {
            hash = sha256(url);
        }
        return hash;
    }

    private String saveImage(BufferedImage image, String path) {
        File dir = new File("data/imagecache");
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        String pathString = "data/imagecache/" + path + ".png";
        File file = new File(pathString);
        pathString = file.getAbsolutePath();
        if (file.canRead()) {
            return pathString;
        }
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathString;
    }

    public String saveImage(String url) {
        String path = hashURL(url);
        return saveImage(loadImage(url), path);
    }

    //todo just for testing purposes; it should me unmodifiable
    public Map<String, BufferedImage> getImageCache() {
        return imageCache;
    }
}
