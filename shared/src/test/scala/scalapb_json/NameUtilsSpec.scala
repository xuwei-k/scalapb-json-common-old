package scalapb_json

import scalaprops._

object NameUtilsSpec extends Scalaprops {
  val `nakeCaseToCamelCase should work for normal names` = Property.forAll {
    assert(NameUtils.snakeCaseToCamelCase("scala_pb") == "scalaPb")
    assert(NameUtils.snakeCaseToCamelCase("foo_bar") == "fooBar")
    assert(NameUtils.snakeCaseToCamelCase("foo_bar_123baz") == "fooBar123Baz")
    assert(NameUtils.snakeCaseToCamelCase("foo_bar_123_baz") == "fooBar123Baz")
    assert(NameUtils.snakeCaseToCamelCase("__foo_bar") == "FooBar")
    assert(NameUtils.snakeCaseToCamelCase("_foo_bar") == "FooBar")
    assert(NameUtils.snakeCaseToCamelCase("_scala_pb") == "ScalaPb")
    assert(NameUtils.snakeCaseToCamelCase("foo__bar") == "fooBar")
    assert(NameUtils.snakeCaseToCamelCase("123bar") == "123Bar")
    assert(NameUtils.snakeCaseToCamelCase("123_bar") == "123Bar")
    true
  }

  val `snakeCaseToCamelCase should work when already in camel case` = Property.forAll {
    assert(NameUtils.snakeCaseToCamelCase("fooBar") == "fooBar")
    assert(NameUtils.snakeCaseToCamelCase("fooBar_baz") == "fooBarBaz")
    assert(NameUtils.snakeCaseToCamelCase("FooBar") == "fooBar")
    true
  }
}
