package com.mingles.metamingle.creativetool.query.application.controller;

import com.mingles.metamingle.common.ApiResponse;
import com.mingles.metamingle.creativetool.query.application.dto.response.ImageResponse;
import com.mingles.metamingle.creativetool.query.application.dto.response.SoundResponse;
import com.mingles.metamingle.creativetool.query.application.service.CreativeToolService;
import com.mingles.metamingle.scenario.command.application.dto.request.CreateScenarioRequest;
import com.mingles.metamingle.scenario.command.infrastructure.service.ScenarioCommandInfraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/creative")
@RequiredArgsConstructor
public class CreativeToolController {

    private final CreativeToolService creativeToolService;
    private final ScenarioCommandInfraService scenarioCommandInfraService;

    @GetMapping("/creative/image")
    public ResponseEntity<ApiResponse> FindImagesByLocation(@RequestBody CreateScenarioRequest request) {

        String location = scenarioCommandInfraService.getBackGroundImage(request.getText());
        List<ImageResponse> response = creativeToolService.findImagesByLocation(location);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success("성공적으로 조회되었습니다." , response)
        );
    }

    @GetMapping("/creative/sound")
    public ResponseEntity<ApiResponse> FindSoundsByGenre(@RequestBody CreateScenarioRequest request) {

        String mood = scenarioCommandInfraService.getBGM(request.getText());
        List<SoundResponse> response = creativeToolService.findSoundsByGenre(mood);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success("성공적으로 조회되었습니다." , response)
        );
    }
}
