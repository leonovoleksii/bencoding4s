package bencoding4s

import org.scalacheck._

import scala.util.Random
import org.scalacheck.Arbitrary.arbitrary

class BenDictionaryParserSpec extends Properties("BenDictionaryParser") with BenValueGens {
  import Prop.forAll

  property("parse bencoded dictionary") = forAll(benDictionaryGen(5)) { d: BenDictionary =>
    benDictionaryParser.parse(encode(d)) == Right(("", d))
  }
}
