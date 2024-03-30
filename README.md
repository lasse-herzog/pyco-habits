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
    - Habits Overview Liste und HabitItems
    - Datenbankprepopulation
    - Einbinden der Quotes

### Anweisungen zum Backend
Es wird nur eine lokale Room-Datenbank zur persistenten speicherung verwendet. Daher wird kein separates Backend auf einem Server benötigt. Das bedeutet aber auch, dass der Nutzer die Möglichkeit hat, alle Daten zur App über die Einstellungen zu löschen. In diesem Fall gehen alle vom Nutzer angelegten Habits verloren.

### Known Issues
- Die Kalenderview umfasst bisher nur eine Woche und die Habits auf der View können nicht identifiziert werden. In Zukunft soll diese View noch erweitert werden, aufgrund von Zeitdruck konnte das Feature aber nicht vollständig in diesem Umfang implementiert werden
- Die Streak-View ist nicht auf das Querformat angepasst
- Keine Unit-Tests
- Möchte man ein Habit bearbeiten, werden die vorher ausgewählten Kategorien nicht direkt angezeigt, sondern erst, wenn man die Cards vertical scrollt.
- Legt man beim Erstellen einer Habit ein End-Datum an, wird mit diesem noch nichts gemacht. Hier muss noch Logik eingebaut werden.
- Das Editieren der Kategorien hat zu sehr vielen Fehlern geführt. Die Option, dia Kategorien eines angelegten Habits zu ändern wurde entfernt.
