package com.mingles.metamingle.global.infra;

import com.mingles.metamingle.quiz.command.application.dto.response.QuizCommandInfraResponse;
import com.mingles.metamingle.quiz.command.application.dto.response.QuizCommandResponse;
import com.mingles.metamingle.quiz.command.application.service.QuizCommandService;
import com.mingles.metamingle.scenario.command.application.dto.request.SaveScenarioRequest;
import com.mingles.metamingle.scenario.command.application.dto.response.BGMResponse;
import com.mingles.metamingle.scenario.command.application.dto.response.BgImageResponse;
import com.mingles.metamingle.scenario.command.application.service.ScenarioCommandService;
import com.mingles.metamingle.translate.dto.TranslationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiInfraService {

    private WebClient webClient;

    private final ScenarioCommandService scenarioCommandService;
    private final QuizCommandService quizCommandService;

    //AI 세션 변경 확인
    public void setWebClient(String aiAddr) {
        this.webClient = WebClient.builder().baseUrl(aiAddr).build();
    }


    //SSE 를 이용한 텍스트 실시간 스트리밍
    public Flux<ServerSentEvent<String>> getStreamingData(String message) {

        Map<String, String> bodyJson = new HashMap<>();
        bodyJson.put("text", message);

        StringBuilder stringBuilder = new StringBuilder();

        return webClient
                .post()
                .uri("/chatbot/make_script")
                .bodyValue(bodyJson)
                .retrieve()
                .bodyToFlux(String.class)
                .doOnNext(stringBuilder::append)
                .doOnComplete(() -> {
                    String finalString = stringBuilder.toString();
                    SaveScenarioRequest request = new SaveScenarioRequest(message, finalString);
                    scenarioCommandService.saveCreatedScenario(request);
                })
                .map(data -> ServerSentEvent.<String>builder()
                        .data(data)
                        .build());
    }


    // 배경
    public String getBackGroundImage(String message) {

        Map<String, String> bodyJson = new HashMap<>();
        bodyJson.put("text", message);

        BgImageResponse background = webClient.post()
                .uri("/chatbot/image_connect")
                .bodyValue(bodyJson)
                .retrieve()
                .bodyToMono(BgImageResponse.class)
                .block();

        return background.getPlace();
//        return "campus";
    }

    // BGM
    public String getBGM(String message) {

        Map<String, String> bodyJson = new HashMap<>();
        bodyJson.put("text", message);

        BGMResponse bgm = webClient.post()
                .uri("/chatbot/music_connect")
                .bodyValue(bodyJson)
                .retrieve()
                .bodyToMono(BGMResponse.class)
                .block();

        return bgm.getMood();

//        return "Chill";
    }


    public QuizCommandResponse getQuizFromScript(String text) {

        Map<String, String> bodyJson = new HashMap<>();
        bodyJson.put("text", text);

        QuizCommandInfraResponse quiz = webClient.post()
                .uri("/chatbot/quiz_gen")
                .bodyValue(bodyJson)
                .retrieve()
                .bodyToMono(QuizCommandInfraResponse.class)
                .block();

        UUID uuid = UUID.randomUUID();

        return quizCommandService.saveQuizWithUUID(uuid, quiz.getKorea(), quiz.getEnglish(), quiz.getIsquiz());
//        return quizCommandService.saveQuizWithUUID(uuid, "테스트", "test", "yes");
    }

    public String translateTextToEnglish(String text) {

        Map<String, String> bodyJson = new HashMap<>();
        bodyJson.put("text", text);
        bodyJson.put("lang", "KO");

        TranslationResponse response = webClient.post()
                .uri("/chat_translate/chat")
                .bodyValue(bodyJson)
                .retrieve()
                .bodyToMono(TranslationResponse.class)
                .block();

        return response.getOutput();
    }

    public String translateTextToKorean(String text) {

        Map<String, String> bodyJson = new HashMap<>();
        bodyJson.put("text", text);
        bodyJson.put("lang", "EN");

        TranslationResponse response = webClient.post()
                .uri("/chat_translate/chat")
                .bodyValue(bodyJson)
                .retrieve()
                .bodyToMono(TranslationResponse.class)
                .block();

        return response.getOutput();
    }

}
