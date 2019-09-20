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

import com.cjwwdev.security.{DecryptionError, Logging, SecurityConfiguration}
import javax.crypto.Cipher
import org.apache.commons.codec.binary.Base64
import play.api.libs.json.{JsPath, Json, JsonValidationError, Reads}

import scala.reflect.ClassTag
import scala.util.Try

trait DeObfuscation extends SecurityConfiguration with Logging {

  private def fetchError[T](throwable: Throwable)(implicit tag: ClassTag[T]): Left[DecryptionError, T] = {
    val error = DecryptionError(throwable.getMessage, this.getClass.getCanonicalName)
    error.logError[T]
    Left(error)
  }

  private def readableJsError[T](errors: Seq[(JsPath, Seq[JsonValidationError])])(implicit tag: ClassTag[T]): DecryptionError = {
    val seq = errors map { case (path, error) => Json.obj(path.toJsonString.replace("obj.", "") -> error.map(_.message).mkString) }
    val decryptionError = DecryptionError(Json.prettyPrint(seq.foldLeft(Json.obj())((obj, a) => obj.deepMerge(a))), this.getClass.getCanonicalName)
    decryptionError.logValidateError[T]
    decryptionError
  }

  private def bytesToType[T](bytes: Array[Byte])(implicit reads: Reads[T], tag: ClassTag[T]): Either[DecryptionError, T] = {
    Json.parse(new String(bytes)).validate[T].fold(
      err  => {
        val errs = err.map { case (a, b) => (a, b.toSeq) }.toSeq
        Left(readableJsError[T](errs))
      },
      data => Right(data)
    )
  }

  def deObfuscate[T](value: String)(implicit reads: Reads[T], tag: ClassTag[T]): Either[DecryptionError, T] = {
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
    Try(cipher.doFinal(Base64.decodeBase64(value))).fold(fetchError[T], bytesToType[T])
  }

  def decrypt[T](value: String)(implicit deObfuscator: DeObfuscator[T]): Either[DecryptionError, T] = deObfuscator.decrypt(value)
}
