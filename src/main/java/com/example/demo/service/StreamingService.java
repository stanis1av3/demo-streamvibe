package com.example.demo.service;

import java.io.File;

/**
 * Created by stas on 12/23/2017.
 */
public interface StreamingService {


    /**
     * to send a videostream as a file
     *
     * @param file
     * @return
     */
    String uploadVideoStream(File file);

    /**
     * to send a videostream as a bytearray
     *
     * @param byteArray
     * @return
     */
    String uploadVideoStream(byte[] byteArray);

    /**
     * to return properly formed stream url for given stream Id
     *
     * @param streamId
     * @return
     */

    String getVideoStreamUrl(String streamId);


}
