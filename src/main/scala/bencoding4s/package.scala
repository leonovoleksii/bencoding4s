import cats.parse.{Numbers, Parser}

package object bencoding4s {
  val benIntegerParser: Parser[BenInteger] = for {
    _ <- Parser.char('i')
    start <- Parser.index
    i <- Numbers.bigInt
    _ <- Parser.char('e')
    end <- Parser.index
    _ <- if (i == 0 && (end - start) != 2) Parser.fail else Parser.unit
  } yield BenInteger(i)

  val benStringParser: Parser[BenString] = for {
    stringLength <- Numbers.bigInt
    _ <- Parser.char(':')
    string <- Parser.length(stringLength.toInt)
  } yield BenString(string)

  val benListParser: Parser[BenList] = Parser.recursive[BenList] { recurse =>
    for {
      _ <- Parser.char('l')
      values <- Parser.oneOf[BenValue](benIntegerParser :: benStringParser :: recurse :: Nil).rep0
      _ <- Parser.char('e')
    } yield BenList(values)
  }
}