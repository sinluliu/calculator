

/**
 * 使用了命令模式来封装加、减、乘、除操作。每个运算都被封装在一个实现了Command接口的内部类中。
 * <p>
 * 同时使用了两个栈，undoStack和redoStack来存储命令对象。
 * 每当执行一个新命令时，我们将其添加到undoStack中，并清空redoStack。
 * 当执行撤销操作（undo）时，我们从undoStack中弹出最近的命令，并执行其undo()方法，然后将它推到redoStack中。
 * 当执行重做操作（redo）时，我们从redoStack中弹出最近的命令，并执行其execute()方法，然后将它推回undoStack中。
 */
public class Calculator {


}
