package backend

object Adapter {
  def Transform(participant: Participant): ParticipantForDB = {
    ParticipantForDB(participant.getName().value, participant.getScore().value)
  }
}
