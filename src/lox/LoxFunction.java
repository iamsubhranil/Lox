package lox;

import java.util.List;

class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Environment closure;

    LoxFunction(Stmt.Function declaration, Environment closure) {
        this.closure = closure;
        this.declaration = declaration;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.parameters.size(); i++) {
            Token decl = declaration.parameters.get(i);
            Object arg = arguments.get(i);
            if(arg instanceof Stmt.Function){
                Stmt.Function farg = (Stmt.Function)arg;
                Stmt.Function nfarg = new Stmt.Function(decl, farg.parameters, farg.body);
                interpreter.visitFunctionStmt(nfarg);
            }
            else {
                environment.define(decl.lexeme, arg);
            }
        }

        try {
            interpreter.executeBlock(declaration.body, environment);
        } catch (Return returnValue) {
            return returnValue.value;
        }

        return null;
    }

    @Override
    public int arity() {
        return declaration.parameters.size();
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + ">";
    }
}