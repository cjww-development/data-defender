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

import dev.cjww.security.{Logging, SecurityConfiguration}
import org.apache.commons.codec.binary.Base64
import play.api.libs.json.JsValue

import java.nio.charset.StandardCharsets
import javax.crypto.Cipher

trait Obfuscation extends SecurityConfiguration with Logging {

  def obfuscate(json: JsValue): String = {
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
    Base64.encodeBase64URLSafeString(cipher.doFinal(
      json.toString.getBytes(StandardCharsets.UTF_8)
    ))
  }

  def encrypt[T](value: T)(implicit obfuscator: Obfuscator[T]): String = {
    obfuscator.encrypt(value)
  }
}
