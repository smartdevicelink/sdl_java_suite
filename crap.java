package com.ford.applink.fordowner.ivsu;

import android.os.Environment;
import android.os.Looper;
import android.os.Message;

import com.ford.applink.fordowner.ivsu.artifacts.IvsuTransferMessageObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class IvsuUploadAndLocalFileHandler extends IvsuUploadHandler {

    private static final String IVSU_CORRUPTION_DIR = "IVSU_CORRUPTION_TEST";

    String mDir = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + IVSU_CORRUPTION_DIR + File.separator;

    public IvsuUploadAndLocalFileHandler(Looper looper, String chunkStoragePath) {
        super(looper, chunkStoragePath);
        File corruption = new File(mDir);
        if(!corruption.exists()){
            corruption.mkdirs();
        }
    }

    @Override
    public void handleMessage(Message msg) {
        IvsuTransferMessageObject itm = (IvsuTransferMessageObject) msg.obj;
        BufferedInputStream in = null;
        RandomAccessFile raf = null;
        File localFile = new File(mChunkCachePath+ itm.ivsuFileChunk.getLocalName());
        try {
            raf = new RandomAccessFile(mDir + itm.sdlFileName, "rw");
            in = new BufferedInputStream(
                    new FileInputStream(localFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(in != null && raf != null) {

            byte[] buffer = new byte[50000000];

            try {
                raf.seek(itm.ivsuFileChunk.getOffsetStart());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            try {
                int bytesRead = in.read(buffer);
                if(bytesRead == -1){
                    bytesRead = (int) localFile.length();
                }
                raf.write(buffer, 0, bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            try {
                in.close();
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.handleMessage(msg);
    }
}
