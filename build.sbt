name := "KAOS"

version := "0.1"

scalaVersion := "2.12.13"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.22"

lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add JavaFX dependencies
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m=>
  "org.openjfx" % s"javafx-$m" % "11" classifier osName
)
