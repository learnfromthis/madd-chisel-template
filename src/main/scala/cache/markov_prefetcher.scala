class MarkovPrefetcher extends Module {
  val io = IO(new Bundle {
    val addr = Input(UInt(32.W))
    val data = Output(UInt(32.W))
  })

  // Markov链表，用于存储内存地址转移概率
  val markovTable = Mem(1024, new MarkovNode)

  // 缓存，用于存储预取的数据
  val cache = SyncReadMem(1024, UInt(32.W))

  // 当前访问地址的后继地址
  val nextAddr = Wire(UInt(32.W))

  // 查找Markov链表并进行数据预取
  when(markovTable(io.addr).count > 0.U) {
    nextAddr := markovTable(io.addr).next
    cache.write(nextAddr, io.data)
  }

  // 输出预取的数据
  io.data := cache.read(nextAddr)
}
