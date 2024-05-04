public class Pair<non, term> {
    private non nonTerminal;
    private term terminal;

    public Pair(non nonTerminal, term terminal) {
        this.nonTerminal = nonTerminal;
        this.terminal = terminal;
    }

    public non getnonTerminal() {
        return nonTerminal;
    }

    public term getterminal() {
        return terminal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        if (nonTerminal != null ? !nonTerminal.equals(pair.nonTerminal) : pair.nonTerminal != null) return false;
        return terminal != null ? terminal.equals(pair.terminal) : pair.terminal == null;
    }

    @Override
    public int hashCode() {
        int result = nonTerminal != null ? nonTerminal.hashCode() : 0;
        result = 31 * result + (terminal != null ? terminal.hashCode() : 0);
        return result;
    }
}
