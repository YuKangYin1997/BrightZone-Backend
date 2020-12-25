# comp5104-cms

## 1. database setting
```sql
CREATE DATABASE cms;
create user 'cmsadmin'@'localhost' identified by '123456';
grant all privileges on cms.* to cmsadmin@localhost;
```

## 2. database populating
Database can now be populated by randomly generated data for testing purpose.
The resulting database with default setting consists of：
- 5 Admin, 100 Professors, 500 Students and their corresponding Accounts
- 6 Faculties
- 5158 Courses
- 100 Class_Room with random capacity
- 89 Classes with random schedule

Usage:
- Run CmsApplication
- Open localhost:8080/populate
- Wait until `DB populated successfully` is shown

## 3. Executing EvoMaster
- Right click `EMDrive.java` located at `src/test/java/com/carleton/comp5104/cms/driver/EMDriver.java` and say `Run 'EMDriver.main()'`
- Type command `java -jar evomaster.jar --maxTime 20s --outputFolder src/test/java` in terminal to execute `evomaster`
- Once you have already down so, you will see a `EvoMasterTest.java` file generated under `src/test/java/` folder
- Then simply right click `EvoMasterTest.java` and say `Run 'EvoMasterTest'` or `Run 'EvoMaster' with Coverage`
- After that, you'll see several test cases pass at the bottom window and code coverage at the right sidebar
