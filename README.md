
[![](https://jitpack.io/v/ToxicMushroom/JagTag-Kotlin.svg)](https://jitpack.io/#ToxicMushroom/JagTag-Kotlin)
## JagTag
JagTag is a simple - yet powerful and customizable - interpretted text parsing language!
Some methods are included in the built-in libraries, and additional methods can be defined that utilize the parser environment veriables, as well as the method's input.


## Kotlin example
```kotlin
import com.jagrosh.jagtag.*;
public class Example {
	fun main() {
		val parser = JagTag.newDefaultBuilder()
						.addMethod(Method("exclaim", complex = { (_: Environment, input: Array<String>) ->
            	    		input[0] + "!!!")	
						})
                		.build();
    	val result = parser.parse("{exclaim:{if:this|=|that|then:Foo Bar|else:Hello World}}");
		println(result);
	}
}
```
Result: `Hello World!!!`


## Maven
To use Maven with JagTag-kotlin, simply add the following to your pom.xml
```xml
  <dependency>
    <groupId>com.github.ToxicMushroom</groupId>
    <artifactId>JagTag-Kotlin</artifactId>
    <version>v0.6-beta.1</version>
  </dependency>
```

## Gradle
To use Gradle with JagTag-kotlin, simply add the following to your build.gradle
```gradle
	implementation 'com.github.ToxicMushroom:JagTag-Kotlin:v0.6-beta.1'
```

## Current Projects
Here are some other projects that utilize JagTag:
* [**Spectra (Discord Bot)**](https://github.com/jagrosh/Spectra) - Spectra uses JagTag in its customizable "tags" (user-created commands), and in welcome and leave messages for servers. (https://github.com/jagrosh/Spectra/blob/master/src/spectra/jagtag/libraries/Discord.java)

## Other Libraries
Below are JagTag-related libraries available for other languages or purposes:
* [**TheSharks/JagTag-JS**](https://github.com/TheSharks/JagTag-JS) - A JavaScript port of the JagTag text parsing language
* [**TheMonitorLizard/JagTagXML**](https://github.com/TheMonitorLizard/JagTagXML) - a JagTag to XML transpiler written in Java
