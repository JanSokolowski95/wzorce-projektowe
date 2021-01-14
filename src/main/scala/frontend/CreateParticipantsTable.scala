package frontend

import backend.Participant
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{Button, TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

object CreateParticipantsTable {
  val isYourTurnColumn: TableColumn[Participant, Color] = new TableColumn[Participant, Color] {
    text = "Turn"
    cellValueFactory = {
      _.value.color
      }
    prefWidth = 100
    cellFactory = { _ =>
      new TableCell[Participant, Color] {
        item.onChange { (_, _, newColor) =>
          graphic =
            if (newColor != null)
              new Circle {
                fill = newColor
                radius = 8
              }
            else
              null
        }
      }
    }
  }

  val nameColumn: TableColumn[Participant, String] = new TableColumn[Participant, String] {
    text = "Name"
    cellValueFactory = {
      _.value.participantName
    }
    prefWidth = 100
  }

  val scoreColumn: TableColumn[Participant, Int] = new TableColumn[Participant, Int] {
    text = "Score"
    cellValueFactory = {
      _.value.score
    }
    prefWidth = 100
  }

  def CreateParticipantsTable(participants: ObservableBuffer[Participant]): TableView[Participant] = {
    new TableView[Participant](participants) {
      columns ++= List(
        isYourTurnColumn,
        nameColumn,
        scoreColumn
      )
    }
  }
}
