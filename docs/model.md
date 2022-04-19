# xmlify

## Model

This module provides you with a data model to serialize and operate XML entities (elements and documents)

## Element

`Element` represents an XML element. There are 2 kinds of elements:

- `LeafElement`, which represent XML elements that contain a value.
- `TreeElement`, which represent XML elements that are composed by other nested XML elements

```xml
<!--Leaf Element-->
<name>Jane Doe</name>

<!--Tree Element-->
<person>
  <name>John Doe</name>
  <age>42</age>
</person>
```

All XML elements have a name. XML elements can have a set of attributes

```xml
<!--A xml element named 'food' with 2 attributes: 'kind' and 'quantity'-->
<food kind="fruit" quantity="10">Orange</food>

<!--A xml element without attributes-->
<food>Fried Chicken</food>
```

XML elements can be collapsed when they do not have a value (for `LeafElement`, the value is
`null` and for `TreeElement`, the element has no nested elements). When an element is collapsed, it is rendered
differently

```xml
<!--Collapsed Element-->
<vehicles/>

<!--Non Collapsed Tree Element-->
<vehicles>
  <!--Collapsed Leaf and Tree Elements have the same appearance-->
  <boat/>
  <car/>
</vehicles>
```

Some special characters are encoded differently in XML:
- `&` is encoded to `&amp;`
- `"` is encoded to `&quot;`
- `'` is encoded to `&apos;`
- `<` is encoded to `&lt;`
- `>` is encoded to `&gt;`

## Documents

`Document` represents an XML document. An XML document is composed by:

- a version (either `1.0`, `1.1` or `2.0`)
- an encoding
- a root element

```xml
<?xml version="1.0" encoding="UTF-8"?>
<vehicles>
  <boat/>
  <car/>
</vehicles>
```

## API

- [KDocs](https://leomartins1999.github.io/xmlify/xmlify/com.github.leomartins1999.xmlify.model/index.html)
- Examples:
  - [Leaf Element](../src/samples/kotlin/model/LeafElement.kt)
  - [Tree Element](../src/samples/kotlin/model/TreeElement.kt)
  - [Document](../src/samples/kotlin/model/Document.kt)
