class Bufor implements AbstractBuforInterface {
  private int _bufor[]; // tablica liczb w buforze
  private int _buforItems = 0; // ilość elementów w buforze
  private int _buforSize; // wielkość bufora

  public Bufor(int size) { // inicjalizacja w konstruktorze
    _bufor = new int[size];
    _buforSize = size;
  }

  public boolean isFull() { // funkcja sprawdzająca, czy możemy dodać element
    return (_buforItems == _buforSize);
  }

  public boolean isEmpty() { // funckja sprawdzająca, czy bufor jest pusty
    return (_buforItems == 0);
  }

  @Override
  public synchronized void put(int i) { // metoda synchronizowana - wykorzystuje monitor
    while (isFull()) { // w pętli sprawdzamy, czy jest pełny
      try {
        wait();// jeśli tak to oczekujemy, aż się zrobi miejsce
      } catch (InterruptedException e) {
      }
    }
    _bufor[_buforItems++] = i; // jeśli nie jest pełny to dodajemy element na koniec
    System.out.println("Producent: " + i); // wyświetlamy go na ekranie

    if (_buforItems == 1) { // jeśli mamy jakiś element, to informujemy wątki
      notifyAll();
    }
  }

  @Override
  public synchronized int get() { // metoda synchronizowana - wykorzystuje monitor
    while (isEmpty()) { // w pętli sprawdzamy, czy jest pusty
      try {
        wait(); // jeśli jest to oczekujemy na pojawienie się wartości
      } catch (InterruptedException e) {
      }
    }

    int i = _bufor[0]; // jeśli nie to pobieramy pierwszy element bufora

    for (int j = 0; j < _buforItems - 1; ++j) {
      _bufor[j] = _bufor[j + 1]; // przesuwamy elementy w buforze
    }
    --_buforItems; // zmniejszamy ilość elementów w buforze o zdjęty element

    System.out.println("\t\tKonsument: " + i); // wyświetlamy element zdejmowany z bufora

    if (_buforItems == _buforSize - 1) { // jeśli liczba elementów w buforze jest mniejsza niż maksymalna
      notifyAll(); // to informujemy o tym inne wątki
    }
    return i; // zwracamy element z bufora
  }
}
