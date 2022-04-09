package com.github.leomartins1999.xmlify.xml

interface ElementVisitor {
    fun visit(element: LeafElement)
    fun visit(element: TreeElement): Boolean
    fun endVisit(element: TreeElement)
}
