package backend

object MediaState extends Enumeration {
  type MediaState = Value
  val Played, Playing, ToBePlayed = Value
}
