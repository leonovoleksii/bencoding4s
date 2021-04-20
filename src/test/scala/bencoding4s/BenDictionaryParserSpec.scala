package bencoding4s

import org.scalacheck._

class BenDictionaryParserSpec extends Properties("BenDictionaryParser") with BenValueGens {
  import Prop.forAll

  property("parse bencoded dictionary") = forAll(benDictionaryGen(5)) { d: BenDictionary =>
    benDictionaryParser.parse(encode(d)) == Right(("", d))
  }

  property("parse bencoded dictionary with max depth") = forAll(benDictionaryGen(5)) { d: BenDictionary =>
    benDictionaryParser(DefaultMaxDepth).parse(encode(d)) == Right(("", d))
  }
}
