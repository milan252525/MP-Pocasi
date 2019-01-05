package pocasi;

/**
 * Handler pro získávání potřebných dat
 * @author Milan Abrahám, Anh THai Hoang
 */
public class Handler {
    /** 
    * Objekt třídy Pocasi, ze kterého bude handler získávat data
    * @see Pocasi 
    */
    private Pocasi pocasi;
    
    /**
     * Konstruktor handleru
     * @param pocasi Objekt třídy Pocasi, ze kterého handler získává data
     */
    public Handler(Pocasi pocasi){
           this.pocasi = pocasi;
    }

    /**
     * Získání objektu třídy Pocasi, ze kterého handler získává data
     * @return objekt třídy Pocasi, ze kterého handler získává data
     */
    public Pocasi getPocasi() {
        return pocasi;
    }
    
    /**
     * Změna objektu pocasi
     * @param pocasi nový objekt pocasi
     */
    public void setPocasi(Pocasi pocasi) {
        this.pocasi = pocasi;
    }
    
    /**
    * získání objektu WeatherByCity z objektu pocasi
    * @return objekt WeatherByCity
    */
    public WeatherByCity getWeatherByCity(){
        return pocasi.getWeatherByCity();
    }
    /**
    * získání ikony objektu WeatherByCity z objektu pocasi
    * @return objekt WeatherByCity
    */    
    public String getWeatherByCityIcon(){
        return pocasi.getWeatherByCity().getWeatherIcon();
    }
    /**
    * získání objektu WeatherForecast z objektu pocasi
    * @return objekt WeatherForecast
    */
    public WeatherForecast getWeatherForecast(){
        return pocasi.getWeatherForecast();
    }
    /**
    * získání ikony objektu WeatherForecastIcon z objektu pocasi na pozici x
    * @param x index ikony
    * @return objekt WeatherByCity
    */     
    public String getWeatherForecastIcon(int x){
        return pocasi.getWeatherForecast().getArrayValue(x,3);
    }
    /**
    * získání objektu WeatherByCoordinates z objektu pocasi
    * @return objekt WeatherByCoordinates
    */    
    public WeatherByCoordinates getWeatherByCoordinates(){
       return pocasi.getWeatherByCoordinates();
    }
    /**
    * získání ikony objektu WeatherByCoordinates z objektu pocasi
    * @return objekt WeatherByCoordinates
    */ 
      public String getWeatherByCoordinatesIcon(){
        return pocasi.getWeatherByCoordinates().getWeatherIcon();
    }
}
