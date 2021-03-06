# xmlify

## mapper

### xmlify

This module provides you a simple API to turn your objects into XML elements (using the data model from the `model` module)

Imagine you had the following data class `SoccerPlayer`:

```kotlin
data class SoccerPlayer(
    val name: String,
    val number: Int,
    val retired: Boolean
)
```

To map instances of `SoccerPlayer` to `Element`, you simply need to use the `xmlify` method:

```kotlin
val player = SoccerPlayer("Bernardo Silva", 20, false)
val objectElement = xmlify { player }
```

`xmlify` supports:
- classes/data classes (mapping the class properties to elements)
- enumerates
- collections

Samples: [API](../core/src/samples/kotlin/mapper/XMLify.kt) 

### xmlify annotations

`xmlify` provides you a set of annotations to customize the elements generated by the `xmlify` method:
- `@XMLName`, which customizes the name of generated elements. This can be specified both for classes and class properties
- `@XMLIgnore`, which makes it so that the annotated properties are not mapped to elements
- `@XMLAttribute`, which adds the specified attributes to the element. This can be specified both for classes and class properties
- `@XMLMapper`, which allows you to define a custom mapper to map the element. This can be specified both for classes and class properties

Samples: [Annotations](../core/src/samples/kotlin/mapper/XMLifyAnnotations.kt) 

### API

- [KDocs](https://leomartins1999.github.io/xmlify/xmlify/com.github.leomartins1999.xmlify.mapper/index.html)
- Examples:
    - [API](../core/src/samples/kotlin/mapper/XMLify.kt)
    - [Annotations](../core/src/samples/kotlin/mapper/XMLifyAnnotations.kt)
