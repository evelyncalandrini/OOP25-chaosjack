package it.unibo.chaosjack.test;

import it.unibo.chaosjack.model.impl.DealerImpl;
import it.unibo.chaosjack.model.api.Card;
//import di junit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;




public class TestDealer {

    @Test
    void TestDealerHitsSixteen(){
        //ARRANGE: prima parte dove creo il dealer e gli dò dei valori
        DealerImpl dealer = new DealerImpl(); //creo un nuovo dealer
        dealer.addCard(()-> 10);//uso le lambda perchè dentro le parentesi dovrei mettere un tipo card e non un numero
        dealer.addCard(()-> 6);

        //ACT e ASSERT: in questo caso li mette insieme
        assertTrue(dealer.shouldHit());
    }
//devo capire quale stile guardare dal suo e capire perchè non mi vanno
    @Test
    void testdealerStandOnSeventeen(){

        DealerImpl dealer = new DealerImpl();
        dealer.addCard(()-> 10);
        dealer.addCard(()->7 );

        boolean result = dealer.shouldHit();
        assertFalse(result);
    }


    @Test
    void testPlayTurnStopsAtCorrectScore(){ //comunque li devo rifare perchè finchè non ho il mazzo non posso fare niente

    }

}
