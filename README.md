# Bomberman Java Implementierung 
Dies ist eine Java implementierung des Spiels Bomberman. 

## Spielregeln
- Der Spieler kann sich in 4 Richtungen bewegen (oben, unten, links, rechts)
- Der Spieler kann Bomben legen 
  - Maximal eine Bomber gleichzeitig 
  - Die Bombe explodiert nach 3 Sekunden
  - Die Explosion zerstört alle Blöcke in der Nähe
- Spieler können sich selber töten
- Jeder Spieler hat 1 Leben

### Spielersteuerung
Blauer Spieler: 
- Bewegen: W A S D
- Bombe legen: Q

Roter Spieler:
- Bewegen: Pfeiltasten
- Bombe legen: Leertaste

## Installation und Ausführung
Die "config.txt" Datei muss auf dem gleichen Pfad wie die .jar Datei liegen.
In der "config.txt" Datei soll nur ein Wort stehen und zwar, das password der Datenbank).

Bei der Kompilierung muss erst der Oracle JDBC Treiber hinzugefügt werden. (https://www.oracle.com/de/database/technologies/appdev/jdbc-downloads.html)
