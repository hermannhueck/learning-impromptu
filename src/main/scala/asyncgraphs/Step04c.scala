package asyncgraphs

import impromptu._
import scala.concurrent.ExecutionContext

/*
  Jon Pretty's talk: Encoding Async Graphs with Dependent Types
  at Scala World 2017
  https://www.youtube.com/watch?v=v60SWyYUW6Q
 */
object Step04c extends App {

  implicit val ec: ExecutionContext = ExecutionContext.global

  val inFile: Async[String, _, String] = Async { readFilename }

  /*
    The contravariant Dependency[-D] is implicitly constructed by autoWrap:

      implicit def autoWrap(async: Async[_, _, _]): Dependency[async.type] = Dependency(async)

      final case class Dependency[-A <: Async[_, _, _]] private (async: Async[_, _, _])
   */

  import Async.Dependency

  val search: Async[String, inFile.type, String] =
    Async.after(Dependency[inFile.type](inFile)) {
      implicit env: Async.Env[inFile.type] => readSearchString
    }

  val source: Async[Iterator[String], inFile.type, Iterator[String]] =
    Async.after(Dependency[inFile.type](inFile)) {
      implicit env: Async.Env[inFile.type] => words(inFile())
    }

  val asyncResult: Async[Boolean, search.type with source.type, Boolean] =
    Async.after(Dependency[search.type](search), Dependency[source.type](source)) {
      implicit env: Async.Env[search.type with source.type] => source() contains search()
    }

  val result: Boolean = asyncResult.await()
  println(s"\nFile containes word?   $result\n")
}
