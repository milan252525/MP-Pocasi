#pip install mysql-connector
#pip install pyowm

import pyowm
import time
import mysql.connector

mydb = mysql.connector.connect(
  host="139.59.139.93",
  user="app",
  passwd="pocasi",
  database="pocasi"
)

cursor = mydb.cursor()

#mesto, teplota (min, max), tlak, vlhkost, popis, ikona, rychlost_vetru, lat, long, vychod, zapad_slunce, cas(dt)

owm = pyowm.OWM("353b379036c2911483cfb9147c1ec9f0")

cities = ["praha", "brno",  "ostrava", "plzen", "ceske budejovice", "hradec kralove", "jihlava", "karlovy vary", "liberec", "olomouc", "pardubice", "usti nad labem", "zlin"]



while True:
  for city in cities:
    weather = owm.weather_at_place(city + ",CZ")
    w = weather.get_weather()
    print("{}: {} °C".format(city, str(w.get_temperature('celsius')["temp"])))

    sql = "UPDATE mesta SET teplota = %s WHERE nazev = %s"
    val = (w.get_temperature('celsius')["temp"], city)    
    cursor.execute(sql, val)
    time.sleep(2)

  #sql = "INSERT INTO mesta (nazev, teplota) VALUES (%s, %s)"

  mydb.commit()
  time.sleep(300)