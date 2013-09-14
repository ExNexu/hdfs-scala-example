package bleibinhaus.hdfsscalaexample

import java.io._
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class HDFSFileServiceTest extends FunSpec with ShouldMatchers {
  val testfileName = "testfile.txt"
  val testText = "Example text"

  describe("Using the HDFS File Service") {

    it("should allow to upload, read and delete a file") {

      val testfile = new File(testfileName)
      testfile.delete
      val testfileWriter: Writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(testfileName), "utf-8"))
      testfileWriter.write(testText)
      testfileWriter.close
      val br = new BufferedReader(new FileReader(testfileName));
      br.readLine should be(testText)
      br.readLine should be (null)
      br.close
      HDFSFileService.removeFile(testfileName)
      HDFSFileService.saveFile(testfileName)
      testfile.delete

      val outputStream = new FileOutputStream(new File(testfileName));
      val in = HDFSFileService.getFile(testfileName)
      var b = new Array[Byte](1024)
      var numBytes = in.read(b)
      while (numBytes > 0) {
        outputStream.write(b, 0, numBytes)
        numBytes = in.read(b)
      }
      outputStream.close
      in.close

      val checkbr = new BufferedReader(new FileReader(testfileName));
      checkbr.readLine should be(testText)
      checkbr.readLine should be (null)
      checkbr.close

      testfile.delete
    }
  }
}

