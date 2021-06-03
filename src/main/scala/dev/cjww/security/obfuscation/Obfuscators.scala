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

package dev.cjww.security.obfuscation

import play.api.libs.json.{JsObject, JsValue, Json}

trait Obfuscators extends Obfuscation {
  implicit val stringObs: Obfuscator[String]               = (value: String)     => obfuscate(Json.toJson(value))
  implicit val intObs: Obfuscator[Int]                     = (value: Int)        => obfuscate(Json.toJson(value))
  implicit val shortObs: Obfuscator[Short]                 = (value: Short)      => obfuscate(Json.toJson(value))
  implicit val byteObs: Obfuscator[Byte]                   = (value: Byte)       => obfuscate(Json.toJson(value))
  implicit val longObs: Obfuscator[Long]                   = (value: Long)       => obfuscate(Json.toJson(value))
  implicit val floatObfuscate: Obfuscator[Float]           = (value: Float)      => obfuscate(Json.toJson(value))
  implicit val doubleObfuscate: Obfuscator[Double]         = (value: Double)     => obfuscate(Json.toJson(value))
  implicit val bigDecimalObfuscate: Obfuscator[BigDecimal] = (value: BigDecimal) => obfuscate(Json.toJson(value))
  implicit val booleanObfuscate: Obfuscator[Boolean]       = (value: Boolean)    => obfuscate(Json.toJson(value))
  implicit val jsonObfuscate: Obfuscator[JsValue]          = (value: JsValue)    => obfuscate(value)
  implicit val jsObjectObfuscate: Obfuscator[JsObject]     = (value: JsObject)   => obfuscate(value)
}
