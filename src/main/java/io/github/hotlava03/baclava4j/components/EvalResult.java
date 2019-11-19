package io.github.hotlava03.baclava4j.components;

public class EvalResult {

    private Object result;
    private boolean empty;
    
    public EvalResult(String result, boolean isEmpty) {
        this.result = result;
        this.empty = isEmpty;
    }

    public Object getResult() {
        return this.result;
    }

    public boolean isEmpty() {
        return this.empty;
    }
}