package cache

import chisel3._
import chisel3.iotesters.PeekPokeTester
import chisel3.util._
import scala.collection.mutable.ListBuffer

class Cache1Tester(dut: Cache1) extends PeekPokeTester(dut) {
    // TODO: (writeEnable, address, data) -> (threadId, pc, writeEnable, address, writeData)
    var trace = new ListBuffer[(Boolean, Int, Int)]()

    val numAccesses = 1024

    for (i <- 0 until numAccesses) {
      trace += ((i % 2 == 0, i * 16 % 256, (i + 1))) // TODO: generate your data here as you like
    }
    
    // initialize the valid and ready bits to false
    poke(dut.io.request.valid, false)
    poke(dut.io.response.ready, false)

    // process the lines in the trace
    for (i <- 0 until numAccesses) {
      // wait until the request is ready
      while (peek(dut.io.request.ready) == BigInt(0)) {
        step(1)
      }

      // feed the request
      poke(dut.io.request.valid, true)
      poke(dut.io.request.bits.address, trace(i)._2)
      poke(dut.io.request.bits.writeEnable, trace(i)._1)

      // feed the write data if write is enabled
      if (trace(i)._1) {
        poke(dut.io.request.bits.writeData, trace(i)._3)
      }

      step(1)

      // mark the request as valid and response as ready
      poke(dut.io.request.valid, false)
      poke(dut.io.response.ready, true)
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
