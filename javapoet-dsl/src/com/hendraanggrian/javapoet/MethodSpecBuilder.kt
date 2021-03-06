package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier

/** Returns a method with custom initialization block. */
fun buildMethodSpec(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
    MethodSpecBuilder(MethodSpec.methodBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns a constructor method with custom initialization block. */
fun buildConstructorMethodSpec(builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
    MethodSpecBuilder(MethodSpec.constructorBuilder())
        .also { builder?.invoke(it) }
        .build()

class MethodSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: MethodSpec.Builder) :
    SpecBuilder<MethodSpec>(),
    JavadocSpecBuilder,
    AnnotatedSpecBuilder,
    ModifieredSpecBuilder,
    TypeVariabledSpecBuilder,
    ControlFlowedSpecBuilder,
    CodedSpecBuilder {

    override fun javadoc(format: String, vararg args: Any) {
        nativeBuilder.addJavadoc(format, *args)
    }

    override fun javadoc(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.addJavadoc(buildCodeBlock(builder))
    }

    override fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    inline fun <reified T> annotation(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        annotation(T::class.java, builder)

    override var modifiers: Collection<Modifier>
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addModifiers(*value.toTypedArray())
        }

    override var typeVariable: TypeVariableName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addTypeVariable(value)
        }

    override var typeVariables: Iterable<TypeVariableName>
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addTypeVariables(value)
        }

    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    fun returns(type: Type) {
        nativeBuilder.returns(type)
    }

    inline fun <reified T> returns() = returns(T::class.java)

    fun parameter(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null) {
        nativeBuilder.addParameter(buildParameterSpec(type, name, builder))
    }

    fun parameter(type: Type, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null) {
        nativeBuilder.addParameter(buildParameterSpec(type, name, builder))
    }

    inline fun <reified T> parameter(name: String, noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        parameter(T::class.java, name, builder)

    var varargs: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.varargs(value)
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

    inline fun <reified T> exception() = exception(T::class.java)

    override fun code(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    override fun code(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.addCode(buildCodeBlock(builder))
    }

    fun comment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    fun defaultValue(format: String, vararg args: Any) {
        nativeBuilder.defaultValue(format, *args)
    }

    override fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    override fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    override fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endControlFlow(format: String, vararg args: Any) {
        nativeBuilder.endControlFlow(format, *args)
    }

    override fun statement(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    override fun statement(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.addStatement(buildCodeBlock(builder))
    }

    override fun build(): MethodSpec = nativeBuilder.build()
}