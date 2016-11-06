package movienight.javi.com.movienight.ui;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Javi on 11/6/2016.
 */

public class ImageCache {

    public static final int MEMORY_AMOUNT = 8;

    private LruCache<String, Bitmap> mMemoryCache;
    private int mMemory;

    public ImageCache(int maxMemory) {

        mMemory = maxMemory / MEMORY_AMOUNT;

        mMemoryCache = new LruCache<String, Bitmap>(maxMemory) {

            @Override
            protected int sizeOf(String key, Bitmap value) {

                return value.getByteCount();
            }
        };
    }

    public int getTotalMemory() {

        return mMemory;
    }

    public Bitmap insert(String key, Bitmap value) {

        if(key == null || value == null)
            return null;

        if(mMemoryCache.get(key) != null)
            return null;

        return mMemoryCache.put(key, value);
    }

    public int size() {

        return mMemoryCache.size();
    }

    public Bitmap get(String key) {

        return mMemoryCache.get(key);
    }
}
