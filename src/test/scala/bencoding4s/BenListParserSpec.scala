package bencoding4s

import org.scalacheck._

import scala.util.Random
import org.scalacheck.Arbitrary.arbitrary

class BenListParserSpec extends Properties("BenListParser") {
  import Prop.forAll

  property("parse valid one-level bencoded list") = forAll(
    arbitrary[(List[String], List[BigInt])].suchThat(_._1.forall(_.length > 0))
  ) { ls: (List[String], List[BigInt]) =>
    val (ss, is) = ls
    val shuffledList = Random.shuffle(ss ++ is)
    val s = "l" ++ shuffledList.map {
      case i: BigInt => s"i${i}e"
      case s: String => s"${s.length}:$s"
    }.foldLeft("")(_ ++ _) ++ "e"
    val expectedList = shuffledList.map {
      case i: BigInt => BenInteger(i)
      case s: String => BenString(s)
    }
    benListParser.parse(s) == Right(("", BenList(expectedList)))
  }

  property("parse list with list inside") = forAll(
    arbitrary[(List[String], List[BigInt])].suchThat(_._1.forall(_.length > 0))
  ) { ls: (List[String], List[BigInt]) =>
    val (ss, is) = ls
    val shuffledList = Random.shuffle(ss ++ is)
    val s = "ll" ++ shuffledList.map {
      case i: BigInt => s"i${i}e"
      case s: String => s"${s.length}:$s"
    }.foldLeft("")(_ ++ _) ++ "ee"
    val expectedList = shuffledList.map {
      case i: BigInt => BenInteger(i)
      case s: String => BenString(s)
    }
    benListParser.parse(s) == Right(("", BenList(List(BenList(expectedList)))))
  }
}
