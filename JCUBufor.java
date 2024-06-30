import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JCUBufor implements AbstractBuforInterface {
  private int _bufor[]; // tablica liczb w buforze
  private int _buforItems = 0; // ilość elementów w buforze
  private int _buforSize; // wielkość bufora
  private final Lock _lock; // zamek do synchronizacji
  private final Condition _notFull; // informacja o tym czy bufor jest pełny
  private final Condition _notEmpty; // informacja o tym czy bufor jest pusty

  public JCUBufor(int size) { // inicjalizacja w konstruktorze
    _bufor = new int[size];
    _buforSize = size;
    _lock = new ReentrantLock();
    _notEmpty = _lock.newCondition();
    _notFull = _lock.newCondition();
  }

  @Override
  public void put(int i) {
    _lock.lock(); // Zakładamy zamek
    try {
      while (_buforItems == _buforSize) {
        _notFull.await(); // jeśli bufor jest pełny to czekamy na sygnał o tym, że przestał być
      }
      _bufor[_buforItems++] = i; // jeśli bufor nie jest pełny to dodajemy do niego element
      _notEmpty.signal(); // informujemy o tej operacji
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      _lock.unlock(); // ostatecznie ściągamy zamek
    }

    System.out.println("Producent: " + i); // wyświetlamy go na ekranie
  }

  @Override
  public int get() {
    _lock.lock(); // Zakładamy zamek
    try {
      while (_buforItems == 0) {
        _notEmpty.await(); // jeśli bufor jest pusty to czekamy na sygnał o tym, że przestał być
      }
      int i = _bufor[0]; // jeśli są jakieś wartości to pobieramy pierwszy element bufora

      for (int j = 0; j < _buforItems - 1; ++j) {
        _bufor[j] = _bufor[j + 1]; // przesuwamy elementy w buforze
      }
      --_buforItems; // zmniejszamy ilość elementów w buforze o zdjęty element

      System.out.println("\t\tKonsument: " + i); // wyświetlamy element zdejmowany z bufora

      _notFull.signal(); // wysyłamy sygnał o tym, że bufor nie jest pełny

      return i; // zwracamy element z bufora
    } catch (InterruptedException e) {
      e.printStackTrace();
      return -1;
    } finally {
      _lock.unlock(); // ostatecznie zdejmujemy zamek
    }
  }
}
