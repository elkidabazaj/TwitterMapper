package tests.util_tests;

import org.junit.jupiter.api.Test;
import util.ImageCache;
import util.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class TestImage {

    private static final String DEFAULT_TESTING_IMAGE = "https://vignette.wikia.nocookie.net/mlp/images/d/d1/Rarity_standing_S1E19_CROPPED.png";

    /*
    * testing default image because of bad url (image url that does not end with
    * .png, .jpeg, or any other compatible extensions)
    * */
    @Test
    public void testDefaultImage() {
        BufferedImage norm = Util.imageFromURL("https://www.cs.ubc.ca/~norm");
        assertNotNull(norm);
    }

    @Test
    public void testGoodImageURL() {
        BufferedImage norm = Util.imageFromURL(DEFAULT_TESTING_IMAGE);
        assertNotNull(norm);
    }

    @Test
    public void testImageException() {
        BufferedImage norm = Util.imageFromURL(null);
        assertNotNull(norm);
    }

    @Test
    public void testLoadImage() {
        BufferedImage norm = ImageCache.getInstance().loadImage(DEFAULT_TESTING_IMAGE);
        assertNotNull(norm);
    }

    @Test
    public void testStoreImageToCache() {
        ImageCache imageCache = ImageCache.getInstance();
        BufferedImage image = Util.imageFromURL(DEFAULT_TESTING_IMAGE);
        imageCache.getImageCache().put(DEFAULT_TESTING_IMAGE, image);

        testStoreExistingImageToCache(imageCache);
        testStoreNewImageToCache(imageCache);
    }

    /*
    * trying to store an image that is already in the cache
    * */
    @Test
    private void testStoreExistingImageToCache(ImageCache imageCache) {
        imageCache.storeImageToCache(DEFAULT_TESTING_IMAGE);
        assertEquals(1, imageCache.getImageCache().size(), "Trying to store an already existing image.");
    }

    /*
    * trying to store an image that is not in the cache
    * */
    @Test
    private void testStoreNewImageToCache(ImageCache imageCache) {
        imageCache.storeImageToCache("https://toppng.com/uploads/preview/twilight-sparkle-my-little-pony-twilight-sparkle-unicor-11563642625seebkqhkur.png");
       try {
           Thread.sleep(3000);
       } catch (Exception e) {
           e.printStackTrace();
       }
        assertEquals(2, imageCache.getImageCache().size(), "Trying to store a new image.");
    }

    @Test
    public void testSaveImage() throws IOException {
        String pathURLFromSave = ImageCache.getInstance().saveImage(DEFAULT_TESTING_IMAGE);
        File file = new File(pathURLFromSave);
        BufferedImage imageFromReturnedURL = ImageIO.read(file);
        assertNotNull(imageFromReturnedURL);
    }

}
