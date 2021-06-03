/*
 * Copyright 2019 CJWW Development
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

package dev.cjww.security.fixtures

import org.joda.time.{DateTime, DateTimeZone}
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class TestModel(string: String, int: Int, dateTime: DateTime)

object TestModel {
  implicit val dateTimeRead: Reads[DateTime] = (__ \ "$date").read[Long] map {
    new DateTime(_, DateTimeZone.UTC)
  }

  implicit val dateTimeWrite: Writes[DateTime] = Writes[DateTime] {
    dateTime => Json.obj("$date" -> dateTime.getMillis)
  }

  implicit val standardFormat: OFormat[TestModel] = (
    (__ \ "string").format[String] and
    (__ \ "int").format[Int] and
    (__ \ "dateTime").format[DateTime](dateTimeRead)(dateTimeWrite)
  )(TestModel.apply, unlift(TestModel.unapply))
}

case class TestModelTwo(string: String, str: String)

object TestModelTwo {
  implicit val standardFormat: OFormat[TestModelTwo] = (
    (__ \ "string").format[String] and
    (__ \ "str").format[String]
  )(TestModelTwo.apply, unlift(TestModelTwo.unapply))
}
