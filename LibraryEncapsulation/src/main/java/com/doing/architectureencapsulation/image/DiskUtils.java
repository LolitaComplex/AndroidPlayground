package com.doing.architectureencapsulation.image;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-13.
 */

public class DiskUtils {

    public static long getSDAvialableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        int availableBlocks = stat.getAvailableBlocks();
        int blockSize = stat.getBlockSize();
        return availableBlocks * blockSize;
    }
}
