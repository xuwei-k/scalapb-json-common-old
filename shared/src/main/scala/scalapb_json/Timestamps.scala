package scalapb_json

import com.google.protobuf.TextFormat.ParseException
import com.google.protobuf.timestamp.Timestamp

object Timestamps {
  // Timestamp for "0001-01-01T00:00:00Z"
  val TIMESTAMP_SECONDS_MIN = -62135596800L
  val TIMESTAMP_SECONDS_MAX = 253402300799L
  val MICROS_PER_SECOND = 1000000
  val NANOS_PER_SECOND = 1000000000
  val NANOS_PER_MILLISECOND = 1000000
  val NANOS_PER_MICROSECOND = 1000


  def isValid(ts: Timestamp): Boolean =
    (ts.seconds >= TIMESTAMP_SECONDS_MIN &&
      ts.seconds <= TIMESTAMP_SECONDS_MAX &&
      ts.nanos >= 0 &&
      ts.nanos < NANOS_PER_SECOND)

  def checkValid(ts: Timestamp): Timestamp = {
    require(isValid(ts), "Timestamp is not valid.")
    ts
  }

  def formatNanos(nanos: Int): String = {
    // Determine whether to use 3, 6, or 9 digits for the nano part.
    if (nanos % NANOS_PER_MILLISECOND == 0) {
      "%1$03d".format(nanos / NANOS_PER_MILLISECOND)
    } else if (nanos % NANOS_PER_MICROSECOND == 0) {
      "%1$06d".format(nanos / NANOS_PER_MICROSECOND)
    } else {
      "%1$09d".format(nanos)
    }
  }

  def parseTimezoneOffset(s: String): Long = s(0) match {
    case 'Z' =>
      if (s.length != 1) {
        throw new ParseException(s"Failed to parse timestamp: invalid trailing data: '$s'")
      } else {
        0
      }
    case '+' | '-' =>
      val pos = s.indexOf(':')
      if (pos == -1) {
        throw new ParseException(s"Failed to parse timestamp: invalid offset value: '$s'")
      } else {
        val hours = s.substring(1, pos)
        val minutes = s.substring(pos + 1)
        val r = hours.toLong * 3600 + minutes.toLong * 60
        if (s(0) == '-') -r else r
      }
    case _ => throw new ParseException("Failed to parse timestamp.")
  }

  def normalizedTimestamp(seconds: Long, nanos: Int): Timestamp = {
    val (ns, nn) = if (nanos <= -NANOS_PER_SECOND || nanos >= NANOS_PER_SECOND) {
      (seconds + nanos / NANOS_PER_SECOND, nanos % NANOS_PER_SECOND)
    } else (seconds, nanos)

    val (ns2, nn2) =
      if (nn < 0) (seconds - 1, nanos + NANOS_PER_SECOND)
      else (ns, nn)

    checkValid(Timestamp(seconds = ns2, nanos = nn2))
  }
}
