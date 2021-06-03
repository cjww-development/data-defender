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

package dev.cjww.security.defence

import dev.cjww.security.DecryptionError

import scala.annotation.implicitNotFound

@implicitNotFound("No DataDefender found for type ${T}. Try to implement an implicit DataDefender for type ${T}")
trait DataDefender[T] {
  def encrypt(value: T): String

  def decrypt(value: String): Either[DecryptionError, T]
}
