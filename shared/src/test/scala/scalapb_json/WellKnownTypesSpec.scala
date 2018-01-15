package scalapb_json

import com.google.protobuf.duration.Duration
import jsontest.test.WellKnownTest
import scalaprops._

object WellKnownTypesSpec extends Scalaprops {

  val durationProto = WellKnownTest(duration = Some(Duration(146, 3455)))

  val `Duration serializer should work` = Property.forAll {
    assert(Durations.writeDuration(Duration(146, 3455)) == "146.000003455s")
    assert(Durations.writeDuration(Duration(146, 3455000)) == "146.003455s")
    assert(Durations.writeDuration(Duration(146, 345500000)) == "146.345500s")
    assert(Durations.writeDuration(Duration(146, 345500000)) == "146.345500s")
    assert(Durations.writeDuration(Duration(146, 345000000)) == "146.345s")
    assert(Durations.writeDuration(Duration(146, 0)) == "146s")
    assert(Durations.writeDuration(Duration(-146, 0)) == "-146s")
    assert(Durations.writeDuration(Duration(-146, -345)) == "-146.000000345s")
    true
  }

  val `Duration parser should work` = Property.forAll {
    assert(Durations.parseDuration("146.000003455s") == Duration(146, 3455))
    assert(Durations.parseDuration("146.003455s") == Duration(146, 3455000))
    assert(Durations.parseDuration("146.345500s") == Duration(146, 345500000))
    assert(Durations.parseDuration("146.345500s") == Duration(146, 345500000))
    assert(Durations.parseDuration("146.345s") == Duration(146, 345000000))
    assert(Durations.parseDuration("146s") == Duration(146, 0))
    assert(Durations.parseDuration("-146s") == Duration(-146, 0))
    assert(Durations.parseDuration("-146.000000345s") == Duration(-146, -345))
    true
  }

}
