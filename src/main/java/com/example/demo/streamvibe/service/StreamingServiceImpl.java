package com.example.demo.streamvibe.service;

import com.example.demo.streamvibe.transport.integration.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;

/**
 * Created by Pavlovskii-pc on 22/12/2017.
 */
@Component
@Slf4j
public class StreamingServiceImpl implements StreamingService {

    @Value("${streaming.service.uri}")
    String SERVICE_URI;
    @Value("${streaming.access.key}")
    String ACCESS_KEY;
    @Value("${streaming.vendor.key}")
    String VENDOR_KEY;
    @Value("${streaming.video.url}")
    String STREAM_VIDEO_URL;


    private RestTemplate restTemplate = new RestTemplate();


    @Override
    public String uploadVideoStream(File file) {

        String token = getApiToken();
        String streamId = uploadVideo(token, file);

        return streamId;
    }


    @Override
    public String uploadVideoStream(byte[] byteArray) {
        String token = getApiToken();
        String streamId = uploadVideo(token, byteArray);

        return streamId;
    }

    @Override
    public String getVideoStreamUrl(String streamId) {
        String streamUrl = getStreamReadyUrl(streamId);
        log.debug("Stream url={}", streamUrl);

        return streamUrl;
    }

    @Override
    public ListStreamsResponse listVideoStreams(Integer listLimit, Integer listOffset, String sortMode) {

        String token = getApiToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ListStreamsRequest> getApiTokenRequest = new HttpEntity<>(new ListStreamsRequest(token, listLimit, listOffset, sortMode), headers);
        ResponseEntity<ListStreamsResponse> response = restTemplate.postForEntity(SERVICE_URI, getApiTokenRequest, ListStreamsResponse.class);

        response.getBody().getResult().getStreams().forEach(stream -> stream.setVideoUrl(getStreamReadyUrl(stream.getId())));

        return response.getBody();
    }

    @Override
    public String unregisterVideoStream(String streamId) {
        String token = getApiToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UnregisterStreamRequest> unregisterStreamRequest = new HttpEntity<>(new UnregisterStreamRequest(token, streamId), headers);
        ResponseEntity<BaseResponse> response = restTemplate.postForEntity(SERVICE_URI, unregisterStreamRequest, BaseResponse.class);

        return response.getBody().getStatus();
    }


    @PostConstruct
    public void started() {
        System.out.println("Checking config params..");

        if (StringUtils.isEmpty(SERVICE_URI)) {
            throw new IllegalArgumentException("SERVICE_URI is empty!");
        }
        if (StringUtils.isEmpty(ACCESS_KEY)) {
            throw new IllegalArgumentException("ACCESS_KEY is empty!");
        }
        if (StringUtils.isEmpty(VENDOR_KEY)) {
            throw new IllegalArgumentException("VENDOR_KEY is empty!");
        }
        if (StringUtils.isEmpty(STREAM_VIDEO_URL)) {
            throw new IllegalArgumentException("STREAM_VIDEO_URL is empty!");
        }
        System.out.println("ok..");
        System.out.println("Getting api token msisdn streamVibe..");
        try {
            getApiToken();
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't get ApiToken msisdn streamVibe!! Check availability or credentials");
        }
        System.out.println("ok..");
        System.out.println("Streaming service started");
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
        log.debug("Starting to upload a file... size={}", file != null ? file.length() : byteArray.length);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateStreamRequest> createStreamRequest = new HttpEntity<>(new CreateStreamRequest(token), headers);
        ResponseEntity<CreateStreamResponse> responseB = restTemplate.postForEntity(SERVICE_URI, createStreamRequest, CreateStreamResponse.class);

        String streamId = responseB.getBody().getResult().getStreamId();

        String sourceUrl = responseB.getBody().getResult().getSourceUrl();

        String uploadUrl = responseB.getBody().getResult().getUploadUrl();
        System.out.println("sourceUrl: "+sourceUrl);
        System.out.println("uploadUrl: "+uploadUrl);

        uploadData(file, byteArray, uploadUrl);

        log.debug("Upload is done...");

        registerStream(streamId, token, sourceUrl);

        return streamId;
    }

    public String getStreamReadyUrl(String streamId) {

        GetStreamResponse streamResponse = getStreamData(streamId, getApiToken());
        System.out.println(streamResponse);
      //  getStreamUrl(streamId, getApiToken());

        return STREAM_VIDEO_URL.replace("ACCESS_KEY", ACCESS_KEY).replace("STREAM_ID", streamId);
    }

    private GetStreamResponse getStreamData(String streamId, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GetStreamRequest> getStreamRequest = new HttpEntity<>(new GetStreamRequest(token, streamId), headers);
        ResponseEntity<GetStreamResponse> response = restTemplate.postForEntity(SERVICE_URI, getStreamRequest, GetStreamResponse.class);
        return response.getBody();
    }

    private void registerStream(String streamId, String token, String sourceUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterStreamRequest> registerStreamRequest = new HttpEntity<>(new RegisterStreamRequest(token, streamId, sourceUrl, "video.mp4"), headers);
        ResponseEntity<BaseResponse> response = restTemplate.postForEntity(SERVICE_URI, registerStreamRequest, BaseResponse.class);

        log.debug("Register is done.. status={}", response.getBody().getStatus());

    }

    private String getStreamUrl(String streamId, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ScheduleStreamRequest> scheduleStreamRequest = new HttpEntity<>(new ScheduleStreamRequest(streamId, token), headers);
        ResponseEntity<ScheduleStreamResponse> responseA = restTemplate.postForEntity(SERVICE_URI, scheduleStreamRequest, ScheduleStreamResponse.class);
        String streamUrl = responseA.getBody().getResult().getUrl();

        System.out.println("streamUrl: "+streamUrl);

        return streamUrl;

    }


    private void uploadData(File file, byte[] byteArray, String uploadUrl) {

        System.out.println("UPLOAD URL: "+uploadUrl);

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

    private void uploadByteStream(final String url, final InputStream inputStream, final long length) {

        try {

            String name = java.util.UUID.randomUUID().toString();
            org.apache.http.HttpEntity byteArrayEntity = MultipartEntityBuilder.create()
                    .addPart("upload_part_1", new InputStreamBody(inputStream, ContentType.APPLICATION_OCTET_STREAM))
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
