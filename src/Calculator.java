import java.util.Stack;

/**
 * 使用了命令模式来封装加、减、乘、除操作。每个运算都被封装在一个实现了Command接口的内部类中。
 * <p>
 * 同时使用了两个栈，undoStack和redoStack来存储命令对象。
 * 每当执行一个新命令时，我们将其添加到undoStack中，并清空redoStack。
 * 当执行撤销操作（undo）时，我们从undoStack中弹出最近的命令，并执行其undo()方法，然后将它推到redoStack中。
 * 当执行重做操作（redo）时，我们从redoStack中弹出最近的命令，并执行其execute()方法，然后将它推回undoStack中。
 */
public class Calculator {

    /**
     * 当前最新值，相当于计算器按=符号后的值
     */
    private double currentNumber;
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    /**
     * 加运算
     * @param value
     */
    public void add(double value) {
        executeCommand(new AddCommand(value));
    }

    /**
     * 减运算
     * @param value
     */
    public void subtract(double value) {
        executeCommand(new SubtractCommand(value));
    }

    /**
     * 乘运算
     * @param value
     */
    public void multiply(double value) {
        executeCommand(new MultiplyCommand(value));
    }

    /**
     * 除运算
     * @param value
     */
    public void divide(double value) {
        executeCommand(new DivideCommand(value));
    }

    /**
     * 撤销
     */
    public void undo() {
        if (!undoStack.empty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    /**
     * 重做
     */
    public void redo() {
        if (!redoStack.empty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    public double getCurrentNumber() {
        return currentNumber;
    }

    /**
     * 运算操作（加减乘除）
     * @param command
     */
    private void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    /**
     * 接口类
     */
    private interface Command {
        void execute();

        void undo();
    }

    /**
     * 加操作的实现类
     */
    private class AddCommand implements Command {
        private final double operand;

        public AddCommand(double operand) {
            this.operand = operand;
        }

        @Override
        public void execute() {
            currentNumber += operand;
        }

        @Override
        public void undo() {
            currentNumber -= operand;
        }
    }

    /**
     * 减操作的实现类
     */
    private class SubtractCommand implements Command {
        private final double operand;

        public SubtractCommand(double operand) {
            this.operand = operand;
        }

        @Override
        public void execute() {
            currentNumber -= operand;
        }

        @Override
        public void undo() {
            currentNumber += operand;
        }
    }

    /**
     * 乘操作的实现类
     */
    private class MultiplyCommand implements Command {
        private final double operand;

        public MultiplyCommand(double operand) {
            this.operand = operand;
        }

        @Override
        public void execute() {
            currentNumber *= operand;
        }

        @Override
        public void undo() {
            currentNumber /= operand;
        }
    }

    /**
     * 除操作的实现类
     */
    private class DivideCommand implements Command {
        private final double operand;

        public DivideCommand(double operand) {
            this.operand = operand;
        }

        @Override
        public void execute() {
            if (operand == 0) {
                throw new IllegalArgumentException("Cannot divide by zero");
            }
            currentNumber /= operand;
        }

        @Override
        public void undo() {
            currentNumber *= operand;
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        // 第一次操作运算
        calculator.add(8);
        // 打印第一次操作运算后的最新值
        System.out.println(calculator.getCurrentNumber());

        // 第一次操作撤销
        calculator.undo();
        // 打印第一次操作撤销后的最新值
        System.out.println(calculator.getCurrentNumber());

        // 第一次操作重做
        calculator.redo();
        //打印第一次操作重做后的最新值
        System.out.println(calculator.getCurrentNumber());

        // 再次操作撤销
        calculator.undo();
        System.out.println(calculator.getCurrentNumber());
    }
}
