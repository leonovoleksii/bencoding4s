package bencoding4s

sealed trait BenValue

case class BenString(s: String) extends BenValue
case class BenInteger(i: BigInt) extends BenValue
case class BenList(l: List[BenValue]) extends BenValue
case class BenDictionary(d: Map[BenString, BenValue]) extends BenValue