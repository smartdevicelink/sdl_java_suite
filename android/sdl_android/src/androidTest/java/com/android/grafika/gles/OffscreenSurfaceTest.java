package com.android.grafika.gles;

import android.opengl.GLES20;
import android.os.Environment;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class OffscreenSurfaceTest {

    private final String TAG = OffscreenSurfaceTest.class.getSimpleName();
    private final int mWidth = 1280;
    private final int mHeight = 720;
    private final int mIterations = 100;

    @Test
    public void testReadPixels() {
        EglCore eglCore = new EglCore(null, 0);
        OffscreenSurface offscreenSurface = new OffscreenSurface(eglCore, mWidth, mHeight);
        float time = runReadPixelsTest(offscreenSurface);
        Log.d(TAG, "runReadPixelsTest returns " + time + " msec");
    }

    // HELPER test method
    /**
     * Does a simple bit of rendering and then reads the pixels back.
     *
     * @return total time (msec order) spent on glReadPixels()
     */
    private float runReadPixelsTest(OffscreenSurface eglSurface) {
        long totalTime = 0;

        eglSurface.makeCurrent();

        ByteBuffer pixelBuf = ByteBuffer.allocateDirect(mWidth * mHeight * 4);
        pixelBuf.order(ByteOrder.LITTLE_ENDIAN);

        Log.d(TAG, "Running...");
        float colorMult = 1.0f / mIterations;
        for (int i = 0; i < mIterations; i++) {
            if ((i % (mIterations / 8)) == 0) {
                Log.d(TAG, "iteration " + i);
            }

            // Clear the screen to a solid color, then add a rectangle.  Change the color
            // each time.
            float r = i * colorMult;
            float g = 1.0f - r;
            float b = (r + g) / 2.0f;
            GLES20.glClearColor(r, g, b, 1.0f);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

            GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
            GLES20.glScissor(mWidth / 4, mHeight / 4, mWidth / 2, mHeight / 2);
            GLES20.glClearColor(b, g, r, 1.0f);
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            GLES20.glDisable(GLES20.GL_SCISSOR_TEST);

            // Try to ensure that rendering has finished.
            GLES20.glFinish();
            GLES20.glReadPixels(0, 0, 1, 1,
                    GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pixelBuf);

            // Time individual extraction.  Ideally we'd be timing a bunch of these calls
            // and measuring the aggregate time, but we want the isolated time, and if we
            // just read the same buffer repeatedly we might get some sort of cache effect.
            long startWhen = System.nanoTime();
            GLES20.glReadPixels(0, 0, mWidth, mHeight,
                    GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pixelBuf);
            totalTime += System.nanoTime() - startWhen;
        }
        Log.d(TAG, "done");

        // It's not the good idea to request external strage permission in unit test.
        boolean requireStoragePermission = false;
        if (requireStoragePermission) {
            long startWhen = System.nanoTime();
            File file = new File(Environment.getExternalStorageDirectory(),
                    "test.png");
            try {
                eglSurface.saveFrame(file);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
            Log.d(TAG, "Saved frame in " + ((System.nanoTime() - startWhen) / 1000000) + "ms");
            assertTrue(file.exists());
        } else {
            // here' we can recognize Unit Test succeeded, but anyway checks to see totalTime and buffer capacity.
            assertTrue(pixelBuf.capacity() > 0 && totalTime > 0);
        }

        return (float)totalTime / 1000000f;
    }

}
