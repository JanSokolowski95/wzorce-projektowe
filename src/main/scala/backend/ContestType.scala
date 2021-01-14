package backend

trait ContestType {
  def getName(): String = {
    this.getClass.getSimpleName
  }
}

case class Muzyczny() extends ContestType

case class Obrazkowy() extends ContestType