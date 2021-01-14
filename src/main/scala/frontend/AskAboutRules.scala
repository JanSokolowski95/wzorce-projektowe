package frontend

import backend._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class AskAboutRules(stage: PrimaryStage) {
  val contestType: ContestType = AskAboutContestType(stage)
  val teamType: TeamType = AskAboutTeamSize(stage)

  private def AskAboutContestType(stage: PrimaryStage): ContestType = {
    val ButtonTypeObrazkowy = new ButtonType("Obrazkowy")
    val ButtonTypeMuzyczny = new ButtonType("Muzyczny")
    val alert = new Alert(AlertType.Confirmation) {
      initOwner(stage)
      title = "Jaki rodzaj konkursu jaki chcesz prowadzić"
      headerText = "Wybierz rodzaj konkursu, jaki chcesz prowadzić"
      buttonTypes = Seq(
        ButtonTypeObrazkowy, ButtonTypeMuzyczny
      )
    }
    val result = alert.showAndWait()
    result match {
      case Some(ButtonTypeObrazkowy) => Muzyczny()
      case Some(ButtonTypeMuzyczny) => Obrazkowy()
    }
  }

  private def AskAboutTeamSize(stage: PrimaryStage): TeamType = {
    val ButtonTypeIndywidualny = new ButtonType("Indywidualny")
    val ButtonTypeDruzynowy = new ButtonType("Drużynowy")
    val alert = new Alert(AlertType.Confirmation) {
      initOwner(stage)
      title = "Czy konkurs będzie indywidualny czy drużynowy"
      headerText = "Wybierz liczebność drużyn"
      buttonTypes = Seq(
        ButtonTypeIndywidualny, ButtonTypeDruzynowy
      )
    }
    val result = alert.showAndWait()
    result match {
      case Some(ButtonTypeIndywidualny) => SingleParticipant()
      case Some(ButtonTypeDruzynowy) => Group()
    }
  }

  def getTeamType: TeamType = {
    teamType
  }

  def getContestType: ContestType = {
    contestType
  }
}
