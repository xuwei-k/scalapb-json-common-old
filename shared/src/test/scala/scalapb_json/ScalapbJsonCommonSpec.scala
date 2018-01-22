package scalapb_json

import com.google.protobuf.field_mask.FieldMask
import utest._

object ScalapbJsonCommonSpec extends utest.TestSuite {

  override def tests = Tests {
    "FieldMask" - {
      val x = FieldMask(Seq("foo.bar", "baz", "foo_bar.baz"))
      val expect = "foo.bar,baz,fooBar.baz"
      val json = ScalapbJsonCommon.fieldMaskToJsonString(x)
      assert(json == expect)
    }
  }

}
