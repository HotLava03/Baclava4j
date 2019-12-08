package io.github.hotlava03.baclava4j.components;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.script.ScriptEngine;

import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import reactor.core.Fuseable.QueueSubscription;
import reactor.core.publisher.Mono;

public class JavaEvalComponent {

    private static final String[] IMPORTS = new String[] { "io.github.hotlava03.baclava4j.components.*",
            "io.github.hotlava03.baclava4j.commands.*", "io.github.hotlava03.baclava4j.*", "discord4j.core.*",
            "discord4j.core.event.*", "discord4j.core.object.*", "discord4j.core.object.entity.*",
            "reactor.core.publisher.*", "java.lang.*", "java.io.*", "java.math.*", "java.eval.*", "java.eval.concurrent.*",
            "java.time.*", "java.awt.*", "java.awt.image.*", "javax.imageio.*", "java.time.format.*" };

    public static Object eval(String code, Map<String, Object> shortcuts) {
        ScriptEngine engine = new GroovyScriptEngineImpl();

        for (Entry<String, Object> shortcut : shortcuts.entrySet())
            engine.put(shortcut.getKey(), shortcut.getValue());
        
        List<String> imports = Arrays.asList(IMPORTS);

        ScheduledFuture<Object> future = Executors.newScheduledThreadPool(1, newThread("JavaEvalThread"))
                .schedule(() -> engine.eval("import " + String.join(";\nimport ", imports) + ";\n" + code), 0, TimeUnit.MILLISECONDS);

        final StringWriter outString = new StringWriter();
        final PrintWriter outWriter = new PrintWriter(outString);
        engine.getContext().setWriter(outWriter);

        final StringWriter errorString = new StringWriter();
        final PrintWriter errorWriter = new PrintWriter(errorString);
        engine.getContext().setErrorWriter(errorString);

        Object result = null;

        try {
            result = future.get(10, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            errorWriter.println(e.getCause().toString());
        } catch (TimeoutException | InterruptedException e) {
            future.cancel(true);
            return "Interrupted. Cause: " + e.getCause();
        }

        if (!errorString.toString().isEmpty())
            return errorString.toString();

        if (result instanceof Mono<?>)
            ((Mono<?>) result).subscribe();
        else if (result instanceof QueueSubscription)
            return new EvalResult(null, true); // when using .subscribe, we might just count it as void    
        else if (result != null)
            if (!result.toString().contains("reactor.core.publisher.LambdaMonoSubscriber")) // LambdaMonoSubscriber is a package-private class
                return new EvalResult(result.toString(), false);    

        if (!outString.toString().isEmpty())
            return new EvalResult(outString.toString(), true);
        else
            return new EvalResult(null, true); 
    }

    public static ThreadFactory newThread(String name) {
        return (run) -> {
            Thread thread = new Thread(run, name);
            thread.setDaemon(false);
            thread.setUncaughtExceptionHandler((final Thread t, final Throwable throwable) -> {
                System.out.println("[" + t.getName() + "]|[Error] " + throwable.getMessage());
            });
            return thread;
        };
    }
}