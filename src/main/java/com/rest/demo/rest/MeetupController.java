package com.rest.demo.rest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rest.demo.model.Meetup;
import com.rest.demo.model.Participant;
import com.rest.demo.model.dto.MeetupDto;
import com.rest.demo.model.dto.ParticipantDto;
import com.rest.demo.service.MeetupService;
import com.rest.demo.service.ParticipantService;

@Controller
@RestController
@RequestMapping("/meetup")
public class MeetupController {
	public MeetupController(MeetupService meetupService, ParticipantService participantService) {
		this.meetupService = meetupService;
		this.participantService = participantService;
	}
	Logger logger = LoggerFactory.getLogger(MeetupController.class);
	private final MeetupService meetupService;
	private final ParticipantService participantService;

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetupDto getMeetup(@PathVariable Long id) {
		return meetupService.getMeetup(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void createMeetup(@RequestBody MeetupDto meetup) {
		meetupService.createMeetup(new Meetup(meetup.description(), meetup.name()));
	}
	
	@PostMapping(path = "/{id}/participants")
	@ResponseStatus(HttpStatus.OK)
	public void createParticipant(@PathVariable Long id, @RequestBody ParticipantDto participantDto) {
		Participant participant = new Participant(participantDto.name());
		logger.info("Adding comment" + participant);
		meetupService.addParticipantToMeetup(id, participant);
	}

	@GetMapping(path = "/{id}/participants")
	@ResponseStatus(HttpStatus.OK)
	public MeetupDto getMeetupWithComments(@PathVariable Long id) {
		return meetupService.getMeetup(id);
	}
	
	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetupDto updateMeetup(@PathVariable Long id, @RequestBody MeetupDto meetupDto) {
		return meetupService.updateMeetup(id, new Meetup(meetupDto.description(),meetupDto.name()));
	}

	@PutMapping(value = "/{id}/participants/{participantId}")
	@ResponseStatus(HttpStatus.OK)
	public ParticipantDto updateParticipant(@PathVariable Long id, @PathVariable Long participantId, @RequestBody ParticipantDto participantDto) {
		return participantService.updateParticipant(participantId, participantDto);
	}

    @PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MeetupDto updatePatchMeetup(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
    	return meetupService.partialUpdateMeetup(id, updates);
	}
}
