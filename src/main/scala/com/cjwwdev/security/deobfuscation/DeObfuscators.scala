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

package com.cjwwdev.security.deobfuscation

import play.api.libs.json.{JsObject, JsValue}

trait DeObfuscators extends DeObfuscation {
  implicit val intDeObfuscate: DeObfuscator[Int]               = (value: String) => deObfuscate[Int](value)
  implicit val shortDeObfuscate: DeObfuscator[Short]           = (value: String) => deObfuscate[Short](value)
  implicit val byteDeObfuscate: DeObfuscator[Byte]             = (value: String) => deObfuscate[Byte](value)
  implicit val longDeObfuscate: DeObfuscator[Long]             = (value: String) => deObfuscate[Long](value)
  implicit val floatDeObfuscate: DeObfuscator[Float]           = (value: String) => deObfuscate[Float](value)
  implicit val doubleDeObfuscate: DeObfuscator[Double]         = (value: String) => deObfuscate[Double](value)
  implicit val bigDecimalDeObfuscate: DeObfuscator[BigDecimal] = (value: String) => deObfuscate[BigDecimal](value)
  implicit val booleanDeObfuscate: DeObfuscator[Boolean]       = (value: String) => deObfuscate[Boolean](value)
  implicit val stringDeObfuscate: DeObfuscator[String]         = (value: String) => deObfuscate[String](value)
  implicit val jsonDeObfuscate: DeObfuscator[JsValue]          = (value: String) => deObfuscate[JsValue](value)
  implicit val jsObjectDeObfuscate: DeObfuscator[JsObject]     = (value: String) => deObfuscate[JsObject](value)
}
