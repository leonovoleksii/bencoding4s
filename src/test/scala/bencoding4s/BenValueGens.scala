package bencoding4s

import org.scalacheck.Gen

trait BenValueGens {
  val benIntegerGen: Gen[BenInteger] = Gen.resultOf(BenInteger)

  val benStringGen: Gen[BenString] = Gen.resultOf(BenString).suchThat(bs => bs.s.nonEmpty && bs.s.length < 100)

  def benListEntryGen(maxDepth: Int): Gen[BenValue] = if (maxDepth == 0) {
    Gen.oneOf(benIntegerGen, benStringGen)
  } else {
    Gen.lzy(Gen.oneOf(benIntegerGen, benStringGen, benDictionaryGen(maxDepth - 1), benListEntryGen(maxDepth - 1)))
  }

  def benListGen(maxDepth: Int): Gen[BenList] = for {
    size <- Gen.choose(0, 10)
    l <- Gen.listOfN(size, benListEntryGen(maxDepth))
  } yield BenList(l)

  def benDictionaryEntryGen(maxDepth: Int): Gen[BenValue] = if (maxDepth == 0) {
    Gen.oneOf(benIntegerGen, benStringGen)
  } else {
    Gen.lzy(Gen.oneOf(benIntegerGen, benStringGen, benListGen(maxDepth - 1), benDictionaryEntryGen(maxDepth - 1)))
  }

  def benDictionaryGen(maxDepth: Int): Gen[BenDictionary] = for {
    size <- Gen.choose(0, 10)
    l <- Gen.listOfN(size, for {
      key <- benStringGen
      value <- benDictionaryEntryGen(maxDepth)
    } yield key -> value)
  } yield BenDictionary(l.toMap)
}
