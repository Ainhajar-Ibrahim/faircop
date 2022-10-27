package com.emse.spring.faircorp.service;

import com.emse.spring.faircorp.service.dto.ApiGouvAdressDto;
import com.emse.spring.faircorp.service.dto.ApiGouvFeatureDto;
import com.emse.spring.faircorp.service.dto.ApiGouvResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin
@Service
@RestController
@RequestMapping("/api/adresses")
public class AdressSearchService {

    private final RestTemplate restTemplate;


    public AdressSearchService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri("https://api-adresse.data.gouv.fr").build();
    }

    @GetMapping
    List<ApiGouvAdressDto> getAdress(@RequestParam String cri){
        cri=cri.replaceAll("\\s", "");
        return findAdress(List.of(cri.split("-")));
    }

    List<ApiGouvAdressDto> findAdress( List<String> criteria){
        String params = String.join("+", criteria);
        String uri =UriComponentsBuilder.fromUriString("/search").queryParam("q", params).queryParam("limit", 15).build().toUriString();
        return  Objects.requireNonNull(restTemplate.getForObject(uri, ApiGouvResponseDto.class))
                .getFeatures()
                .stream()
                .map(ApiGouvFeatureDto::getProperties)
                .collect(Collectors.toList());
    }
}




