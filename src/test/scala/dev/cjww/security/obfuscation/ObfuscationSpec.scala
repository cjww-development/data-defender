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

import dev.cjww.security.fixtures.TestModel
import org.joda.time.{DateTime, DateTimeZone}
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsValue, Json}

class ObfuscationSpec extends PlaySpec {

  trait Test[T] extends Obfuscators {
    val testValue: T
    val result: String
  }

  "Obfuscation" should {
    "obfuscate an int" in new Test[Int] {
      override val locale: String = "test"

      override val testValue: Int = 616
      override val result: String = encrypt(testValue)

      assert(testValue.toString != result)
    }

    "obfuscate a short" in new Test[Short] {
      override val locale: String = "test"

      override val testValue: Short = 1
      override val result: String   = encrypt(testValue)

      assert(testValue.toString != result)
    }

    "obfuscate a byte" in new Test[Byte] {
      override val locale: String = "test"

      override val testValue: Byte = 2
      override val result: String   = encrypt(testValue)

      assert(testValue.toString != result)
    }

    "obfuscate a long" in new Test[Long] {
      override val locale: String = "test"

      override val testValue: Long = 123456789123456789L
      override val result: String   = encrypt(testValue)

      assert(testValue.toString != result)
    }

    "obfuscate a float" in new Test[Float] {
      override val locale: String = "test"

      override val testValue: Float = 1.0F
      override val result: String   = encrypt(testValue)

      assert(testValue.toString != result)
    }

    "obfuscate a double" in new Test[Double] {
      override val locale: String = "test"

      override val testValue: Double = 1.01234
      override val result: String   = encrypt(testValue)

      assert(testValue.toString != result)
    }

    "obfuscate a big decimal" in new Test[BigDecimal] {
      override val locale: String = "test"

      override val testValue: BigDecimal = 1.07654321
      override val result: String        = encrypt(testValue)

      assert(testValue.toString != result)
    }

    "obfuscate a boolean" in new Test[Boolean] {
      override val locale: String = "test"

      override val testValue: Boolean = true
      override val result: String     = encrypt(testValue)

      assert(testValue.toString != result)
    }

    "obfuscate a string" in new Test[String] {
      override val locale: String = "test"

      override val testValue: String = "testString"
      override val result: String    = encrypt(testValue)

      assert(testValue != result)
    }

    "obfuscate a jsvalue" in new Test[JsValue] {
      override val locale: String = "test"

      override val testValue: JsValue = Json.parse("""{ "abc" : "xyz" }""")
      override val result: String     = encrypt(testValue)

      assert(testValue.toString != result)
    }

    "obfuscate a case class" in new Test[TestModel] {
      override val locale: String = "test"

      implicit val testModelObfuscation: Obfuscator[TestModel] = (value: TestModel) => obfuscate(Json.toJson(value))

      final val now = new DateTime(DateTimeZone.UTC)

      override val testValue: TestModel = TestModel(
        string   = "testString",
        int      = 616,
        dateTime = now
      )
      override val result: String = encrypt(testValue)

      assert(testValue.toString != result)
    }
  }
}
