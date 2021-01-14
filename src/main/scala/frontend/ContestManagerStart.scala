package frontend

import backend.DBConnection.{check, updatePoints}
import backend.MediaState.{Played, Playing}
import backend.{Adapter, ContestMedia, ContestType, MediaIterator, Participant, ParticipantIterator, TeamType}
import frontend.ContestManagerMainMenu.stage
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, VBox}

class ContestManagerStart(contest: ContestType, team: TeamType, participantsBuffer: ObservableBuffer[Participant], media: ObservableBuffer[ContestMedia]) {

  def startContest(): Scene = {
    val scene: Scene = new Scene {
      val contestType: ContestType = contest
      val teamType: TeamType = team
      val participants: ObservableBuffer[Participant] = participantsBuffer
      val participantsTable: TableView[Participant] = CreateParticipantsTable.CreateParticipantsTable(participants)
      val mediaTable = CreateMediaTable.createMediaTable(media)
      val mediaIterator = new MediaIterator(media)
      protected val participantIterator = new ParticipantIterator(participants)

      val goodAnswer = new Button("Good")
      val badAnswer = new Button("Bad")
      var newPosition = 0
      var mediaPosition = 0
      participants.apply(newPosition).StartTurn()

      goodAnswer.onAction = (event: ActionEvent) => {
        participants.apply(newPosition).Answer(true)
        IterateParticipants()
      }

      badAnswer.onAction = (event: ActionEvent) => {
        participants.apply(newPosition).Answer(false)
        DidSomeoneTookOver()
        IterateParticipants()
      }

      val nextMedia = new Button("Next Question")
      nextMedia.onAction = (event: ActionEvent) => {
        val img = media.apply(mediaPosition).image
        DisplayWindow.createScene(img)
        IterateMedia()
      }

      val answers = new HBox(goodAnswer, badAnswer)
      val mainView = new VBox(participantsTable, answers)
      val filesView = new VBox(mediaTable, nextMedia)
      val contents = new HBox(mainView, filesView)
      content = contents

      private def IterateParticipants() = {
        var oldPosition = newPosition
        newPosition = participantIterator.Next()
        participants.apply(oldPosition).EndTurn()
        participants.apply(newPosition).StartTurn()
      }

      private def IterateMedia() = {
        if(mediaPosition == 0){
          media.apply(mediaPosition).state = Playing
          media.apply(mediaPosition).setColor()
          mediaPosition = mediaIterator.Next()
        } else if(mediaPosition == media.length - 1) {
          val participantsForDB = participants.map(u => Adapter.Transform(u)).toList
          updatePoints(participantsForDB)
          check()
        } else {
          media.apply(mediaPosition-1).state = Played
          media.apply(mediaPosition-1).setColor()
          media.apply(mediaPosition).state = Playing
          media.apply(mediaPosition).setColor()
          mediaPosition = mediaIterator.Next()
        }
      }

      private def DidSomeoneTookOver(): Unit = {
        val ButtonTypeTak = new ButtonType("Tak")
        val ButtonTypeNie = new ButtonType("Nie")
        val alert = new Alert(AlertType.Confirmation) {
          initOwner(stage)
          title = "Czy pytanie zostało przejęte?"
          headerText = "Czy ktoś przejął pytanie?"
          buttonTypes = Seq(
            ButtonTypeTak, ButtonTypeNie
          )
        }
        val result = alert.showAndWait()
        result match {
          case Some(ButtonTypeTak) => WhoTookOver()
          case Some(ButtonTypeNie) =>
        }
      }

      private def WhoTookOver(): Unit = {
        val participantList = participants.toList.map(u => u.getName().value)
        val dialog = new ChoiceDialog(defaultChoice = participants.apply(newPosition).getName().value, choices = participantList) {
          initOwner(stage)
          title = "Kto przejął pytanie?"
          headerText = "Wybierz drużynę, która przejęła pytanie"
          contentText = "Drużyny:"
        }
        val result = dialog.showAndWait()
        result match {
          case Some(participant: String) => DidTheyAnswerCorrectly(participantList.indexOf(participant))
          case None =>
        }
      }

      private def DidTheyAnswerCorrectly(position: Int) = {
        val ButtonTypeTak = new ButtonType("Tak")
        val ButtonTypeNie = new ButtonType("Nie")
        val alert = new Alert(AlertType.Confirmation) {
          initOwner(stage)
          title = "Jaka odpowiedź?"
          headerText = "Czy odpowiedź była poprawna?"
          buttonTypes = Seq(
            ButtonTypeTak, ButtonTypeNie
          )
        }
        val result = alert.showAndWait()
        result match {
          case Some(ButtonTypeTak) => participants.apply(position).Answer(true)
          case Some(ButtonTypeNie) => participants.apply(position).Answer(false)
        }
      }
    }
    scene

  }

}
