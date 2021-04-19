package bencoding4s

import org.scalacheck._

class BenIntegerParserSpec extends Properties("BenIntegerParser") {
  import Prop.forAll

  property("parse valid bencoded integer") = forAll{ i: BigInt =>
    benIntegerParser.parse(s"i${i}e") == Right(("", BenInteger(i)))
  }
}
