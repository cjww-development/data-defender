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

package com.cjwwdev.security

import com.cjwwdev.security.deobfuscation.DeObfuscator
import com.cjwwdev.security.obfuscation.Obfuscator

object Implicits {
  implicit class ImplicitObfuscator[T](data: T)(implicit obfuscator: Obfuscator[T]) {
    def encrypt: String = obfuscator.encrypt(data)
  }

  implicit class ImplicitDeObfuscator[T](data: String)(implicit deObfuscator: DeObfuscator[T]) {
    def decrypt: Either[DecryptionError, T] = deObfuscator.decrypt(data)
  }
}
