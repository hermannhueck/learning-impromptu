package asyncgraphs

import impromptu._
import scala.concurrent.ExecutionContext

/*
  Jon Pretty's talk: Encoding Async Graphs with Dependent Types
  at Scala World 2017
  https://www.youtube.com/watch?v=v60SWyYUW6Q
 */
object Step04a extends App {

  implicit val ec: ExecutionContext = ExecutionContext.global

  val inFile = Async { readFilename }
  val search = Async.after(inFile) { implicit env => readSearchString }
  val source = Async.after(inFile) { implicit env => words(inFile()) }

  // val asyncResult0 = Async.after(search) { implicit env => // does NOT compile!!!
  //   source() contains search()
  // }
  val asyncResult = Async.after(search, source) {
    implicit env => source() contains search()
  }

  val result = asyncResult.await()
  println(s"\nFile containes word?   $result\n")
}
