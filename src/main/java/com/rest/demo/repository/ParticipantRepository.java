package com.rest.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rest.demo.model.Participant;


public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
