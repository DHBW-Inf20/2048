# Programmieren Praxisprojekt - 2048

<img align="right" width="220" height="75" src="DHBW_Logo.png">

### Gruppenmitglieder:
+ Jonas Weis 
+ Jonathan Schwab 
+ Adrian Liehner
+ Reinhold Jooß
+ Felix Wochele

## Spiel: 

### Steuerung:
  
+ W - Hoch
+ A - Links
+ S - Runter
+ D - Rechts

### Starten des Spiels

+ Im Hauptmenü lässt sich durch Betätigen des Sliders die Spielfeldgröße (4x4 bis 7x7) bestimmen
+ In einem Textfeld lässt sich der Spielername eintragen, welcher beim Erreichen eines Highscores (nur in Spielmodus "Random") in die Highschoreliste eingetragen wird
+ Durch den Button "KI: On/Off" lässt sich der KI-Spieler ein-/ausschalten
+ Durch Drücken von "New _X_ x _X_" wird der Spieler in das Spielmodi Menü weitergeleitet, in welchem nach Auswahl des Spielmodus das Spiel durch Drücken des Buttons "Play" gestartet werden kann

### Spielmodi:

| Spielmodi | Beschreibung |
| ------ | ----------- |
| Kooperativ   | Die Kacheln werden bestmöglich gesetzt |
| MinMax - Kooperativ | Die Kacheln werden bestmöglich nach dem MinMax-Algorithmus gesetzt |
| MinMax - Unkooperativ | Die Kacheln werden schlechtmöglichst nach dem MinMax-Algorithmus gesetzt |
| Random | Die Kacheln werden zufällig gesetzt |


 Zu jedem Mode gibt es einen KI-Modus, in welchem die KI die Steuerung übernimmt.
 (Einstellbar im Hauptmenü mit "KI: On/Off")
 (Der KI-Spieler für die Spielmodi MinMax - Kooperativ/Unkooperativ ist aufgrund der hohen Berechnungszeit langsam)


## Commands for the Repositorys: 
### clone:
```shell
git clone https://github.com/FelixWochele/2048.git
```
### commit & push:
```shell
git add * 
git commit -m "Commit-Message"
git push origin main
```
