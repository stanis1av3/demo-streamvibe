package com.example.demo.service;

import com.example.demo.transport.*;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.databene.jdbacl.identity.mem.SourceTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StreamingService {

    String SERVICE_URI = "https://api.streamvibe.com/api/v5/imd/cloud/stream";
    String ACCESS_KEY = "";
    String VENDOR_KEY = "";
    String STREAM_VIDEO_URL = "//cdn.streamvibe.com/embed/ACCESS_KEY/STREAM_ID/index.html";


    private RestTemplate restTemplate = new RestTemplate();


    public String uploadVideoStream(File file) {
        return null;
    }

    public String getVideoStreamUrl(String streamId) {
        return null;
    }


    @PostConstruct
    public void run() {

        String token = getApiToken();
        String streamId = uploadVideo(token);
        String videoUrl = getStreamReadyUrl(streamId);

        System.out.println("Video url: " + videoUrl);
        System.out.println("Stream ID: " + streamId);

    }

    private String getApiToken() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GetApiTokenRequest> getApiTokenRequest = new HttpEntity<>(new GetApiTokenRequest(ACCESS_KEY, VENDOR_KEY), headers);
        ResponseEntity<GetApiTokenResponse> responseA = restTemplate.postForEntity(SERVICE_URI, getApiTokenRequest, GetApiTokenResponse.class);
        String token = responseA.getBody().getResult().getToken();
        return token;
    }

    private String uploadVideo(String token) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateStreamRequest> createStreamRequest = new HttpEntity<>(new CreateStreamRequest(token), headers);
        ResponseEntity<CreateStreamResponse> responseB = restTemplate.postForEntity(SERVICE_URI, createStreamRequest, CreateStreamResponse.class);

        String streamId = responseB.getBody().getResult().getStreamId();

        String sourceUrl = responseB.getBody().getResult().getSourceUrl();

        String uploadUrl = responseB.getBody().getResult().getUploadUrl();

        uploadFile(uploadUrl, new File("c:/1/video.mp4"));

        System.out.println(sourceUrl);

        registerStream(streamId, token, sourceUrl);

        return streamId;
    }

    private void registerStream(String streamId, String token, String sourceUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterStreamRequest> registerStreamRequest = new HttpEntity<>(new RegisterStreamRequest(token, streamId, sourceUrl, "video.mp4"), headers);
        ResponseEntity<BaseResponse> responseA = restTemplate.postForEntity(SERVICE_URI, registerStreamRequest, BaseResponse.class);

        System.out.println("Stream registered! Status: " + responseA.getBody().getStatus());

    }

    private String getStreamUrl(String streamId, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScheduleStreamRequest> scheduleStreamRequest = new HttpEntity<>(new ScheduleStreamRequest(streamId, token), headers);
        ResponseEntity<ScheduleStreamResponse> responseA = restTemplate.postForEntity(SERVICE_URI, scheduleStreamRequest, ScheduleStreamResponse.class);
        String streamUrl = responseA.getBody().getResult().getUrl();

        return streamUrl;

    }

    private String getStreamReadyUrl(String streamId) {
        return STREAM_VIDEO_URL.replace("ACCESS_KEY", ACCESS_KEY).replace("STREAM_ID", streamId);
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

            org.apache.http.HttpEntity byteArrayEntity = MultipartEntityBuilder.create()
                    .addPart("upload_part_1", new ByteArrayBody())
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

}
