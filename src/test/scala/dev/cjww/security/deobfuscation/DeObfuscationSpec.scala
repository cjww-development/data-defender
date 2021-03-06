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

package dev.cjww.security.deobfuscation

import dev.cjww.security.DecryptionError
import dev.cjww.security.fixtures.TestModel
import dev.cjww.security.obfuscation.{Obfuscator, Obfuscators}
import org.joda.time.{DateTime, DateTimeZone}
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{Json, Reads}

class DeObfuscationSpec extends PlaySpec {

  trait Test[T] extends Obfuscators with DeObfuscators {
    val testValue: String
    val result: Either[DecryptionError, T]
  }

  "DeObfuscation" should {
    "succeed" when {
      "de-obfuscate an int" in new Test[Int] {
        override val locale: String = "test"

        override val testValue: String                    = encrypt[Int](616)
        override val result: Either[DecryptionError, Int] = decrypt[Int](testValue)

        result.fold(
          _ => fail("Decryption error instead of Integer"),
          _ mustBe 616
        )
      }

      "de-obfuscate a short" in new Test[Short] {
        override val locale: String = "test"

        override val testValue: String                      = encrypt(1)
        override val result: Either[DecryptionError, Short] = decrypt[Short](testValue)

        result.fold(
          _ => fail("Decryption error instead of Short"),
          _ mustBe 1
        )
      }

      "de-obfuscate a byte" in new Test[Byte] {
        override val locale: String = "test"

        override val testValue: String                     = encrypt(2)
        override val result: Either[DecryptionError, Byte] = decrypt[Byte](testValue)

        result.fold(
          _ => fail("Decryption error instead of Byte"),
          _ mustBe 2
        )
      }

      "de-obfuscate a long" in new Test[Long] {
        override val locale: String = "test"

        override val testValue: String                     = encrypt(123456789987654321L)
        override val result: Either[DecryptionError, Long] = decrypt[Long](testValue)

        result.fold(
          _ => fail("Decryption error instead of Long"),
          _ mustBe 123456789987654321L
        )
      }

      "de-obfuscate a float" in new Test[Float] {
        override val locale: String = "test"

        override val testValue: String                      = encrypt(1.0F)
        override val result: Either[DecryptionError, Float] = decrypt[Float](testValue)

        result.fold(
          _ => fail("Decryption error instead of Float"),
          _ mustBe 1.0F
        )
      }

      "de-obfuscate a double" in new Test[Double] {
        override val locale: String = "test"

        override val testValue: String                       = encrypt(1.1234)
        override val result: Either[DecryptionError, Double] = decrypt[Double](testValue)

        result.fold(
          _ => fail("Decryption error instead of Double"),
          _ mustBe 1.1234
        )
      }

      "de-obfuscate a big decimal" in new Test[BigDecimal] {
        override val locale: String = "test"

        override val testValue: String                           = encrypt(1.567)
        override val result: Either[DecryptionError, BigDecimal] = decrypt[BigDecimal](testValue)

        result.fold(
          _ => fail("Decryption error instead of BigDecimal"),
          _ mustBe 1.567
        )
      }

      "de-obfuscate a boolean" in new Test[Boolean] {
        override val locale: String = "test"

        override val testValue: String                        = encrypt(true)
        override val result: Either[DecryptionError, Boolean] = decrypt[Boolean](testValue)

        result.fold(
          _ => fail("Decryption error instead of Boolean"),
          x => assert(x)
        )
      }

      "de-obfuscate a string" in new Test[String] {
        override val locale: String = "test"

        override val testValue: String                       = encrypt("testString")
        override val result: Either[DecryptionError, String] = decrypt[String](testValue)

        result.fold(
          _ => fail("Decryption error instead of String"),
          _ mustBe "testString"
        )
      }

      "de-obfuscate a case class" in new Test[TestModel] {
        override val locale: String = "test"

        final val now = new DateTime(DateTimeZone.UTC)

        val model: TestModel = TestModel(
          string   = "testString",
          int      = 616,
          dateTime = now
        )

        implicit val testModelObfuscation: Obfuscator[TestModel] = (value: TestModel) => obfuscate(Json.toJson(value))

        implicit val testModelDeObfuscation: DeObfuscator[TestModel] = new DeObfuscator[TestModel] {
          implicit val reads: Reads[TestModel] = TestModel.standardFormat
          override def decrypt(value: String): Either[DecryptionError, TestModel] = deObfuscate[TestModel](value)
        }

        override val testValue: String = encrypt[TestModel](model)
        override val result: Either[DecryptionError, TestModel] = decrypt[TestModel](testValue)

        result.fold(
          _ => fail("Decryption error instead of TestModel"),
          _ mustBe TestModel(
            string   = "testString",
            int      = 616,
            dateTime = now
          )
        )
      }
    }

    "fail" when {
      "the input string isn't correctly padded" in new Test[Int] {
        override val locale: String = "test"

        override val testValue: String = "invalid-string"
        override val result: Either[DecryptionError, Int] = decrypt[Int](testValue)

        assert(result.isLeft)
        result.fold(
          _.msg mustBe "Input length must be multiple of 16 when decrypting with padded cipher",
          _ => fail("Decryption error should have been returned")
        )
      }

      "the input string decrypts into a incorrect structure" in new Test[TestModel] {
        override val locale: String = "test"

        implicit val testModelDeObfuscation: DeObfuscator[TestModel] = new DeObfuscator[TestModel] {
          implicit val reads: Reads[TestModel] = TestModel.standardFormat
          override def decrypt(value: String): Either[DecryptionError, TestModel] = deObfuscate[TestModel](value)
        }

        override val testValue: String = encrypt(616)
        override val result: Either[DecryptionError, TestModel] = decrypt[TestModel](testValue)

        assert(result.isLeft)
        result.fold(
          err => Json.parse(err.msg) mustBe Json.parse("""
                                                         |{
                                                         | "dateTime" : "error.path.missing",
                                                         | "int" : "error.path.missing",
                                                         | "string" : "error.path.missing"
                                                         |}
                                                          """.stripMargin),
          _ => fail("Decryption error should have been returned")
        )
      }
    }
  }
}
