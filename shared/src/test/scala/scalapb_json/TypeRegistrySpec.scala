package scalapb_json

import com.google.protobuf.wrappers.UInt64Value
import jsontest.test3.{MyTest3, Test3Proto, Wrapper}
import scalaprops._

object TypeRegistrySpec extends Scalaprops {

  val `addFile should add all messages in the file` = Property.forAll {
    val reg = TypeRegistry().addFile(Test3Proto)

    assert(reg.findType("type.googleapis.com/jsontest.MyTest3") == Some(MyTest3))
    assert(reg.findType("type.googleapis.com/jsontest.Wrapper") == Some(Wrapper))
    assert(reg.findType("type.googleapis.com/google.protobuf.UInt64Value") == Some(UInt64Value))
    assert(reg.findType("type.googleapis.com/something.else") == None)
    true
  }

  val `addMessage should not add other messages from same file` = Property.forAll {
    val reg = TypeRegistry().addMessage[MyTest3]
    assert(reg.findType("type.googleapis.com/jsontest.MyTest3") == Some(MyTest3))
    assert(reg.findType("type.googleapis.com/jsontest.Wrapper") == None)
    true
  }

}
