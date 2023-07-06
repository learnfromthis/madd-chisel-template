class RandomAccess extends Module {
  val io = IO(new Bundle {
    val addr = Output(UInt(32.W))
    val data = Input(UInt(32.W))
  })

  // 随机访问地址
  val addrReg = RegInit(0.U(32.W))
  when(true.B) {
    io.addr := addrReg
    addrReg := addrReg ^ (0xdeadbeef.U + io.data)
  }
}
