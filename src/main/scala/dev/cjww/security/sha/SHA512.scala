package dev.cjww.security.sha

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object SHA512 extends SHA512

trait SHA512 {

  private val sha512 = MessageDigest.getInstance("SHA-512")

  def encrypt(value: String): String = {
    sha512.digest(value.getBytes(StandardCharsets.UTF_8))
      .map(byte => "%02x".format(byte))
      .mkString
  }
}
