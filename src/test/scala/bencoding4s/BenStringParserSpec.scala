package bencoding4s

import org.scalacheck._
import org.scalacheck.Arbitrary.arbitrary

class BenStringParserSpec extends Properties("BenStringParser") {
  import Prop.forAll

  property("parse valid bencoded string") = forAll(arbitrary[String].suchThat(_.length > 0)) { s: String =>
    benStringParser.parse(s"${s.length}:$s") == Right(("", BenString(s)))
  }
}
