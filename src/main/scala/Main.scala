import java.sql.DriverManager
import java.sql.Connection

trait DB {
  val driver = "org.postgresql.Driver"
  val url = "jdbc:postgresql://localhost/db"
  val username = "postgres"
  val password = "passwd"
  
  var connection: Option[Connection] = None

  def getConnection(): Connection = {
    connection match {
      case None => {
        Class.forName(driver)
        var c = DriverManager.getConnection(url, username, password)
        connection = Some(c)
        connection.get
      }
      case Some(conn) => conn
    }
  }
}

object Main extends App with DB {
  def getActorNames() = {
    try {
      val statement = getConnection().createStatement()
      val resultSet = statement.executeQuery("SELECT name FROM actors")
      while (resultSet.next()) {
        val name = resultSet.getString("name")
        println("name = " + name)
      }
    } catch {
      case e: Throwable => e.printStackTrace
    }
  }

  def getMovieTitles() = {
    try {
      val statement = getConnection().createStatement()
      val resultSet = statement.executeQuery("SELECT title FROM movies")
      while (resultSet.next()) {
        val title = resultSet.getString("title")
        println("title = " + title)
      }
    } catch {
      case e: Throwable => e.printStackTrace
    }
  }

  def getMoviesForActor(actorName: String) = {
    try {
      val statement = getConnection().createStatement()
      val resultSet = statement.executeQuery(s"""
      SELECT actors.name as actor, movies.title as movie
      FROM actors
      JOIN movies_actors
        ON actors.id = movies_actors.actor_id
      JOIN movies 
        ON movies.id = movies_actors.movie_id
      WHERE '${actorName}' ~ actors.name;
      """
      .stripMargin)
      println(s"Actor: ${actorName}")
      while (resultSet.next()) {
        val actor = resultSet.getString("actor")
        val movie = resultSet.getString("movie")
        println("movie = " + movie)
      }
    } catch {
      case e: Throwable => e.printStackTrace
    }
  }

  def getActorsForMovie(movieTitle: String) = {
    try {
      val statement = getConnection().createStatement()
      val resultSet = statement.executeQuery(s"""
      SELECT actors.name as actor, movies.title as movie
      FROM actors
      JOIN movies_actors
        ON actors.id = movies_actors.actor_id
      JOIN movies 
        ON movies.id = movies_actors.movie_id
      WHERE '${movieTitle}' ~ movies.title;
      """
      .stripMargin)
      println(s"Movie: ${movieTitle}")
      while (resultSet.next()) {
        val actor = resultSet.getString("actor")
        val movie = resultSet.getString("movie")
        println("actor = " + actor)
      }
    } catch {
      case e: Throwable => e.printStackTrace
    }
  }

  getMoviesForActor("Kevin Bacon")
  getMoviesForActor("Keanu Reeves")
  getActorsForMovie("Footloose")

  getConnection().close()
}


