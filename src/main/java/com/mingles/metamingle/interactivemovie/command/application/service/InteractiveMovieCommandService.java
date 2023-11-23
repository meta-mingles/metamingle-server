package com.mingles.metamingle.interactivemovie.command.application.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.mingles.metamingle.interactivemovie.command.application.dto.response.CreateInteractiveMovieResponse;
import com.mingles.metamingle.interactivemovie.command.application.dto.response.SubtitledVideo;
import com.mingles.metamingle.interactivemovie.command.application.dto.response.UploadVideo;
import com.mingles.metamingle.interactivemovie.command.domain.aggregate.entity.InteractiveMovie;
import com.mingles.metamingle.interactivemovie.command.domain.aggregate.vo.MemberNoVO;
import com.mingles.metamingle.interactivemovie.command.domain.aggregate.vo.ShortFormNoVO;
import com.mingles.metamingle.interactivemovie.command.domain.repository.InteractiveMovieCommandRepository;
import com.mingles.metamingle.interactivemovie.command.domain.service.InteractiveMovieDomainService;
import com.mingles.metamingle.interactivemovie.command.infrastructure.service.ApiShortFormService;
import com.mingles.metamingle.shortform.command.application.dto.response.CreateShortFormResponse;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InteractiveMovieCommandService {

    @Value("${firebase.credentials.path}")
    private String firebaseConfigPath; // Firebase Admin SDK 설정 파일 경로
    @Value("${firebase.storage.bucket}")
    private String bucketName; // Firebase Storage 버킷 이름
    @Value("${firebase.storage.bucket-url}")
    private String bucketUrl;

    private final WebClient webClient = WebClient.builder().baseUrl("http://192.168.0.19:8011/mp4").build();

    private final InteractiveMovieCommandRepository interactiveMovieCommandRepository;

    private final InteractiveMovieDomainService interactiveMovieDomainService;

    private final ApiShortFormService apiShortFormService;

//    public List<CreateInteractiveMovieResponse> createInteractiveMovie(List<MultipartFile> files, String title, String description, List<String> choices, Long memberNo)
//            throws JCodecException, IOException {
//
//        List<CreateInteractiveMovieResponse> response = new ArrayList<>();
//
//        ShortForm shortForm = apiShortFormService.createShortFormWithInteractiveMovie(files.get(0), title, description, memberNo);
//        response.add(new CreateInteractiveMovieResponse(shortForm.getShortFormNo(), null, shortForm.getThumbnailUrl(),shortForm.getUrl(), "none", 0));
//
//        ShortFormNoVO shortFormNoVO = new ShortFormNoVO(shortForm.getShortFormNo());
//
//        for (int i = 1; i <= 2; i++) {
//
//            String fileKeyName = createFileName(files.get(i).getOriginalFilename()); // 파일 이름을 고유한 파일 이름으로 교체
//
//            Bucket bucket = StorageClient.getInstance().bucket(bucketName);
//            InputStream inputStream = files.get(i).getInputStream();
//            Blob blob = bucket.create(fileKeyName, inputStream, files.get(i).getContentType());
//
//            inputStream.close();
//
//            String url = bucketUrl + fileKeyName + "?alt=media";
//
//            String thumbnailUrl = createAndUploadThumbnail(files.get(i), fileKeyName);
//
//            // 임시 멤버 넘버 사용
//            MemberNoVO memberNoVO = new MemberNoVO(memberNo);
//
//            InteractiveMovie interactiveMovieEntity = new InteractiveMovie(title, url, thumbnailUrl, description, choices.get(i - 1), new Date(),
//                    i, shortFormNoVO, memberNoVO);
//
//            InteractiveMovie uploadedInteractiveMovie = interactiveMovieCommandRepository.save(interactiveMovieEntity);
//
//            response.add(new CreateInteractiveMovieResponse(null, uploadedInteractiveMovie.getInteractiveMovieNo(),
//                                                            uploadedInteractiveMovie.getThumbnailUrl(),
//                                                            uploadedInteractiveMovie.getUrl(),
//                                                            uploadedInteractiveMovie.getChoice(),
//                                                            uploadedInteractiveMovie.getSequence()));
//        }
//
//        return response;
//
//    }

    public List<CreateInteractiveMovieResponse> createInteractiveMovieWithSubtitle(List<MultipartFile> files, String title,
                                                                                   String description, List<String> choices, Long memberNo)
                                                                                   throws JCodecException, IOException, InterruptedException {

        List<CreateInteractiveMovieResponse> response = new ArrayList<>();

        CreateShortFormResponse shortForm = apiShortFormService.createShortFormWithInteractiveMovie(files.get(0), title,
                                                                                    description, memberNo, Boolean.TRUE);
        response.add(new CreateInteractiveMovieResponse(shortForm.getShortFormNo(), null,
                shortForm.getThumbnailUrlKr(), shortForm.getUrlKr(),
                shortForm.getThumbnailUrlEng(), shortForm.getUrlEng(), "none", 0));

        ShortFormNoVO shortFormNoVO = new ShortFormNoVO(shortForm.getShortFormNo());

        MemberNoVO memberNoVO = new MemberNoVO(memberNo);

        // ai 서버에 요청
        for (int i = 1; i <= 2; i++) {

            String fileKeyName = createFileName(files.get(i).getOriginalFilename()); // 파일 이름을 고유한 파일 이름으로 교체

            SubtitledVideo subtitledVideo = new SubtitledVideo();

            System.out.println("ai 영어 자막 영상 요청 : 인터랙티브 무비");
            subtitledVideo.setFileEng(sendToAIForEngSub(files.get(i).getResource(), fileKeyName));
            System.out.println("ai 영어 자막 영상 응답 완료 : 인터랙티브 무비");
            System.out.println("ai 한글 자막 영상 요청 : 인터랙티브 무비");
            subtitledVideo.setFileKr(sendToAIForKrSub(fileKeyName));
            System.out.println("ai 한글 자막 영상 응답 완료 : 인터랙티브 무비");

            String thumbnailUrl = shortForm.getThumbnailUrlKr();

            UploadVideo uploadVideoEng = createInteractiveMovie(subtitledVideo.getFileEng(), fileKeyName + "eng.mp4", thumbnailUrl);
            UploadVideo uploadVideoKr = createInteractiveMovie(subtitledVideo.getFileKr(), fileKeyName + "kr.mp4", thumbnailUrl);

            InteractiveMovie interactiveMovieEntity = new InteractiveMovie(title, uploadVideoKr.getUrl(), uploadVideoKr.getThumbnailUrl(),
                    uploadVideoEng.getUrl(), uploadVideoEng.getThumbnailUrl(), description, choices.get(i - 1), new Date(),
                    i, shortFormNoVO, memberNoVO);

            InteractiveMovie createdMovie = interactiveMovieCommandRepository.save(interactiveMovieEntity);

            response.add(new CreateInteractiveMovieResponse(null, createdMovie.getInteractiveMovieNo(),
                    createdMovie.getThumbnailUrlKr(),
                    createdMovie.getUrlKr(),
                    createdMovie.getThumbnailUrlEng(),
                    createdMovie.getUrlEng(),
                    createdMovie.getChoice(),
                    createdMovie.getSequence()));
        }

        return response;

    }

    public MultipartFile sendToAIForEngSub(Resource file, String fileKeyName) {


        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", file);
        bodyBuilder.part("file_uuid", fileKeyName);

        System.out.println("영어 자막 영상 처리 중");

        Flux<DataBuffer> responseBody = webClient.post()
                .uri("/en_script_video/")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        return dataBufferToMultipartFile(fileKeyName, responseBody);
    }

    public MultipartFile sendToAIForKrSub(String fileKeyName) {

        Map<String, String> bodyJson = new HashMap<>();
        bodyJson.put("file_uuid", fileKeyName);

        System.out.println("한글 자막 영상 처리 중 : 인터랙티브 무비");

        return webClient.post()
                .uri("/kr_script_video/")
                .contentType(MediaType.MULTIPART_FORM_DATA)  // Set the content type here
                .bodyValue(bodyJson)
                .retrieve()
                .bodyToMono(MultipartFile.class)
                .block();
    }

    private UploadVideo createInteractiveMovie(MultipartFile file, String fileKeyName, String thumbnailUrl)
                                                    throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket(bucketName);
        InputStream inputStream = file.getInputStream();
        Blob blob = bucket.create(fileKeyName, inputStream, file.getContentType());

        inputStream.close();

        String url = bucketUrl + fileKeyName + "?alt=media";

        return new UploadVideo(url, thumbnailUrl);
    }

    // Flux<DataBuffer>를 MultipartFile로 변환
    private MultipartFile dataBufferToMultipartFile(String fileKeyName, Flux<DataBuffer> responseBody) {
        byte[] byteArray = responseBody
                .collectList()
                .map(dataBuffers -> {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    dataBuffers.forEach(buffer -> {
                        try {
                            byte[] bytes = new byte[buffer.readableByteCount()];
                            buffer.read(bytes);
                            outputStream.write(bytes);
                        } catch (IOException e) {
                            throw new IllegalArgumentException(e);
                        }
                    });
                    return outputStream.toByteArray();
                })
                .block();

        return new MockMultipartFile(fileKeyName, fileKeyName, MediaType.MULTIPART_FORM_DATA_VALUE, byteArray);
    }

    // 이미지 파일 이름 생성
    private String createFileName(String fileName) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        String fileExtension = interactiveMovieDomainService.checkAndGetFileExtension(fileName);

        // UUID + timestamp로 고유한 파일 이름 생성해서 반환
        return UUID.randomUUID().toString().concat(timestamp);
    }

    // 파일 확장자 가져오기
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }

    // 썸네일 이미지 생성
    private String createAndUploadThumbnail(MultipartFile file, String fileKeyName) throws IOException, JCodecException {
        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(multipartToFile(file)));
        double startSec = 2; // 영상에서 얻고자 하는 시간대 설정

        // 시작 시간으로 이동
        grab.seekToSecondPrecise(startSec);
        Picture picture = grab.getNativeFrame();

        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 이미지 포맷을 "jpeg"로 변경
        ImageIO.write(bufferedImage, "jpeg", baos);
        byte[] thumbnailBytes = baos.toByteArray();
        Bucket bucket = StorageClient.getInstance().bucket(bucketName);
        String thumbnailKey = "thumbnails/" + fileKeyName.replace(".mp4", ".jpeg");
        Blob blob = bucket.create(thumbnailKey, thumbnailBytes, "image/jpeg");

        return bucketUrl + thumbnailKey.replace("/", "%2F") + "?alt=media";
    }

    // MultipartFile을 File로 변환
    private File multipartToFile(MultipartFile multipart) throws IOException {
        File file = new File(multipart.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            StreamUtils.copy(multipart.getInputStream(), fos);
        }
        return file;
    }

}
