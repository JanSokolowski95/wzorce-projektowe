package backend

import frontend.DisplayWindow.displayWindow
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

class MediaIterator(media: ObservableBuffer[ContestMedia]) {
  val len: Int = media.length
  var pos = 0
  var finished = false

  val contestFinished = new Alert(AlertType.Information) {
    initOwner(displayWindow)
    title = "Koniec Konkursu"
    headerText = "Pytania się wyczerpały, konkurs został zakończony!"
    contentText = "Mam nadzieję, że wszyscy dobrze się bawili"
  }

  def Next(): Int = {
    if(pos < len - 1){
      pos += 1
    } else if(finished) {
      contestFinished.showAndWait()
    } else {
      finished = true
    }
    pos
  }



}
