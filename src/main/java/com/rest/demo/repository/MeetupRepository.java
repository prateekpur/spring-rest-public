package com.rest.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.demo.model.Meetup;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {

}