package robinh.s2qltp;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    ShoppingCartTest.class,
    ShoppingCartPromoTest.class,
    OrderServiceTest.class
})
class EcommerceTestSuite {
}
