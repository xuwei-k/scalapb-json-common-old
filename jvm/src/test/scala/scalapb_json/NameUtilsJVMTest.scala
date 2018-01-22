package scalapb_json

import scalaprops._
import com.google.common.base.CaseFormat

object NameUtilsJVMTest extends Scalaprops {

  val `camelCaseToSnakeCase compatible guava` = Property.forAllG(Gen.asciiString) { s =>
    val x = NameUtils.camelCaseToSnakeCase(s)
    val y = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s)
    utest.assert(x == y)
    true
  }

  val `lowerSnakeCaseToCamelCase compatible guava` = Property.forAllG(Gen.asciiString) { s =>
    val x = NameUtils.lowerSnakeCaseToCamelCase(s)
    val y = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s)
    utest.assert(x == y)
    true
  }

}
