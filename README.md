# Concurrent Java 

It's university task to compare speed of handling Producer-Consumer problem with JCU and regular wait()/notify() metods.

## Classes
- Producent - is responsible for putting values to the bufor.
- Konsument - is responsible for getting values from the bufor.
- Bufor - is responsible for implementing AbstractBuforInterface with `wait()`/`notify()` methods and `synchronized` blocks
- JCUBufor - doing exactly the same as Bufor but with locks from JCU.
- PKmon - is responsible for running two threads with Producer and Consumer and measure time. It contains `main()` function and gets parameter of time which thread should sleep to imitate some operations.
- PKJcu - doing exactly the same as PKmon but with JCU buffer implementation.

## Interfaces
- AbstractBuforInterface - declares two methods needed to implement the basic bufor.