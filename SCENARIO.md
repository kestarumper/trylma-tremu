#PRZYKŁAD DOŁĄCZENIA DO ROZGRYWKI
1. USER wpisuje swój nickname
2. Wyświetla się lista wolnych rozgrywek oraz przycisk umożliwiający stworzenie własnej
3. USER wybiera rozgrywke i dołącza do lobby
4. SYSTEM czeka na dołączenie odpowiedniej liczby graczy
    1) Wymagana liczba zostaje osiągnięta [SUKCES]
        1) Przejście do @5
    2) Liczba nie została osiągnięta i OWNER usuwa rozgrywkę [FAIL]
        1) Przekierowanie każdego USER'a do @2
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
        1) poinformowanie dołączonych USER'ów
        2) przejście do @2
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
    1) [FAZA] Select Pawn
    2) [FAZA] Select Move Field
        1) [SCENARIO 1] wybrał pole
        2) [SCENARIO 2] wybrał innego własnego pionka (pod warunkiem że nie ruszał się w tej turze)
            1) podmień aktywnego pionka na nowo wybranego
            2) przejdź do @5.2
    3) [FAZA] Validation Move Field
        1) [SUCCESS] gracz dokonał legalnego ruchu, przejdź do @5.4
        2) [FAILURE] nielegalny ruch, przejdź do @5.2
    4) [FAZA] Make Move Field
        1) aktualizacja planszy po stronie serwera
        2) propagacja różnicy planszy do graczy
    5) [FAZA] End Turn State (czyszczenie, komunikacja z serwerem, przejście do waiting)
    6) [FAZA] Waiting (observer)
        1) Wyświetla zmiany zachodzące na planszy od innych graczy
        2) Czeka na swoją turę, po czym przechodzi do @5.6
    7) [FAZA] End Game
        1) Wyświetla komunikat
        2) Podpina bota, jeśli leftnął
6. Zakończenie rozgrywki i przejście do listy rozgrywek
    