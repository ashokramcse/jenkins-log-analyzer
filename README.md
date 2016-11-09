#Jenkins Log Analyzer

##Motivation
---
To provide a robust analyzer suite that will provide rapid analysis results on Jenkins logs for all types of test jobs.

##One-Time/First-Time Process
---
1. This project requires ***Java 8*** in the machine.
2. Configure Maven on the machine. (Recommended 3.3.X and more)
3. Download and extract [Tomcat 8](https://tomcat.apache.org/download-80.cgi) application server.
4. Fork the main Jenkins util repository from my user `https://github.com/ashokramcse/jenkins-log-analyser.git`
5. Clone your forked branch `git clone https://github.com/<git-username>/jenkins-log-analyser.git`
6. Go the project directory on terminal
7. Add main repository as an upstream alias `git remote add upstream https://github.com/ashokramcse/jenkins-log-analyser.git`

##How to Setup the Workspace
---
1. Import the project to any IDE and convert it into Maven project.
2. Configure Java and Tomcat 8 in the IDE.
3. Add the project into the Tomcat server.
4. Start the Server and hit the [home page](http://localhost:8080/jenkins-util/index.html)

##Bug Fixing & Contributions
---
1. Create your feature branch `git checkout -b newbranch`
2. Make changes or Fix Bugs.
3. Add your changes `git add .`
3. Commit your changes `git commit -m “add relevant comment”`
4. Pull new changes from upstream and resolve conflicts `git pull --rebase upstream master`
5. Push to your forked repo `git push origin newbranch`
6. Submit a pull request via `github.com`

##Output
---
![ScreenShot](https://cloud.githubusercontent.com/assets/23329036/20129941/74d6882e-a679-11e6-9b1e-4485ea566a97.png)

##Changes&Releases
---
##V1.1
1. Error Pages updated
2. Errors will be displayed in the table format.

---
##V1.0.1
1. Can download the excel from the tree view.
2. Can refresh tree view when it is failed to load.
3. Bug Fixes.

---
##V1.0
1. Enhanced to get Excel and in Tree format as output from the analyzer.
2. Can download the excel from the tree view.
3. The output in Excel has the following format.

###Failure

```
|S.No|TestCase Name|Reason of Failure|
| 01 |  Test Case1 | RemoteWebDriver |
| 02 |  Test Case2 | CompanyCreation |
| 03 |  Test Case3 |  AssertionError |
|*Failure*|Unstable|Success|
```

###Success

```
|S.No|TestCase Name|
| 01 |  Test Case1 |
| 02 |  Test Case2 |
| 03 |  Test Case3 |
|Failure|Unstable|*Success*|
```

---
##V0.1-Alpha
1. This release has basic jenkins log analyzer core.
2. This will give output in the form of Excel.
3. The output in Excel has the following format.

###Failure

```
|S.No|TestCase Name|Reason of Failure|
| 01 |  Test Case1 | RemoteWebDriver |
| 02 |  Test Case2 | CompanyCreation |
| 03 |  Test Case3 |  AssertionError |
|*Failure*|Unstable|Success|
```

###Success

```
|S.No|TestCase Name|
| 01 |  Test Case1 |
| 02 |  Test Case2 |
| 03 |  Test Case3 |
|Failure|Unstable|*Success*|
```
---