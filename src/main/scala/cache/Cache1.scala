class SequentialAccess extends Module {
  val io = IO(new Bundle {
    val addr = Output(UInt(32.W))
    val data = Input(UInt(32.W))
  })

  // 访问地址从0x00000000到0x00001000
  val addrReg = RegInit(0.U(32.W))
  when(addrReg < 4096.U) {
    io.addr := addrReg
    addrReg := addrReg + 4.U
  }

  // 输出输入数据
  printf("Data: %x\n", io.data)
}
