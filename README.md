# LotteryAdvisor
Simple application to collect lottery numbers from REST API and making statistical analysis

Application consist two parts. Poller which collects lottery draw results and UI application for analysis.

Poller is implemented using Python language. It's intended to run as a cron job. 
It's only purpose is retrieve lottery draws from  REST API. There is one option which should be used. 
Name of the game has to be given. Working names are: KENO, LOTTO, JOKERI and VIKING. 
There are some other options which are optional:
    -l <path and logfile>
    --start <start date of collect formatted dd.mm.yyyy>
    --end <end date of collect formatted dd.mm.yyyy>
    
An example is here:
    veikkaus_poller --game=KENO

User interface is implemented using J2SE Swing.