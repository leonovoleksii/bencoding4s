package bencoding4s

import org.scalacheck._
import org.scalacheck.Arbitrary.arbitrary

class BenIntegerParserSpec extends Properties("BenIntegerParser") {
  import Prop.forAll

  property("parse valid bencoded integer") = forAll { i: BigInt =>
    benIntegerParser.parse(s"i${i}e") == Right(("", BenInteger(i)))
  }

  property("fail on -0") = benIntegerParser.parse("i-0e").isLeft

  property("fail on leading zeroes") = forAll(arbitrary[BigInt].suchThat(_ > 0)) { i: BigInt =>
    benIntegerParser.parse(s"i0${i}e").isLeft
  }
}
