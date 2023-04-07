package com.jpndev.patients.utils;

public class JavaClass extends classY implements InterfaceA,InterfaceB {

  long number =1234_5678_9012_3456L;


    @Override
    public void funA() {

    }

    @Override
    public void funB() {

    }
}


interface  InterfaceA {
   void  funA();

}

interface  InterfaceB {
    void  funB();

}

abstract class classX   {
    Object o;
    public void funX() {

    }
}
 class classY extends  classX  {

    public void funY() {

    }


}

class classZ extends  classX {

    public void funZ() {

    }


}




