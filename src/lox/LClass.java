package lox;

import java.util.Collections;
import java.util.List;

public class LClass {

    private final Environment present;
    private final String className;

    public LClass(LoxClass lx, Interpreter interpreter, List<Object> args, Environment closure){
        present = new Environment(closure);
        className = lx.getClassName();
        List<Stmt> body = lx.getStatements();
        int i = 0;
        while(i < body.size()){
            if(!(body.get(i) instanceof Stmt.Function))
                interpreter.executeBlock(Collections.singletonList(body.get(i)), present);
            else{
                Stmt.Function sf = (Stmt.Function)body.get(i);
                LoxFunction function = new LoxFunction(sf, present);
                present.define(sf.name.lexeme, function);
            }
            i++;
        }
        i = 0;
        while(i < body.size()){
            if(body.get(i) instanceof Stmt.Function){
                Token fname = ((Stmt.Function)body.get(i)).name;
                LoxFunction lf = (LoxFunction)present.get(fname);
                if(fname.lexeme.equals("init"))
                    lf.call(interpreter, args);
            }
            i++;
        }
    }

    public Object call(Expr.Call arguments){
        Interpreter i = new Interpreter(present);
        return i.visitCallExpr(arguments);
    }

    public Object get(Object classRef){
        System.out.println("Returning : "+present.get((Token)classRef));
        return present.get((Token)classRef);
    }

    public void set(Token name, Object value){
        present.assign(name, value);
    }

    @Override
    public String toString(){
        return "<class "+className+">";
    }

}
