#pip install mysql-connector
#pip install pyowm

import pyowm
import asyncio
import mysql.connector

mydb = mysql.connector.connect(
  host="139.59.139.93",
  user="app",
  passwd="pocasi",
  database="pocasi"
)

#mesto, teplota (min, max), tlak, vlhkost, popis, ikona, rychlost_vetru, lat, long, vychod, zapad_slunce, cas(dt)

owm = pyowm.OWM("353b379036c2911483cfb9147c1ec9f0")

cities = ["praha", "brno",  "ostrava", "plzen", "ceske budejovice", "hradec kralove", "jihlava", "karlovy vary", "liberec", "olomouc", "pardubice", "usti nad labem", "zlin"]



for city in cities:
    weather = owm.weather_at_place(city + ",CZ")
    w = weather.get_weather()
    print("{}: {} °C".format(city, str(w.get_temperature('celsius')["temp"])))
    asyncio.wait(0.5)
