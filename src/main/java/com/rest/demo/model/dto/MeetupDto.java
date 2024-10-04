package com.rest.demo.model.dto;

import java.util.List;


public record MeetupDto(String name, String description, List<ParticipantDto> participants) {}
