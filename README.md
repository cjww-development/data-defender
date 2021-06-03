[![Apache-2.0 license](http://img.shields.io/badge/license-Apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

# data-defender


To utilise this library add this to your sbt build file

```sbtshell
"dev.cjww.libs" % "data-defender_2.13" % "x.x.x" 
```

Obfuscation and de-obfuscation provide functionality to encrypt type `T` to a `String` and decrypt `String` into type `T`.

```scala
    import com.cjwwdev.security.obfuscation.Obfuscation
    
    val encString: String = Obfuscation.encrypt(616)
```

```scala
    import com.cjwwdev.security.deobfuscation.DeObfuscation
    
    val decType: Boolean = DeObfuscation.decrypt[Boolean]("some-padded-string")
```

**Need a encrypt a different type?**

Both Obfuscation and DeObfuscation common obfuscators and de-obfuscators that can encrypt and decrypt common types such as `String`, `Int`, `Boolean` and others. 

But lets say you want to encrypt something that is covered by the common obfuscators. You have to define your own obfuscator. 
```scala
    case class ExampleModel(str: String, int: Int)
    
    val testModel = ExampleModel("testString", 616)
    
    implicit val exampleModelObfuscator: Obfuscator[ExampleModel] = new Obfuscator[ExampleModel] {
      override def encrypt(value: ExampleModel): String = Obfuscation.obfuscateJson(Json.toJson(value))
    }
    
    Obfuscation.encrypt(testModel)
```

Similar if you want to decrypt your example model.
```scala
    case class ExampleModel(str: String, int: Int)
    
    val testEncString = "example-padded-string"
    
    implicit val exampleModelDeObfuscator: DeObfuscator[ExampleModel] = new DeObfuscator[ExampleModel] {
      override def decrypt(value: String): Either[ExampleModel, DecryptionError] = DeObfuscation.deObfuscate[ExampleModel](value)
    }
    
    DeObfuscation.decrypt[ExampleModel](testEncString)
```



### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")
