package frontend

import backend.{ContestMedia, Participant}
import backend.MediaState.MediaState
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

object CreateMediaTable {

  val nameColumn: TableColumn[ContestMedia, String] = new TableColumn[ContestMedia, String] {
    text = "Media"
    cellValueFactory = {
      _.value.name
    }
  }
  val wasMediaPlayedColumn: TableColumn[ContestMedia, Color] = new TableColumn[ContestMedia, Color] {
    text = "Turn"
    cellValueFactory = {
      _.value.color
    }
    prefWidth = 100
    cellFactory = { _ =>
      new TableCell[ContestMedia, Color] {
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

  def createMediaTable(media: ObservableBuffer[ContestMedia]): TableView[ContestMedia] = {
    new TableView[ContestMedia](media) {
      columns ++= List(
        wasMediaPlayedColumn,
        nameColumn
      )
    }
  }
}
