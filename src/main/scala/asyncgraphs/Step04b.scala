package asyncgraphs

import impromptu._
import scala.concurrent.ExecutionContext

/*
  Jon Pretty's talk: Encoding Async Graphs with Dependent Types
  at Scala World 2017
  https://www.youtube.com/watch?v=v60SWyYUW6Q
 */
object Step04b extends App {

  implicit val ec: ExecutionContext = ExecutionContext.global

  val inFile: Async[String, _, String] = Async { readFilename }

  val search: Async[String, inFile.type, String] =
    Async.after(inFile) {
      implicit env: Async.Env[inFile.type] => readSearchString
    }

  val source: Async[Iterator[String], inFile.type, Iterator[String]] =
    Async.after(inFile) {
      implicit env: Async.Env[inFile.type] => words(inFile())
    }

  // val asyncResult0 = Async.after(search) { implicit env => // does NOT compile!!!
  //   source() contains search()
  // }
  val asyncResult: Async[Boolean, search.type with source.type, Boolean] =
    Async.after(search, source) {
      implicit env: Async.Env[search.type with source.type] => source() contains search()
    }

  val result: Boolean = asyncResult.await()
  println(s"\nFile containes word?   $result\n")
}
