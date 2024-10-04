# spring-rest-public

Spring rest services covering one-to-many relation in database

Services created -

Get Parent (Get Meetup) Request - /meetup/<meetup_id>
Get Child (Get Participants) Request - /meetup/<meetup_id>/participants/<participant_id>
Create Parent (Create Meetup) POST request - /meetup
Create Child (Create Participant) POST request - /meetup/<meetup_id>/participants
Update Parent (Update Meetup) PUT request - /meetup/<meetup_id>
Update Child (Update Participants) PUT REQUEST - /meetup/<meetup_id>/participants/<participant_id>
Patch Parent (Patch meetup) PATCH request - /meetup/<meetup_id>
