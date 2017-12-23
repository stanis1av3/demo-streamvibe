package com.example.demo.service;

import com.example.demo.transport.integration.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Created by Pavlovskii-pc on 22/12/2017.
 */
@Component
public class StreamingServiceImpl implements StreamingService{

    @Value("${streaming.service.uri}")
    String SERVICE_URI;
    @Value("${streaming.access.key}")
    String ACCESS_KEY;
    @Value("${streaming.vendor.key}")
    String VENDOR_KEY;
    @Value("${streaming.video.url}")
    String STREAM_VIDEO_URL;


    private RestTemplate restTemplate = new RestTemplate();


    public String uploadVideoStream(File file) {

        String token = getApiToken();
        String streamId = uploadVideo(token, file);

        return streamId;
    }


    public String uploadVideoStream(byte[] byteArray) {
        String token = getApiToken();
        String streamId = uploadVideo(token, byteArray);

        return streamId;
    }

    public String getVideoStreamUrl(String streamId) {
        return getStreamReadyUrl(streamId);
    }


    @PostConstruct
    public void started() {
        System.out.println("streaming service started");
    }

    private String getApiToken() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GetApiTokenRequest> getApiTokenRequest = new HttpEntity<>(new GetApiTokenRequest(ACCESS_KEY, VENDOR_KEY), headers);
        ResponseEntity<GetApiTokenResponse> responseA = restTemplate.postForEntity(SERVICE_URI, getApiTokenRequest, GetApiTokenResponse.class);
        String token = responseA.getBody().getResult().getToken();

        return token;
    }


    private String uploadVideo(String token, File file) {
        return uploadVideo(token, file, null);
    }

    private String uploadVideo(String token, byte[] byteArray) {
        return uploadVideo(token, null, byteArray);
    }

    private String uploadVideo(String token, File file, byte[] byteArray) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateStreamRequest> createStreamRequest = new HttpEntity<>(new CreateStreamRequest(token), headers);
        ResponseEntity<CreateStreamResponse> responseB = restTemplate.postForEntity(SERVICE_URI, createStreamRequest, CreateStreamResponse.class);

        String streamId = responseB.getBody().getResult().getStreamId();

        String sourceUrl = responseB.getBody().getResult().getSourceUrl();

        String uploadUrl = responseB.getBody().getResult().getUploadUrl();

        uploadData(file, byteArray, uploadUrl);

        registerStream(streamId, token, sourceUrl);

        return streamId;
    }

    private String getStreamReadyUrl(String streamId) {
        return STREAM_VIDEO_URL.replace("ACCESS_KEY", ACCESS_KEY).replace("STREAM_ID", streamId);
    }


    private void registerStream(String streamId, String token, String sourceUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterStreamRequest> registerStreamRequest = new HttpEntity<>(new RegisterStreamRequest(token, streamId, sourceUrl, "video.mp4"), headers);
        ResponseEntity<BaseResponse> response = restTemplate.postForEntity(SERVICE_URI, registerStreamRequest, BaseResponse.class);

    }

    private String getStreamUrl(String streamId, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScheduleStreamRequest> scheduleStreamRequest = new HttpEntity<>(new ScheduleStreamRequest(streamId, token), headers);
        ResponseEntity<ScheduleStreamResponse> responseA = restTemplate.postForEntity(SERVICE_URI, scheduleStreamRequest, ScheduleStreamResponse.class);
        String streamUrl = responseA.getBody().getResult().getUrl();

        return streamUrl;

    }


    private void uploadData(File file, byte[] byteArray, String uploadUrl) {

        if (file != null) {
            uploadFile(uploadUrl, file);
        }
        if (byteArray != null) {
            uploadByteArray(uploadUrl, byteArray);
        }
    }

    private void uploadFile(final String url, final File file) {
        long length = file.length();
        try {
            org.apache.http.HttpEntity entity = MultipartEntityBuilder.create()
                    .addPart("upload_part_1", new FileBody(file))
                    .addPart("upload_cmd", new StringBody("upload", ContentType.TEXT_PLAIN))
                    .addPart("comment", new StringBody("upload", ContentType.TEXT_PLAIN))
                    .addPart("upload_index", new StringBody("1", ContentType.TEXT_PLAIN))
                    .addPart("upload_count", new StringBody("1", ContentType.TEXT_PLAIN))
                    .addPart("upload_block", new StringBody("" + length, ContentType.TEXT_PLAIN))
                    .addPart("upload_total", new StringBody("" + length, ContentType.TEXT_PLAIN))
                    .build();

            HttpPost request = new HttpPost(url);
            request.setEntity(entity);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(request);
            response.getEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadByteArray(final String url, final byte[] byteArray) {
        long length = byteArray.length;
        try {

            String name = java.util.UUID.randomUUID().toString();
            org.apache.http.HttpEntity byteArrayEntity = MultipartEntityBuilder.create()
                    .addPart("upload_part_1", new ByteArrayBody(byteArray, name + ".mp4"))
                    .addPart("upload_cmd", new StringBody("upload", ContentType.TEXT_PLAIN))
                    .addPart("comment", new StringBody("upload", ContentType.TEXT_PLAIN))
                    .addPart("upload_index", new StringBody("1", ContentType.TEXT_PLAIN))
                    .addPart("upload_count", new StringBody("1", ContentType.TEXT_PLAIN))
                    .addPart("upload_block", new StringBody("" + length, ContentType.TEXT_PLAIN))
                    .addPart("upload_total", new StringBody("" + length, ContentType.TEXT_PLAIN))
                    .build();

            HttpPost request = new HttpPost(url);
            request.setEntity(byteArrayEntity);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(request);
            response.getEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
