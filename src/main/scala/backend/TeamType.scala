package backend

trait TeamType

case class SingleParticipant() extends TeamType

case class Group() extends TeamType