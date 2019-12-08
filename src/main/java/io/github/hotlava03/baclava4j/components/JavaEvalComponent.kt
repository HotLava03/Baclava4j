package io.github.hotlava03.baclava4j.components

import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl
import reactor.core.Fuseable.QueueSubscription
import reactor.core.publisher.Mono
import java.io.PrintWriter
import java.io.StringWriter
import java.util.concurrent.*
import javax.script.ScriptEngine

object JavaEvalComponent {
    private val IMPORTS = arrayOf("io.github.hotlava03.baclava4j.components.*",
            "io.github.hotlava03.baclava4j.commands.*", "io.github.hotlava03.baclava4j.*", "discord4j.core.*",
            "discord4j.core.event.*", "discord4j.core.object.*", "discord4j.core.object.entity.*",
            "reactor.core.publisher.*", "java.lang.*", "java.io.*", "java.math.*", "java.eval.*", "java.eval.concurrent.*",
            "java.time.*", "java.awt.*", "java.awt.image.*", "javax.imageio.*", "java.time.format.*")

    @JvmStatic
    fun eval(code: String, shortcuts: Map<String?, Any?>): Any {
        val engine: ScriptEngine = GroovyScriptEngineImpl()
        for ((key, value) in shortcuts) engine.put(key, value)
        val future = Executors.newScheduledThreadPool(1, newThread("JavaEvalThread"))
                .schedule<Any>({ engine.eval("import " + IMPORTS.joinToString(";\nimport ") + ";\n" + code) }, 0, TimeUnit.MILLISECONDS)
        val outString = StringWriter()
        val outWriter = PrintWriter(outString)
        engine.context.writer = outWriter
        val errorString = StringWriter()
        val errorWriter = PrintWriter(errorString)
        engine.context.errorWriter = errorString
        var result: Any? = null
        try {
            result = future[10, TimeUnit.SECONDS]
        } catch (e: ExecutionException) {
            errorWriter.println(e.cause.toString())
        } catch (e: TimeoutException) {
            future.cancel(true)
            return "Interrupted. Cause: " + e.cause
        } catch (e: InterruptedException) {
            future.cancel(true)
            return "Interrupted. Cause: " + e.cause
        }
        if (errorString.toString().isNotEmpty()) return errorString.toString()
        if (result is Mono<*>) result.subscribe() else if (result is QueueSubscription<*>) return EvalResult(null, true) // when using .subscribe, we might just count it as void
        else if (result != null) if (!result.toString().contains("reactor.core.publisher.LambdaMonoSubscriber")) // LambdaMonoSubscriber is a package-private class
            return EvalResult(result.toString(), false)
        return if (outString.toString().isNotEmpty()) EvalResult(outString.toString(), true) else EvalResult(null, true)
    }

    private fun newThread(name: String?): ThreadFactory {
        return ThreadFactory { run: Runnable? ->
            val thread = Thread(run, name)
            thread.isDaemon = false
            thread.uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { t: Thread, throwable: Throwable -> println("[" + t.name + "]|[Error] " + throwable.message) }
            thread
        }
    }
}