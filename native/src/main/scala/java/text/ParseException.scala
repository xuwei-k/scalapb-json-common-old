package java.text

class ParseException(message: String, position: Int) extends Exception(message + " " + position)
