class A {
    public C m1() {
        return new C();
    }
}
class B extends A {

}
class C extends A{}
public class TestCode {
    public static void main (String [] args) {
    System.out.print("DONE");
    }
    /*C c1 = (C)new A();       // compiles, but fails at runtime 

    // C c2 = (C)new B();    // doesn't compile

    C c3 = (C)(A)new B();   // compiles, because we go through the type A first*/

}