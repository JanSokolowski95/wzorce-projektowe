package frontend

import backend._
import javafx.scene.image.ImageView
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.image.Image
import scalafx.scene.input.KeyCode
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.stage.FileChooser.ExtensionFilter
import scalafx.stage.{FileChooser, Modality, Stage}

object ContestManagerMainMenu extends JFXApp {

  stage = new PrimaryStage {
    title = "Contest Manager"
    scene = new Scene {
      val askAboutRulesAndParticipants = new AskAboutRules(stage)
      val contestType: ContestType = askAboutRulesAndParticipants.getContestType
      val teamType: TeamType = askAboutRulesAndParticipants.getTeamType
      val participants: ObservableBuffer[Participant] = ObservableBuffer[Participant]()

      val participantFactory = new ParticipantFactory(teamType)
      val addName = new TextField()
      val addButton = new Button("Add")
      val participantsTable: TableView[Participant] = CreateParticipantsTable.CreateParticipantsTable(participants)
      val addMediaButton = new Button("Wybierz pliki")
      val media: ObservableBuffer[ContestMedia] = ObservableBuffer[ContestMedia]()
      addMediaButton.onAction = (event: ActionEvent) => {
        val fileChooser = new FileChooser()
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"))
        val mediaList = fileChooser.showOpenMultipleDialog(stage)
        mediaList.foreach(u => media.add(new ContestMedia(u)))
      }


      val mediaTable: TableView[ContestMedia] = CreateMediaTable.createMediaTable(media)

      def enterKeyPressed(keyCode: KeyCode): Unit = {
        if (keyCode == KeyCode.ENTER) {
          if (addName.getText().isEmpty) {
            AddProperNameAlert(stage).showAndWait()
          } else {
            participants.add(participantFactory.createParticipant(addName.getText()))
            addName.clear()
          }
        }
      }

      addName.setOnKeyPressed((event) => enterKeyPressed(event.getCode))

      addButton.onAction = (event: ActionEvent) => {
        if (addName.getText().isEmpty) {
          AddProperNameAlert(stage).showAndWait()
        } else {
          participants.add(participantFactory.createParticipant(addName.getText()))
          addName.clear()
        }
      }

      val startButton = new Button("start")
      startButton.onAction = (event: ActionEvent) => {
        val contestManagerStart = new ContestManagerStart(contestType, teamType, participants, media)
        val playingScene = contestManagerStart.startContest()
        stage.setScene(playingScene)
      }

      val addParticipants = new HBox(addName, addButton)
      val mainView = new VBox(participantsTable, addParticipants, startButton)
      val filesView = new VBox(mediaTable, addMediaButton)
      val contents = new HBox(mainView, filesView)
      content = contents

    }
  }

  private def AddProperNameAlert(stage: PrimaryStage): Alert = {
    new Alert(AlertType.Error) {
      initOwner(stage)
      title = "Name empty"
      headerText = "You have to input proper name."
      contentText = "Your name field was empty."
    }
  }
}