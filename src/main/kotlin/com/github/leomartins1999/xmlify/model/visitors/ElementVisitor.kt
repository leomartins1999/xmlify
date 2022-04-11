package com.github.leomartins1999.xmlify.model.visitors

import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement

interface ElementVisitor {
    fun visit(element: LeafElement) {}
    fun visit(element: TreeElement): Boolean = true
    fun endVisit(element: TreeElement) {}
}
