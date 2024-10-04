# spring-rest-public

Spring rest services covering one-to-many relation in database

Services created -

  1. Get Parent (Get Meetup) Request - /meetup/<meetup_id>
  2. Get Child (Get Participants) Request - /meetup/<meetup_id>/participants/<participant_id>
  3. Create Parent (Create Meetup) POST request - /meetup
  4. Create Child (Create Participant) POST request - /meetup/<meetup_id>/participants
  5. Update Parent (Update Meetup) PUT request - /meetup/<meetup_id>
  6. Update Child (Update Participants) PUT REQUEST - /meetup/<meetup_id>/participants/<participant_id>
  7. Patch Parent (Patch meetup) PATCH request - /meetup/<meetup_id>
