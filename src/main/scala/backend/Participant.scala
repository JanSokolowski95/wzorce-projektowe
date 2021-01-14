package backend

import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.scene.paint.Color

class Participant(name_ : String, goodAnswerOwnQuestionPoints: Int, goodAnswerElseQuestionPoints: Int, badAnswerElseQuestionPoints: Int) {
  val participantName = new StringProperty(this, "Name", name_)
  var score = new ObjectProperty[Int](this, "Score", 0)
  var isYourTurn = new ObjectProperty[Boolean](this, "Is this your turn", false)
  var color = new ObjectProperty(this, "color", Color.Red)

  def Answer(isCorrect: Boolean): Unit = {
    if (isCorrect) {
      CorrectAnswer()
    } else IncorrectAnswer()
  }

  private def CorrectAnswer(): Unit = {
    if (isYourTurn.value) {
      ChangeScore(goodAnswerOwnQuestionPoints)
    } else ChangeScore(goodAnswerElseQuestionPoints)
  }

  private def IncorrectAnswer(): Unit = {
    if (!isYourTurn.value) {
      ChangeScore(badAnswerElseQuestionPoints)
    }
  }

  private def ChangeScore(points: Int): Unit = {
    score.value += points
  }

  def StartTurn(): Unit = {
    isYourTurn.value = true
    color.value = Color.Green
  }

  def EndTurn(): Unit = {
    isYourTurn.value = false
    color.value = Color.Red

  }

  def getScore(): ObjectProperty[Int] = {
    score
  }

  def getName(): StringProperty = {
    participantName
  }

}

class Team(name: String, members: List[Participant], goodAnswerOwnQuestionPoints: Int = 2, goodAnswerElseQuestionPoints: Int = 1, badAnswerElseQuestionPoints: Int = -2)
  extends Participant(name: String, goodAnswerOwnQuestionPoints: Int, goodAnswerElseQuestionPoints: Int, badAnswerElseQuestionPoints: Int)
