import cats.parse.{Numbers, Parser}

package object bencoding4s {
  val benIntegerParser: Parser[BenInteger] = for {
    _ <- Parser.char('i')
    i <- Numbers.bigInt
    _ <- Parser.char('e')
  } yield BenInteger(i)
}
