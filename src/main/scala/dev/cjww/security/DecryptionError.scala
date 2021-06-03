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

import scala.reflect.ClassTag
import scala.util.control.NoStackTrace

case class DecryptionError(msg: String, locale: String) extends NoStackTrace with Logging {
  def logError[T](implicit tag: ClassTag[T]): Unit = {
    logger.error(s"[$locale] - the input string has failed decryption into type $tag - reason: $msg")
  }

  def logValidateError[T](implicit tag: ClassTag[T]): Unit = {
    logger.error(s"[$locale] - the json could not be read into type $tag - reason: $msg")
  }
}
