package com.rest.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rest.demo.model.Meetup;
import com.rest.demo.model.Participant;
import com.rest.demo.model.dto.ParticipantDto;
import com.rest.demo.repository.MeetupRepository;
import com.rest.demo.repository.ParticipantRepository;

@Service
public class ParticipantService {
	public ParticipantService(MeetupRepository meetupRepository, ParticipantRepository participantRepository) {
		super();
		this.meetupRepository = meetupRepository;
		this.participantRepository = participantRepository;
	}
	private final MeetupRepository meetupRepository;
	private final ParticipantRepository participantRepository;
	
	public List<ParticipantDto> getParticipantsForMeetup(Long meetupId)	{
		Meetup parentmeetup = meetupRepository.findById(meetupId).map(meetup -> new Meetup(meetup.getDescription(), meetup.getName()))
				.orElseThrow(() -> new IllegalArgumentException("Post not found"));
		List<ParticipantDto> participants = parentmeetup.getParticipants().stream()
			.map(participant -> new ParticipantDto(participant.getId(), participant.getName(), participant.getMeetup().getId()))
			.collect(Collectors.toList());
		return participants;
	}
	
	public Long addParticipant(Long meetupId, ParticipantDto participantDto)	{
		Meetup parentmeetup = meetupRepository.findById(meetupId).map(meetup -> new Meetup(meetup.getDescription(), meetup.getName()))
				.orElseThrow(() -> new IllegalArgumentException("Post not found"));
		Participant participant = new Participant(participantDto.name());
		parentmeetup.addParticipant(participant);
		return participantRepository.save(participant).getId();
	}
	
	public ParticipantDto updateParticipant(Long participantId, ParticipantDto participantDto)	{
		Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " Comment not found"));
		participant.setName(participantDto.name());
		participant = participantRepository.save(participant);
		return new ParticipantDto(participant.getId(), participant.getName(), participant.getMeetup().getId());	
	}
}
