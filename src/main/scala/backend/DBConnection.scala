package backend

object DBConnection{

  import java.sql.Connection
  import java.sql.DriverManager

  // connect to the database named "mysql" on the localhost
  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
  val username = "root"
  val password = "root"

  // there's probably a better way to do this
  var connection:Connection = null


  def updatePoints(participantsForDB: List[ParticipantForDB]): Unit = {
    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      val statement = connection.createStatement()
      participantsForDB.foreach(participant => {
      val resultSet = statement.executeQuery(s"Select Name, Points FROM participants WHERE Name = '${participant.name}';")
      var exists = false
        while (resultSet.next()) {
          val points = resultSet.getInt("Points")
          val executeStatement = connection.createStatement()
          executeStatement.executeUpdate(s"UPDATE participants SET Points = ${points + participant.points} WHERE Name = '${participant.name}';")
          exists = true
        }
        if (!exists) {
          val executeStatement = connection.createStatement()
          executeStatement.executeUpdate(s"INSERT INTO participants (Name, Points)" +
            s"VALUES ('${participant.name}', ${participant.points});")
        }
      }
      )
    }
    connection.close()
    println("Update")
  }

  def check(): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery(s"Select Name, Points FROM participants;")
      while (resultSet.next()) {
        val name = resultSet.getString("Name")
        val points = resultSet.getInt("Points")
        println(s"Name: $name, points: $points")
      }
      connection.close()
      println("Check")
    }
  }
}
