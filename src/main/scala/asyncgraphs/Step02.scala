package asyncgraphs

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/*
  Jon Pretty's talk: Encoding Async Graphs with Dependent Types
  at Scala World 2017
  https://www.youtube.com/watch?v=v60SWyYUW6Q
 */
object Step02 extends App {

  implicit val ec: ExecutionContext = ExecutionContext.global

  val inFile = Future { readFilename }
  val search = inFile map { _ => readSearchString }
  val source = inFile map words
  val futureSeqObject = Future.sequence(Seq(search, source))
  val futureResult = futureSeqObject map {
    case Seq(searchVal, sourceVal) =>
      sourceVal.asInstanceOf[Iterator[String]] contains searchVal.asInstanceOf[String]
  }

  val result = Await.result(futureResult, Duration.Inf)
  println(s"\nFile containes word?   $result\n")
}
