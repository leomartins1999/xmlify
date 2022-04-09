package com.github.leomartins1999.xmlfordummies.xml

interface ElementVisitor {
    fun visit(element: LeafElement)
    fun visit(element: TreeElement): Boolean
    fun endVisit(element: TreeElement)
}
