package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.AnnotationManager
import com.hendraanggrian.javapoet.internal.ModifierManager
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier

fun buildParameterSpec(
    type: TypeName,
    name: String,
    builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpecBuilder = ParameterSpecBuilderImpl(ParameterSpec.builder(type, name)).also { builder?.invoke(it) }

fun buildParameterSpec(
    type: Type,
    name: String,
    builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpecBuilder = ParameterSpecBuilderImpl(ParameterSpec.builder(type, name)).also { builder?.invoke(it) }

interface ParameterSpecBuilder : AnnotationManager, ModifierManager, SpecBuilder<ParameterSpec> {

    val nativeBuilder: ParameterSpec.Builder

    override fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder).build())
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder).build())
    }

    override fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    override fun build(): ParameterSpec = nativeBuilder.build()
}

internal class ParameterSpecBuilderImpl(override val nativeBuilder: ParameterSpec.Builder) : ParameterSpecBuilder