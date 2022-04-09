# xmlify

Still using XML in 2022? No worries!

With this simple library for the JVM you'll be serializing and operating XML in no time!

## How to use it

Add the `xmlify` dependency

```
# maven
<dependency>
    <groupId>com.github.leomartins1999</groupId>
    <artifactId>xmlify</artifactId>
    <version>1.0.0</version>
</dependency>

# gradle
implementation("com.github.leomartins1999:xmlify:1.0.0")
```

And use our API to serialize objects to XML

```kotlin
val element = element("potatoes")
println(element.render())
/*
    <potatoes>
    </potatoes>
 */

val leafElement = element("name", "Jane Doe")
println(leafElement.render())
/*
    <name>Jane Doe</name>
 */

val document = document(elements = listOf(element, leafElement))
println(document.render())
/*
    <?xml version="1.0" encoding="UTF-8"?>
    <potatoes>
    </potatoes>
    <name>Jane Doe</name>
 */
```
