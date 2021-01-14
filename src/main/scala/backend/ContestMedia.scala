package backend

import backend.MediaState.{Played, Playing, ToBePlayed}
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.scene.image.Image
import scalafx.scene.paint.Color

import java.io.File
import scala.collection.immutable.Nil.lastIndexOf
import java.nio.file

class ContestMedia(file: File) {
  val fileName: String = file.getName()
  val filePath = file.getAbsoluteFile
  val name = new StringProperty(this, "Name", fileName)
  val path = new StringProperty(this, "Path", filePath.toString)
  println(filePath)
  val image = new Image("file:".concat(filePath.toString))
  var state = ToBePlayed
  var color = new ObjectProperty(this, "Color", Color.Green)

  def setColor() = {
    state match {
      case Played => color.value = Color.Red
      case Playing => color.value = Color.Yellow
      case ToBePlayed => color.value = Color.Green
    }
  }
}
