package scalapb_json

import com.google.protobuf.duration.Duration
import com.google.protobuf.timestamp.Timestamp
import jsontest.test.WellKnownTest
import utest._

class WellKnownTypesSpec extends TestSuite {

  val durationProto = WellKnownTest(duration = Some(Duration(146, 3455)))

val tests = Tests{

  Symbol("Duration serializer should work") - {
    assert(Durations.writeDuration(Duration(146, 3455)) == "146.000003455s")
    assert(Durations.writeDuration(Duration(146, 3455000)) == "146.003455s")
    assert(Durations.writeDuration(Duration(146, 345500000)) == "146.345500s")
    assert(Durations.writeDuration(Duration(146, 345500000)) == "146.345500s")
    assert(Durations.writeDuration(Duration(146, 345000000)) == "146.345s")
    assert(Durations.writeDuration(Duration(146, 0)) == "146s")
    assert(Durations.writeDuration(Duration(-146, 0)) == "-146s")
    assert(Durations.writeDuration(Duration(-146, -345)) == "-146.000000345s")
  }

  Symbol("Duration parser should work") - {
    assert(Durations.parseDuration("146.000003455s") == Duration(146, 3455))
    assert(Durations.parseDuration("146.003455s") == Duration(146, 3455000))
    assert(Durations.parseDuration("146.345500s") == Duration(146, 345500000))
    assert(Durations.parseDuration("146.345500s") == Duration(146, 345500000))
    assert(Durations.parseDuration("146.345s") == Duration(146, 345000000))
    assert(Durations.parseDuration("146s") == Duration(146, 0))
    assert(Durations.parseDuration("-146s") == Duration(-146, 0))
    assert(Durations.parseDuration("-146.000000345s") == Duration(-146, -345))
  }

  Symbol("Timestamp parser should work") - {
    val start = Timestamps.parseTimestamp("0001-01-01T00:00:00Z")
    val end = Timestamps.parseTimestamp("9999-12-31T23:59:59.999999999Z")
    assert(start.seconds == Timestamps.TIMESTAMP_SECONDS_MIN)
    assert(start.nanos == 0)
    assert(end.seconds == Timestamps.TIMESTAMP_SECONDS_MAX)
    assert(end.nanos == 999999999)

    assert(Timestamps.writeTimestamp(start) == "0001-01-01T00:00:00Z")
    assert(Timestamps.writeTimestamp(end) == "9999-12-31T23:59:59.999999999Z")

    assert(Timestamps.parseTimestamp("1970-01-01T00:00:00Z") == Timestamp(0, 0))
    assert(Timestamps.parseTimestamp("1969-12-31T23:59:59.999Z") == Timestamp(-1, 999000000))

    assert(Timestamps.writeTimestamp(Timestamp(nanos = 10)) == "1970-01-01T00:00:00.000000010Z")
    assert(Timestamps.writeTimestamp(Timestamp(nanos = 10000)) == "1970-01-01T00:00:00.000010Z")
    assert(Timestamps.writeTimestamp(Timestamp(nanos = 10000000)) == "1970-01-01T00:00:00.010Z")

    assert(Timestamps.writeTimestamp(Timestamps.parseTimestamp("1970-01-01T00:00:00.010+08:35")) == 
      "1969-12-31T15:25:00.010Z")
    assert(Timestamps.writeTimestamp(Timestamps.parseTimestamp("1970-01-01T00:00:00.010-08:12")) == 
      "1970-01-01T08:12:00.010Z")
  }
}

}
