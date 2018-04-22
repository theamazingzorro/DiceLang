package dice.program;

@SuppressWarnings("serial")
public class Return extends Throwable {

    final int val;

    public Return(int val) {
        this.val = val;
    }

    public int getVal() {
        return this.val;
    }

}
