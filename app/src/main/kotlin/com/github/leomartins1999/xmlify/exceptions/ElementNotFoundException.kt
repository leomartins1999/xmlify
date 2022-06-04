package com.github.leomartins1999.xmlify.exceptions

import com.github.leomartins1999.xmlify.model.ElementID

class ElementNotFoundException(id: ElementID) : RuntimeException("Requested element $id was not found!")
