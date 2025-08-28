package web3.beaglegaze;

public class Demo {

    @PayPerCall(price = 1L)
    public void greet(String string) {
        System.out.println("Demo.greet called with: " + string);
    }
    

}
