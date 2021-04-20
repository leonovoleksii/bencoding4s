package bencoding4s

import org.scalacheck._

class BenListParserSpec extends Properties("BenListParser") with BenValueGens {
  import Prop.forAll

  property("parse bencoded list") = forAll(benListGen(5)) { l: BenList =>
    benListParser.parse(encode(l)) == Right(("", l))
  }

  property("parse bencoded list with max depth") = forAll(benListGen(5)) { l: BenList =>
    benListParser(DefaultMaxDepth).parse(encode(l)) == Right(("", l))
  }
}
