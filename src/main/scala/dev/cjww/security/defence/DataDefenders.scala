package dev.cjww.security.defence

import dev.cjww.security.DecryptionError
import play.api.libs.json.{JsObject, JsValue, Json}

trait DataDefenders extends DataDefence {

  implicit val stringDefense: DataDefender[String] = new DataDefender[String] {
    override def encrypt(value: String): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, String] = deObfuscate[String](value)
  }

  implicit val intDefense: DataDefender[Int] = new DataDefender[Int] {
    override def encrypt(value: Int): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, Int] = deObfuscate[Int](value)
  }

  implicit val shortDefense: DataDefender[Short] = new DataDefender[Short] {
    override def encrypt(value: Short): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, Short] = deObfuscate[Short](value)
  }

  implicit val byteDefence: DataDefender[Byte] = new DataDefender[Byte] {
    override def encrypt(value: Byte): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, Byte] = deObfuscate[Byte](value)
  }

  implicit val longDefense: DataDefender[Long] = new DataDefender[Long] {
    override def encrypt(value: Long): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, Long] = deObfuscate[Long](value)
  }

  implicit val floatDefense: DataDefender[Float] = new DataDefender[Float] {
    override def encrypt(value: Float): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, Float] = deObfuscate[Float](value)
  }

  implicit val doubleDefense: DataDefender[Double] = new DataDefender[Double] {
    override def encrypt(value: Double): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, Double] = deObfuscate[Double](value)
  }

  implicit val bigDecimalDefense: DataDefender[BigDecimal] = new DataDefender[BigDecimal] {
    override def encrypt(value: BigDecimal): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, BigDecimal] = deObfuscate[BigDecimal](value)
  }

  implicit val booleanDefense: DataDefender[Boolean] = new DataDefender[Boolean] {
    override def encrypt(value: Boolean): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, Boolean] = deObfuscate[Boolean](value)
  }

  implicit val jsonDefense: DataDefender[JsValue] = new DataDefender[JsValue] {
    override def encrypt(value: JsValue): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, JsValue] = deObfuscate[JsValue](value)
  }

  implicit val jsObjectDefense: DataDefender[JsObject] = new DataDefender[JsObject] {
    override def encrypt(value: JsObject): String = obfuscate(Json.toJson(value))
    override def decrypt(value: String): Either[DecryptionError, JsObject] = deObfuscate[JsObject](value)
  }
}
