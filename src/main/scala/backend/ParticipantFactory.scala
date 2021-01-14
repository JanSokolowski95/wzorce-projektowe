package backend

class ParticipantFactory(participantType: TeamType, goodAnswerOwnQuestionPoints: Int = 2, goodAnswerElseQuestionPoints: Int = 1, badAnswerElseQuestionPoints: Int = -2) {
  def createParticipant(name: String, members: List[String] = List()): Participant = {
    participantType match {
      case SingleParticipant() =>
        new Participant(name, goodAnswerOwnQuestionPoints, goodAnswerElseQuestionPoints, badAnswerElseQuestionPoints)
      case Group() =>
        new Team(name, createParticipantList(members), goodAnswerOwnQuestionPoints, goodAnswerElseQuestionPoints, badAnswerElseQuestionPoints)
    }
  }

  private def createParticipantList(members: List[String]): List[Participant] = {
    val participants = members.map(u => createParticipant(u))
    participants
  }
}