package compiler;

import com.sun.istack.internal.NotNull;

public class StringExecuter extends Executer {
    protected String str;

    public void setString(@NotNull String str) {
        this.str = str;
    }

    @Override
    public void execute() {

    }
}
