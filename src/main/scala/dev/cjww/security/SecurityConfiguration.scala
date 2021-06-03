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

package dev.cjww.security

import com.typesafe.config.{Config, ConfigFactory}

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import scala.util.Try

trait SecurityConfiguration {
  self: Logging =>

  val locale: String

  private val LENGTH: Int = 16

  private val securityConfig: Config = ConfigFactory
    .load()
    .getConfig("data-defender")

  lazy val KEY: String = Try(securityConfig.getString(s"$locale.key")).getOrElse {
    logger.warn(s"Could not find custom key for $locale, using the default key instead")
    securityConfig.getString("default.key")
  }

  lazy val cipher: Cipher = Cipher
    .getInstance("AES/ECB/PKCS5Padding")

  lazy val secretKeySpec: SecretKeySpec = {
    new SecretKeySpec(
      util.Arrays.copyOf(
        MessageDigest
          .getInstance("SHA-512")
          .digest(s"$KEY".getBytes(StandardCharsets.UTF_8)),
        LENGTH
      ),
      "AES"
    )
  }
}
