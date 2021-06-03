/*
 * Copyright 2021 CJWW Development
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.typesafe.config.ConfigFactory

import scala.util.Try

val libraryName: String = "data-defender"

val btVersion: String = Try(ConfigFactory.load.getString("version")).getOrElse("0.1.0-local")

val dependencies: Seq[ModuleID] = Seq(
  "ch.qos.logback"          % "logback-core"       % "1.2.3",
  "ch.qos.logback"          % "logback-classic"    % "1.2.3",
  "com.typesafe"            % "config"             % "1.4.1",
  "commons-codec"           % "commons-codec"      % "1.15",
  "com.typesafe.play"      %% "play-json"          % "2.9.2",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0"   % Test,
  "joda-time"               % "joda-time"          % "2.10.10" % Test
)

lazy val library = Project(libraryName, file("."))
  .settings(
    version                              :=  btVersion,
    scalaVersion                         :=  "2.13.6",
    semanticdbEnabled                    :=  true,
    semanticdbVersion                    :=  scalafixSemanticdb.revision,
    organization                         :=  "dev.cjww.libs",
    libraryDependencies                  ++= dependencies,
    githubTokenSource                    := (if (Try(ConfigFactory.load.getBoolean("local")).getOrElse(true)) {
      TokenSource.GitConfig("github.token")
    } else {
      TokenSource.Environment("GITHUB_TOKEN")
    }),
    githubOwner                          :=  "cjww-development",
    githubRepository                     :=  libraryName,
    scalacOptions                        ++= Seq(
      "-unchecked",
      "-deprecation",
      "-Wunused"
    ),
    Test / testOptions                   +=  Tests.Argument("-oF"),
    Test / fork                          :=  true,
    Test / javaOptions                   :=  Seq(
      "-Ddata-defender.test.key=testKey",
      "-Ddata-defender.default.key=testKey"
    )
  )
