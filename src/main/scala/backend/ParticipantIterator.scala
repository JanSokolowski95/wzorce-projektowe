package backend

import scalafx.collections.ObservableBuffer

class ParticipantIterator(participants: ObservableBuffer[Participant]) {
  val len: Int = participants.length
  var pos = 0

  def Next(): Int = {
    if(pos < len - 1){
      pos += 1
    } else {
      pos = 0
    }
    pos
  }
}
