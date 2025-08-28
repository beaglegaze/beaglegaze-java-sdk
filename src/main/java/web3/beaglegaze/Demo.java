package web3.beaglegaze;

import web3.beaglegaze.PayPerCall;

public class Demo {

    @PayPerCall(price = 1L)
    public void greet(String string) {
        System.out.println("Demo.greet called with: " + string);
    }
    

}
