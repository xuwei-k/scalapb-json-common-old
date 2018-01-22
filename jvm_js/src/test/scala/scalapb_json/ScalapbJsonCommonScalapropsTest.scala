package scalapb_json

import com.google.protobuf.field_mask.FieldMask
import scalaprops._

object ScalapbJsonCommonScalapropsTest extends Scalaprops {

  val `roundtrip FieldMask toJson/fromJson` = {
    implicit val g: Gen[String] = Gen.asciiString
    implicit val fieldMaskGen = Gen.from1[List[String], FieldMask](FieldMask.apply _)
    Property.forAll { x: FieldMask =>
      val json = ScalapbJsonCommon.fieldMaskToJsonString(x)
      val y = ScalapbJsonCommon.fieldMaskFromJsonString(json)
      utest.assert(x == y)
      true
    }
  }

}
