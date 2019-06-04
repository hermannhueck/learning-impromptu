import asyncgraphs._

object ExecAll extends App {

  val progs = Seq(Step01, Step02, Step03a, Step03b, Step04a, Step04b, Step04c)

  progs foreach { app =>
    println(">>>>> " + app.getClass.getName.replaceFirst(".$", ""))
    app.main(Array())
  }
}
