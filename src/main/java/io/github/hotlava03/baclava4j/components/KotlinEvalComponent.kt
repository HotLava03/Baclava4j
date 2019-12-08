package io.github.hotlava03.baclava4j.components

import org.jetbrains.kotlin.script.jsr223.KotlinJsr223JvmLocalScriptEngine
import org.reactivestreams.Subscriber
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import javax.script.ScriptContext
import javax.script.ScriptEngineManager


object KotlinEvalComponent {
    private val IMPORTS = Arrays.asList("io.github.hotlava03.baclava4j.components.*",
            "io.github.hotlava03.baclava4j.commands.*", "io.github.hotlava03.baclava4j.*", "discord4j.core.*",
            "discord4j.core.event.*",
            "reactor.core.publisher.*", "java.lang.*", "java.io.*", "java.math.*",
            "java.time.*", "java.awt.*", "java.awt.image.*", "javax.imageio.*",
            "java.time.format.*", "kotlinx.coroutines.*", "kotlin.reflect.*", "kotlin.reflect.jvm.*", "kotlin.reflect.full.*",
            "kotlin.system.*", "kotlin.io.*", "kotlin.random.*", "kotlin.concurrent.*",
            "kotlin.properties.*")

    @JvmStatic
    fun eval(code: String, shortcuts: Map<String?, Any?>): Any {
        val engine = ScriptEngineManager().getEngineByExtension("kts") as KotlinJsr223JvmLocalScriptEngine
        for ((key, value) in shortcuts) engine.put(key, value)
        val scriptPrefix = buildString {
            for ((key, value) in engine.getBindings(ScriptContext.ENGINE_SCOPE)) {
                if ("." !in key) {
                    val name: String = value.javaClass.name
                    val bind = """val $key = bindings["$key"] as $name"""
                    appendln(bind)
                }
            }
        }
        val toEval = "import " + IMPORTS.joinToString("\nimport ") + "\n\n" + scriptPrefix + "\n// User code\n\n" + code
        println(toEval)
        try {
            val evaluated: Any? = engine.compileAndEval(toEval, engine.context)
            if (evaluated !== null) {
                when (evaluated) {
                    is Mono<*> -> evaluated.subscribe()
                    is Flux<*> -> evaluated.collectList().subscribe()
                    is Subscriber<*> -> return EvalResult(null, true)
                    else -> return EvalResult(evaluated, false)
                }
                return EvalResult(null, true)
            } else
                return EvalResult(null, true)
        } catch (t: Throwable) {
            return t.toString()
        }
        /*val result: Any?
        return try {
            result = engine.compileAndEval(toEval, engine.context)
            EvalResult(result, false)
        } catch (t: Throwable) {
            val stack: MutableList<String> = ArrayList()
            for (element in t.stackTrace) stack.add(element.toString())
            t.message + ": " + t.cause + "\nat " + java.lang.String.join("\nat ", stack)
        }*/
    }
}