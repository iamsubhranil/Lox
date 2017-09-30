package lox;

import java.util.List;

public class LoxClass implements LoxCallable{

    private int ar = 0;
    private final Token className;
    private final List<Stmt> body;

    public LoxClass(Stmt.ClassDecl stmt){
        className = stmt.name;
        body = stmt.Body;
        int i = 0;
        while(i < body.size()){
            if(body.get(i) instanceof Stmt.Function){
                Stmt.Function f = (Stmt.Function)body.get(i);
                if(f.name.lexeme.equals("init")) {
                    LoxFunction lf = new LoxFunction(f, new Environment());
                    ar = lf.arity();
                }
            }
            i++;
        }
    }

    @Override
    public int arity() {
        return ar;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        return new LClass(this, interpreter, arguments, interpreter.environment);
    }

    public String getClassName(){
        return className.lexeme;
    }

    public List<Stmt> getStatements(){
        return body;
    }
}
