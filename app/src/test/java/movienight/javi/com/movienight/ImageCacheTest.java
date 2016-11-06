package movienight.javi.com.movienight;

import android.graphics.Bitmap;
import android.os.Build;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import movienight.javi.com.movienight.ui.ImageCache;

/**
 * Created by Javi on 11/6/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)

public class ImageCacheTest {

    private ImageCache mImageCache;
    private int maxMemory = 1024;

    @Before
    public void setUp() throws Exception {

        mImageCache = new ImageCache(maxMemory);
    }

    @Test
    public void getTotalMemoryCache() throws Exception {

        // Arrange
        int expectedMemory = maxMemory / ImageCache.MEMORY_AMOUNT;

        // Act
        int actualMemoryCache = mImageCache.getTotalMemory();

        // Assert
        Assert.assertEquals(expectedMemory, actualMemoryCache);
    }

    @Test
    public void insertValue() throws Exception {

        // Arrange
        String path = "/sdfhdsfhds";

        int width = 10;
        int height = 10;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;

        // Act
        mImageCache = new ImageCache(maxMemory);
        Bitmap previousValue = mImageCache.insert(path, Bitmap.createBitmap(width, height, config));

        // Assert
        Assert.assertNull(previousValue);
    }

    @Test
    public void doesNotInsertWithNullValues() throws Exception {

        // Arrange
        String path = null;
        Bitmap value = null;
        int expectedCacheSize = 0;

        // Act
        mImageCache = new ImageCache(maxMemory);
        mImageCache.insert(path, value);
        int actualCacheSize = mImageCache.size();

        // Assert
        Assert.assertEquals(expectedCacheSize, actualCacheSize);
    }

    @Test
    public void getValue() throws Exception {

        // Arrange
        String path = "/sdfhdsfhds";

        int width = 10;
        int height = 10;
        Bitmap expectedValue = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Act
        mImageCache = new ImageCache(maxMemory);
        mImageCache.insert(path, expectedValue);
        Bitmap actualValue = mImageCache.get(path);

        // Assert
        Assert.assertSame(expectedValue, actualValue);
    }
}
