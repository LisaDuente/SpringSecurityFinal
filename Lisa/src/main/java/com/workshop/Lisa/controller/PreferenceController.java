package com.workshop.Lisa.controller;

import com.workshop.Lisa.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/preferences")
@RequiredArgsConstructor
public class PreferenceController {
    private final PreferenceService service;
}
