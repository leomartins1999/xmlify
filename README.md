# xml-for-dummies

Library for JVM applications to serialize objects to XML

## How to use it

Add the `xml-for-dummies` dependency

```
# maven
<dependency>
    <groupId>com.github.leomartins1999</groupId>
    <artifactId>xml-for-dummies</artifactId>
    <version>1.0.0</version>
</dependency>

# gradle
implementation("com.github.leomartins1999:xml-for-dummies:1.0.0")
```

And use our API to serialize objects to XML

```kotlin
val obj = object {
    val name = "John Doe"
    val age = 23
}

val xml: XMLEntity = xml { obj }

"""
  <obj>
    <name>John Doe</name>
    <age>23</age>
  </obj>
"""
println(xml.ser())
```
