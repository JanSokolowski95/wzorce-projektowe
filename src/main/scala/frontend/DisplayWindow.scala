package frontend

import frontend.ContestManagerMainMenu.stage
import javafx.scene.image.ImageView
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.scene.layout.BorderPane
import scalafx.stage.{Modality, Stage}

object DisplayWindow {
  val displayWindow = new Stage()
  displayWindow.initModality(Modality.None)
  displayWindow.initOwner(stage)
  val displayScene = new Scene {
    val pane = new BorderPane()
    pane.setPrefSize(1920, 1080)
    content = pane
  }
  displayWindow.setScene(displayScene)
  displayWindow.show()

  def createScene(img: Image) = {
    val newScene = new Scene {
      val imageView = new ImageView(img)
      imageView.setFitHeight(1080)
      imageView.setFitWidth(1920)
      imageView.setPreserveRatio(true)
      val pane = new BorderPane()
      pane.setPrefSize(1920, 1080)
      pane.setCenter(imageView)
      content = pane
    }
    displayWindow.setScene(newScene)
    displayWindow.show()
  }
}
