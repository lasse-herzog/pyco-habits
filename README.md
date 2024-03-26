# pyco-habits
## Pursue Your Customized Objectives
### Aufgabenverteilung
- Daniel Schwager
    - Create/Edit Habit View
    - Designentwurf
- Jonas Wiedenmann
    - Streak View
    - Datenbankmodell
    - Einbindung Daily Habit Worker
- Lasse Herzog
    - Home Screen
    - Navigation
    - Kalender View
- Maximilian Hartmann
    - Habits Overview Liste
    - Umsetzung Material Design
    - Einbindung Daily Habit Worker

### Anweisungen zum Backend
Es wird nur eine lokale Room-Datenbank zur persistenten speicherung verwendet. Daher wird kein separates Backend auf einem Server benötigt. Das bedeutet aber auch, dass der Nutzer die Möglichkeit hat, alle Daten zur App über die Einstellungen zu löschen. In diesem Fall gehen alle vom Nutzer angelegten Habits verloren.

### Known Issues
- Die Kalenderview umfasst bisher nur eine Woche. In Zukunft soll diese View noch erweitert werden; aufgrund von zeitdruck konnte das Feature aber nicht implementiert werden
- Die Streak-View ist nicht auf das Querformat angepasst
- Keine Unit-Tests