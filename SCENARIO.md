#PRZYKŁAD DOŁĄCZENIA DO ROZGRYWKI
1. USER wpisuje swój nickname
2. Wyświetla się lista wolnych rozgrywek oraz przycisk umożliwiający stworzenie własnej
3. USER wybiera rozgrywke i dołącza do lobby
4. SYSTEM czeka na dołączenie odpowiedniej liczby graczy
    4a) Wymagana liczba zostaje osiągnięta [SUKCES]
        4a1) Przejście do @5
    4b) Liczba nie została osiągnięta i OWNER usuwa rozgrywkę [FAIL]
        4b1) Przekierowanie każdego USER'a do @2
5. Gra rozpoczyna się

#STWORZENIE ROZGRYWKI
1. USER wpisuje swój nickname
2. Wyświetla się lista wolnych rozgrywek oraz 
    przycisk umożliwiający stworzenie własnej
3. USER wybiera tworzenie rozgrywki
4. Wprowadza parametry
    1) nazwa rozgrywki
    2) ile graczy
    3) ilość pionków / wielkość planszy
    4) zatwierdza
5. Wysłanie do serwera żądania utworzenia stołu
6. Przemianowanie USER'a na OWNER'a rozgrywki
7. [LOBBY]
    0) Przycisk zniszczenia rozgrywki
        a) poinformowanie dołączonych USER'ów
        b) przejście do @2
    1) OWNER po dołączeniu odpowiedniej liczby graczy
       może wystartować grę
8. Start rozgrywki

#ROZGRYWKA
1. Ustalenie kolejki graczy (w tym pierwszego)
2. Każdy z graczy dostaje stan początkowy gry
    a) ułożenie pionków
    b) wielkość planszy
    c) token sesji USER'a
3. Rysowanie planszy
4. Rozmieszczenie pionków na planszy
5. ROZPOCZĘCIE SESJI (gry)
    5a) [FAZA] Select Pawn
    5b) [FAZA] Select Move Field
        5b1) [SCENARIO 1] wybrał pole
        5b2) [SCENARIO 2] wybrał innego własnego pionka (pod warunkiem że nie ruszał się w tej turze)
            5b2i) podmień aktywnego pionka na nowo wybranego
            5b2ii) przejdź do @5b
    5c) [FAZA] Validation Move Field
        5c1) [SUCCESS] gracz dokonał legalnego ruchu, przejdź do @5d
        5c2) [FAILURE] nielegalny ruch, przejdź do @5b
    5d) [FAZA] Make Move Field
        5d1) aktualizacja planszy po stronie serwera
        5d2) propagacja różnicy planszy do graczy
    5e) [FAZA] End Turn State (czyszczenie, komunikacja z serwerem, przejście do waiting)
    5f) [FAZA] Waiting (observer)
        5f1) Wyświetla zmiany zachodzące na planszy od innych graczy
        5f2) Czeka na swoją turę, po czym przechodzi do @5a
    5g) [FAZA] End Game
        5g1) Wyświetla komunikat
        5g2) Podpina bota, jeśli leftnął
6. Zakończenie rozgrywki i przejście do listy rozgrywek
    