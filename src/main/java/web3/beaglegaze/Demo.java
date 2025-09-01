package web3.beaglegaze;

public class Demo {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Demo.class);

    @PayPerCall(price = 1L)
    public void greet(String string) {
        logger.info("Demo.greet called with: {}", string);
    }
    

}
