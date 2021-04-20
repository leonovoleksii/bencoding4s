import cats.parse.{Numbers, Parser}

package object bencoding4s {
  val DefaultMaxDepth: Int = 500

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
      values <- Parser.oneOf[BenValue](benIntegerParser :: benStringParser :: benDictionaryParser :: recurse :: Nil).rep0
      _ <- Parser.char('e')
    } yield BenList(values)
  }

  def benListParser(maxDepth: Int): Parser[BenList] = if (maxDepth < 0) Parser.failWith("Maximum list depth reached") else for {
    _ <- Parser.char('l')
    values <- Parser.oneOf[BenValue](benIntegerParser :: benStringParser :: benDictionaryParser(maxDepth - 1) :: benListParser(maxDepth - 1) :: Nil).rep0
    _ <- Parser.char('e')
  } yield BenList(values)

  val benDictionaryParser: Parser[BenDictionary] = Parser.recursive[BenDictionary] { recurse =>
    for {
      _ <- Parser.char('d')
      keyValues <- (for {
        key <- benStringParser
        value <- Parser.oneOf[BenValue](benIntegerParser :: benStringParser :: benListParser :: recurse :: Nil)
      } yield (key -> value)).rep0
      _ <- Parser.char('e')
    } yield BenDictionary(keyValues.toMap)
  }

  def benDictionaryParser(maxDepth: Int): Parser[BenDictionary] = if (maxDepth < 0) Parser.failWith("Maximum dictionary depth reached") else for {
    _ <- Parser.char('d')
    keyValues <- (for {
      key <- benStringParser
      value <- Parser.oneOf[BenValue](benIntegerParser :: benStringParser :: benListParser(maxDepth - 1) :: benDictionaryParser(maxDepth - 1) :: Nil)
    } yield (key -> value)).rep0
    _ <- Parser.char('e')
  } yield BenDictionary(keyValues.toMap)

  def encode(bv: BenValue): String = bv match {
    case BenInteger(i) => s"i${i}e"
    case BenString(s) => s"${s.length}:$s"
    case BenList(l) => s"l${l.map(encode).foldLeft("")(_ ++ _)}e"
    case BenDictionary(d) => s"d${d.map(sv => encode(sv._1) ++ encode(sv._2)).foldLeft("")(_ ++ _)}e"
  }
}