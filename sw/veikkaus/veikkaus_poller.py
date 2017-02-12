#!/usr/bin/python

"""
example url
url = 'https://www.veikkaus.fi/api/v1/draw-games/draws?game-names=KENO&status=RESULTS_AVAILABLE&date-from=1440363600000&date-to=1440968400000'

"""

import sys, getopt
import urllib2
import re
from urlparse import urlparse
import MySQLdb
import logging
import json
import time
import calendar
from StringIO import StringIO
import gzip
from datetime import date, datetime
from datetime import timedelta
from time import mktime

VERSION_NAME = "1.0.1"
VERSION_NUMBER = 10000

db_host = ""
db_user = ""
db_passwd = ""
db_name = ""
veikkausUrl = 'https://www.veikkaus.fi/api/v1/draw-games/draws'
game = 'KENO' # KENO, LOTTO, JOKERI, VIKING, PORE, EJACKPOT, RAFFLE
status = 'RESULTS_AVAILABLE'

def LotteryDb(roundData):
    """ Add new lottery draw to database 
    """
    db = MySQLdb.connect(host = db_host, user = db_user, passwd = db_passwd, db = db_name)
     
    # create cursor
    cursor = db.cursor() 
    sql = "INSERT INTO lottery_numbers(brand_name, game_name, veikkaus_id, draw_time, close_time, primary_results, secondary_results)" \
           "VALUES ('%s', '%s', '%d', '%s', '%s', '%s', '%s')" \
           % (roundData['brand_name'], roundData['game_name'], int(roundData['veikkaus_id']), roundData['draw_time'],
           roundData['close_time'], roundData['primary_results'], roundData['secondary_results'])

    try:   
        cursor.execute("SELECT id FROM lottery_numbers WHERE game_name = '%s' and veikkaus_id = '%d'" % (roundData['game_name'], int(roundData['veikkaus_id'])))
        results = cursor.fetchone()        
        
        if cursor.rowcount == 0:
            try:      
                # make insert
                cursor.execute(sql)
                db.commit()
        
            except MySQLdb.OperationalError as e:
                logging.warning("Error: unable to insert data %s " % e)
        else:
            logging.warning('data exist in data base %s: %s' % (roundData['brand_name'], roundData['close_time']))
           
    except MySQLdb.OperationalError as e:
        logging.warning("Error: unable to fetch data %s " % e)
        
    db.close()
      
def readUrl(url):
    """ Read data from given url
        Return data in raw form
    """
    
    try:
        request = urllib2.Request(url)
        request.add_header('Content-Type', 'application/json')
        request.add_header('Accept', 'application/json')
        request.add_header('X-ESA-API-Key', 'WWW')
        
        page = urllib2.urlopen(request)
        
        response_headers = page.info()

        # unzip compressed content
        if page.info().get('Content-Encoding') == 'gzip':
            buf = StringIO( page.read())
            f = gzip.GzipFile(fileobj = buf)
            data = f.read()
        else:
            data = page.read()
 
        return data   
    except urllib2.URLError as e:
        logging.warning("Error: unable to open url %s " % e)
    except urllib2.HTTPError, e:    
        logging.warning("Error: unable to open url %s " % e)
    except httplib.HTTPException, e:    
        logging.warning("Error: unable to open url %s " % e)
 
        
def parseGame(game):
    """ Parse game info from given list
    """    
    
    roundData = {}
    roundData['brand_name'] = game['brandName']
    roundData['game_name'] = game['gameName']
    roundData['veikkaus_id'] = game['id']
    roundData['draw_time'] = datetime.fromtimestamp(game['drawTime'] / 1000) 
    
    #print game['brandName']
    #print game['gameName']
    #print roundData['draw_time']
    #print game['id']  
    
    roundData['primary_results'] = ','.join(game['results'][0]['primary'])
    #print roundData['primary_results']
    
    if(game['gameName'] == 'JOKERI'):      
        roundData['close_time'] = '0000-00-00 00:00:00'
        roundData['secondary_results'] = 'NULL'
        
    if(game['gameName'] == 'KENO'):            
        roundData['close_time'] = datetime.fromtimestamp(game['closeTime'] / 1000) 
        #print roundData['close_time']
        roundData['secondary_results'] = ','.join(game['results'][0]['secondary'])
        #print roundData['secondary_results']
        
    if(game['gameName'] == 'LOTTO' or game['gameName'] == 'VIKING'):    
        roundData['secondary_results'] = ','.join(game['results'][0]['secondary'])
        roundData['close_time'] = '0000-00-00 00:00:00'
        #print roundData['secondary_results']
        
        for i in range(len(game['addonDraws'])):
            addonDraw = game['addonDraws'][i]
            #print parseGame(addonDraw)
                      
    LotteryDb(roundData)

def printUsingHelp():
    """ Print usage 
    """
    print 'veikkaus_poller.py -l <log file> --game= <game name KENO, LOTTO, VIKING, JOKERI> --start= <start date> --end= <end date>'          
    
def main(argv):
    global game
    logfile ='/tmp/veikkaus_poller.log'
    
    # set default request time difference one day
    diffDays = timedelta(days= -1)
    dateTo = date.today()
    dateFrom = dateTo + diffDays
    unixDateFrom = int(mktime(dateFrom.timetuple())) * 1000
    unixDateTo = int(mktime(dateTo.timetuple())) * 1000
    
    try:
        opts, args = getopt.getopt(argv, "hl:", ["end=","game=","logfile=", "url=","start="])
    except getopt.GetoptError:
        printUsingHelp()
        sys.exit(2)

    for opt, arg in opts:
        if opt in ("--end"):
            # set end date
            unixDateTo = calendar.timegm(time.strptime(arg, '%d.%m.%Y')) * 1000
        elif opt in ("--game"):
            # set game
            game = arg
        elif opt == '-h':
            printUsingHelp()
            sys.exit()
        elif opt in ("--start"):
            # set start date
            unixDateFrom = calendar.timegm(time.strptime(arg, '%d.%m.%Y')) * 1000
        elif opt in ("-l", "--log"):
            logfile = arg   

    logging.basicConfig(filename = logfile, level = logging.INFO)           
        
    # build request string
    url = veikkausUrl + '?game-names=' + game + '&status=' + status + '&date-from={0:.0f}&date-to={1:.0f}'.format(unixDateFrom, unixDateTo)
    
    data = readUrl(url)
    results = json.loads(data)

    # loop all results
    for i in range(len(results['draws'])):        
        parseGame(results['draws'][i])

if __name__ == "__main__":
   main(sys.argv[1:])
