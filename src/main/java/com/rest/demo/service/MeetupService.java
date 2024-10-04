package com.rest.demo.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import com.rest.demo.model.Meetup;
import com.rest.demo.model.Participant;
import com.rest.demo.model.dto.MeetupDto;
import com.rest.demo.model.dto.ParticipantDto;
import com.rest.demo.repository.MeetupRepository;

@Service
public class MeetupService {
	public MeetupService(MeetupRepository meetupRepository) {
		this.meetupRepository = meetupRepository;
	}

	Logger logger = LoggerFactory.getLogger(MeetupService.class);
	private final MeetupRepository meetupRepository;

	public MeetupDto getMeetup(Long id) {
		Meetup meetup = meetupRepository.findById(id).get();
		logger.info("Get Meetup" + meetup.toString());
		logger.info("Participant size : " + meetup.getParticipants().size());
		meetup.getParticipants().forEach(participant -> logger.info("Participant : " + participant.toString()));
		List<ParticipantDto> participants = meetup.getParticipants().stream()
				.map(participant -> new ParticipantDto(participant.getId(), participant.getName(), participant.getMeetup().getId()))
				.collect(Collectors.toList());		
		return new MeetupDto(meetup.getName(), meetup.getDescription(), participants);
	}

	public void createMeetup(Meetup meetup) {
		meetupRepository.save(meetup);  // ID is generated here
	}

	public void addParticipantToMeetup(Long meetupId, Participant participant)	{
		Meetup meetup = meetupRepository.findById(meetupId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Post not found"));
		participant.setMeetup(meetup);
		meetup.addParticipant(participant);
		meetupRepository.save(meetup);
	}
	
	public MeetupDto updateMeetup(Long meetupId, Meetup meetup)	{
		Meetup updatedMeetup = meetupRepository.findById(meetupId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Meetup not found"));
		updatedMeetup.setDescription(meetup.getDescription());
		updatedMeetup.setName(meetup.getName());
		updatedMeetup = meetupRepository.save(updatedMeetup);
		List<ParticipantDto> participants = updatedMeetup.getParticipants().stream().
				map(participant -> new ParticipantDto(participant.getId(), participant.getName(), participant.getMeetup().getId())).
				collect(Collectors.toList());
		return new MeetupDto(updatedMeetup.getName(), updatedMeetup.getDescription(), participants);
	}
	
	public MeetupDto partialUpdateMeetup(Long meetupId, Map<String, Object> updates)	{
		final Meetup updatedMeetup = meetupRepository.findById(meetupId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Meetup not found"));
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Meetup.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, updatedMeetup, value);
            }
        });
        Meetup meetup1 = meetupRepository.save(updatedMeetup);
		List<ParticipantDto> participants = meetup1.getParticipants().stream().
				map(participant -> new ParticipantDto(participant.getId(), participant.getName(), participant.getMeetup().getId())).
				collect(Collectors.toList());
		return new MeetupDto(meetup1.getName(), meetup1.getDescription(), participants);
	}
}
