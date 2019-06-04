
package object asyncgraphs {

  def readFilename: String = {
    // StdIn.readLine("Filename: ")
    "README.md"
  }

  def readSearchString: String = {
    // scala.io.StdIn.readLine("Search: ")
    "type"
  }

  def words(file: String): Iterator[String] =
    linesFromFile(file) flatMap { s => s.toLowerCase.split("\\W").toList }

  def linesFromFile(file: String): Iterator[String] =
    scala.io.Source.fromFile(file).getLines
}
