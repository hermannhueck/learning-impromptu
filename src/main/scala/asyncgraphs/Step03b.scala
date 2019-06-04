package asyncgraphs

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/*
  Jon Pretty's talk: Encoding Async Graphs with Dependent Types
  at Scala World 2017
  https://www.youtube.com/watch?v=v60SWyYUW6Q
 */
object Step03b extends App {

  implicit val ec: ExecutionContext = ExecutionContext.global

  val fInFile = Future { readFilename }
  val fSearch = fInFile map { _ => readSearchString }
  val fSource = fInFile map words

  val futureResult = for {
    search <- fSearch
    source <- fSource
  } yield source contains search

  val result = Await.result(futureResult, Duration.Inf)
  println(s"\nFile containes word?   $result\n")
}
