import cats.parse.{Numbers, Parser}

package object bencoding4s {
  val benIntegerParser: Parser[BenInteger] = for {
    _ <- Parser.char('i')
    i <- Numbers.bigInt
    _ <- Parser.char('e')
    l <- Parser.index
    _ <- if (i == 0 && l != 3) Parser.fail else Parser.unit
  } yield BenInteger(i)

  val benStringParser: Parser[BenString] = for {
    stringLength <- Numbers.bigInt
    _ <- Parser.char(':')
    string <- Parser.length(stringLength.toInt)
  } yield BenString(string)
}