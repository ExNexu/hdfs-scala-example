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
      testfile.createNewFile
      val testfileWriter = new BufferedWriter(new FileWriter(testfile))
      testfileWriter.write(testText)
      testfileWriter.close
      val localCheckReader = new BufferedReader(new FileReader(testfileName))
      localCheckReader.readLine should be(testText)
      localCheckReader.readLine should be (null)
      localCheckReader.close
      HDFSFileService.removeFile(testfileName)
      HDFSFileService.saveFile(testfileName)
      testfile.delete

      val outputStream = new FileOutputStream(new File(testfileName))
      val in = HDFSFileService.getFile(testfileName)
      var b = new Array[Byte](1024)
      var numBytes = in.read(b)
      while (numBytes > 0) {
        outputStream.write(b, 0, numBytes)
        numBytes = in.read(b)
      }
      outputStream.close
      in.close

      val localCheckReader2 = new BufferedReader(new FileReader(testfileName))
      localCheckReader2.readLine should be(testText)
      localCheckReader2.readLine should be (null)
      localCheckReader2.close

      testfile.delete
      HDFSFileService.removeFile(testfileName) should be(true)

    }
  }
}

