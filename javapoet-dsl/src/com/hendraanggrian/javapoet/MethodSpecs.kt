package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.AnnotationManager
import com.hendraanggrian.javapoet.internal.JavadocManager
import com.hendraanggrian.javapoet.internal.ModifierManager
import com.hendraanggrian.javapoet.internal.ParameterSpecManager
import com.hendraanggrian.javapoet.internal.TypeVariableManager
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier

interface MethodSpecBuilder : JavadocManager,
    AnnotationManager,
    ModifierManager,
    TypeVariableManager,
    ParameterSpecManager {

    val nativeBuilder: MethodSpec.Builder

    override fun javadoc(format: String, vararg args: Any) {
        nativeBuilder.addJavadoc(format, *args)
    }

    override fun javadoc(codeBlock: CodeBlock) {
        nativeBuilder.addJavadoc(codeBlock)
    }

    override fun annotations(annotationSpecs: Iterable<AnnotationSpec>) {
        nativeBuilder.addAnnotations(annotationSpecs)
    }

    override fun annotation(annotationSpec: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotationSpec)
    }

    override fun annotation(annotation: ClassName) {
        nativeBuilder.addAnnotation(annotation)
    }

    override fun annotation(annotation: Class<*>) {
        nativeBuilder.addAnnotation(annotation)
    }

    override fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    override fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    override fun typeVariable(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    fun returns(returnType: TypeName) {
        nativeBuilder.returns(returnType)
    }

    fun returns(returnType: Type) {
        nativeBuilder.returns(returnType)
    }

    override fun parameter(type: TypeName, name: String, builder: ParameterSpecBuilder.() -> Unit) {
        nativeBuilder.addParameter(createParameter(type, name, builder))
    }

    override fun parameter(type: Type, name: String, builder: ParameterSpecBuilder.() -> Unit) {
        nativeBuilder.addParameter(createParameter(type, name, builder))
    }

    fun varargs() {
        nativeBuilder.varargs()
    }

    fun varargs(varargs: Boolean) {
        nativeBuilder.varargs(varargs)
    }

    fun exceptions(exceptions: Iterable<TypeName>) {
        nativeBuilder.addExceptions(exceptions)
    }

    fun exception(exception: TypeName) {
        nativeBuilder.addException(exception)
    }

    fun exception(exception: Type) {
        nativeBuilder.addException(exception)
    }

    fun code(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    fun code(block: CodeBlock) {
        nativeBuilder.addCode(block)
    }

    fun comment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    fun defaultValue(format: String, vararg args: Any) {
        nativeBuilder.defaultValue(format, *args)
    }

    fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    fun endControlFlow(format: String, vararg args: Any) {
        nativeBuilder.endControlFlow(format, *args)
    }

    fun statement(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    fun statement(block: CodeBlock) {
        nativeBuilder.addStatement(block)
    }
}

class MethodSpecBuilderImpl(override val nativeBuilder: MethodSpec.Builder) : MethodSpecBuilder