# Vizsgafeladat

A feladatok megoldásához az IDEA fejlesztőeszközt használd!
Bármely régebbi osztályt megnyithatsz.

A saját neveddel ellátott vizsga repository-ba dolgozz (`java-sv2-adv-exams-XY`)!
A vizsgát a repository-n belül külön projektként helyezd el,
a projekt neve legyen: `sv2022-jvjbf-felezovizsga`.
A vizsgakiírás tartalmát nyugodtan át lehet másolni (`pom.xml`).
GroupId: `training360`, artifactId: `sv2022-jvjbf-felezovizsga`. Csomagnév: `videos`.

Ha ezzel kész vagy, azonnal commitolj!

A feladatra 3 és negyed órád van összesen (195 perc). Ha letelik az idő (tehát 13:00-kor), mindenképp commitolj! Akkor is, ha nem vagy kész!

## Feladat

### JPA (50 pont)
Egy videós platform adatbázis API-ját kell megvalósítanod. Megvalósítás során figyelj arra, hogy az architektura a tanultaknak megfelelő legyen! Figyelj a tábla és oszlop elnevezéskre! (7 pont)<br>

Készíts egy `videos.User` nevű entitást. A `User` tud videókat feltölteni az oldalra.
Attribútumai a következők: `String name`, `LocalDate registrationDate` és egy `UserStatus userStatus`. Ez utóbbi egy felsorolásos típus
és a feltöltött videók mennyisége alapján kap értéket (`BEGINNER, ADVANCED`), kezdetben mindig `BEGINNER`. (8 pont)<br>

Hozz létre egy `Video` entitást. Egy videónak az egyedi azonosítón kívül egy címe legyen. Valósíts meg kétirányú egy-több kapcsolatot a `User`
és a `Video` között. (Egy felhasználó több videót tölthet fel) (8 pont).

Hozd létre a `UserRepository` nevű osztályt és valósítsd meg benne a következőket:
* Lehessen új felhasználót lemenenteni az adatbázisba. (`User saveUser(User user)`) (3 pont)
* Lehessen lekérdezni egy fehasználót videókkal együtt. Figyelj arra, hogy azokat is, akik még nem töltöttek fel videót! (`User findUserWithVideos(long userId)`) (5 pont)
* Lehessen egy videót hozzárendenlni egy felhasználóhoz. Ekkor keressük meg a felhasználót a videóhoz rendeljük hozzá, majd mentsük a videót! (`void updateUserWithVideo(long userId, Video video)`) (3 pont)
* Lehessen egy felhasználó státuszát megváltoztatni! (`User updateUserStatus(long userId, UserStatus status)`) (3 pont)
* Lehessen lekérdezni felhasználót a vidóinak mennyisége alapján! (`List<User> findUsersWithMoreVideosThan(int amount)`) (5 pont)

Hozd létre a `UserService` osztályt és valósítsd meg benne a következő üzleti logikát. Egy felhasználó tölthessen fel videót, amit a `void uploadVideo(long id, Video video)` metódus valósít meg.
Egy felhasználó maximum 10 videót tölthet fel, ha megvan már a 10 akkor nem tölthet fel újat és a metódus `IllegalStateException`-t dob. Ha egy felhasználó az új videóval már legalább az 5. feltöltésnél jár
akkor a státusza `Advanced`-re változik. (8 pont) <br>


A feladat bővíthető további metódusokkal, konstruktorokkal ha szükségesnek érzed!

### Tesztelés (30 pont)
Valósítsd meg a következő teszteseteket! A megoldásnál törekedj az AssertJ és a Mockito használatára!<br>

Hozd létre a `UserRepositoryTest` osztályt és a következőket ellenőrizd.

* Ellenőrizd, hogy a mentés sikeres volt-e. Ments le majd kérj vissza egy felhasználót id alapján!
  Ellenőrizz a nevére, státuszára! (3 pont)
* Ellenőrizd a videó hozzáadását. Ments le egy felhasználót, majd frissítsd egy új videóval, majd kérd vissza és ellenőrizd az állapotát! (3 pont)
* Ellenőrizd a státusz megváltoztatását. Ments le egy felhasználót, változtasd meg a státuszát,majd ellenőrizd, hogy a visszaadott entitásnak valóban más-e a státusza! (3 pont)
* Ellenőrizd a videók mennyisége szerinti lekérdezést. Ments le néhány felhasználót, rendelj hozzájuk különböző mennyiségű videót majd szűrj videók mennyisége alpján!(Létrehozhatsz ehhez egy `addVideo(Video video)` metódust is a `User` osztályon belül, de figyelj, hogy a videók is mentésre kerüljenek!) (5 pont)

Hozd létre a `UserServiceIT` teszt osztályt és írj __integrációs__ teszteket a feltöltés metódusra:

* Ments le egy felhasználót, adj hozzá egy videót és ellenőrizd, hogy a státusza `BEGINNER` maradt-e. (3 pont)
* Ments le egy felhasználót, adj hozzá egy videót majd kérd vissza és nézd meg, hogy a video bekerült-e a listájába.  (3 pont)
* Ments le egy felhasználót, tölts fel hozzá 4 videót majd egy 5.-et és ellenőrizd, hogy a státusza `ADVANCED` lett-e. (5 pont)

Hozd létre a `UserServiceTest` teszt osztályt és írj __unit__ teszteket a felltöltés metódusra:

* Ellenőrizd, hogy a 11. video feltöltésekor valóban kivételt dob a tesztelendő metódus és azt is, hogy az üzenete megfelelő!(5 pont)