package com.java17.programs.redifined.all;

public class ExecutionOrderJavaProgramWithInheritance {
    public static void main(String[] args) {
/**
 * an extended example with inheritance to show how the execution flow works when a parent (superclass) and a child (subclass) are involved.
 *
 * This will illustrate:
 *
 * Static blocks and variables from both classes
 *
 * Instance blocks and variables from both classes
 *
 * Constructor chaining from superclass to subclass
 *
 */
            System.out.println("Main method started\n");

            System.out.println("Creating first object:");
            Child c1 = new Child();

            System.out.println("\nCreating second object:");
            Child c2 = new Child();

            System.out.println("\nMain method ended");
        }
    /**
     * Main method started
     *
     * Creating first object:
     * Parent static variable initialized
     * Parent static block executed
     * Child static variable initialized
     * Child static block executed
     * Parent instance variable initialized
     * Parent instance block executed
     * Parent constructor executed
     * Child instance variable initialized
     * Child instance block executed
     * Child constructor executed
     *
     * Creating second object:
     * Parent instance variable initialized
     * Parent instance block executed
     * Parent constructor executed
     * Child instance variable initialized
     * Child instance block executed
     * Child constructor executed
     *
     * Main method ended
     */


}
class Parent {

    static int staticParentVar = staticParentMethod();

    static {
        System.out.println("Parent static block executed");
    }

    int instanceParentVar = instanceParentMethod();

    {
        System.out.println("Parent instance block executed");
    }

    Parent() {
        System.out.println("Parent constructor executed");
    }

    static int staticParentMethod() {
        System.out.println("Parent static variable initialized");
        return 1;
    }

    int instanceParentMethod() {
        System.out.println("Parent instance variable initialized");
        return 2;
    }
}

class Child extends Parent {

    static int staticChildVar = staticChildMethod();

    static {
        System.out.println("Child static block executed");
    }

    int instanceChildVar = instanceChildMethod();

    {
        System.out.println("Child instance block executed");
    }

    Child() {
        System.out.println("Child constructor executed");
    }

    static int staticChildMethod() {
        System.out.println("Child static variable initialized");
        return 10;
    }

    int instanceChildMethod() {
        System.out.println("Child instance variable initialized");
        return 20;
    }


}
/**
 * 🧠 Execution Flow Summary (with Inheritance):
 * Static members (variables and blocks) of Parent are initialized first.
 *
 * Static members of Child come after Parent's.
 *
 * Static blocks run only once when the class is first loaded.
 *
 * main() starts.
 *
 * For each new Child():
 *
 * Parent's instance variables → Parent's instance block → Parent constructor
 *
 * Child's instance variables → Child's instance block → Child constructor
 *
 * main() ends.
 *
 */