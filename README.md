# xmlao

Still using XML in 2022? No worries!

With this simple library for the JVM you'll be serializing and operating XML in no time!

## How to use it

Add the `xmlao` dependency

```
# maven
<dependency>
    <groupId>com.github.leomartins1999</groupId>
    <artifactId>xmlao</artifactId>
    <version>1.0.0</version>
</dependency>

# gradle
implementation("com.github.leomartins1999:xmlao:1.0.0")
```

And use our API to serialize objects to XML

```kotlin
val element = element("potatoes")
println(element.render())
/*
    <potatoes>
    </potatoes>
 */

val valueElement = element("name", "Jane Doe")
println(valueElement.render())
/*
    <name>Jane Doe</name>
 */

val document = document(elements = listOf(element, valueElement))
println(document.render())
/*
    <?xml version="1.0" encoding="UTF-8"?>
    <potatoes>
    </potatoes>
    <name>Jane Doe</name>
 */
```
