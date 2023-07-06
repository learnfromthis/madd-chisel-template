class MarkovPrefetcherTester extends FlatSpec with ChiselScalatestTester {
  behavior of "MarkovPrefetcher"
  it should "prefetch data correctly" in {
    test(new MarkovPrefetcher) { c =>
      c.io.addr.poke(0x1000.U)
      c.io.data.expect(0.U)

      c.io.addr.poke(0x2000.U)
      c.io.data.expect(0.U)

      c.io.addr.poke(0x1000.U)
      c.io.data.expect(0.U)

      c.io.addr.poke(0x3000.U)
      c.io.data.expect(0.U)
    }
  }
}
