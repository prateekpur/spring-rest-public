package com.rest.demo.model;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Meetup {
	public Meetup() {}
	
	public Meetup(String description, String name) {
		this.description = description;
		this.name = name;
	}

	public Meetup(String description, String name, List<Participant> participants) {
		this.description = description;
		this.name = name;
		this.participants = new ArrayList<Participant>();
		this.participants.addAll(participants);
	}

	private String description;
	private String name;
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}
	
	public void addParticipant(Participant participant)	{
		participants.add(participant);
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meetup_seq")
    @SequenceGenerator(name = "meetup_seq_gen", sequenceName = "meetup_seq", allocationSize = 1)
	private Long id;
	
    @OneToMany(mappedBy = "meetup", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();

}
