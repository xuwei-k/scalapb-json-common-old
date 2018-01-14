addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.21")

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.3.0")

addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.13")

addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.3.6")

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.7.0-rc6"
