package bencoding4s

import org.scalacheck._

import scala.util.Random
import org.scalacheck.Arbitrary.arbitrary

class BenListParserSpec extends Properties("BenListParser") with BenValueGens {
  import Prop.forAll

  property("parse bencoded list") = forAll(benListGen(5)) { l: BenList =>
    benListParser.parse(encode(l)) == Right(("", l))
  }
}
