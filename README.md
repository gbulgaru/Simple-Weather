# SimpleWeather
SimpleWeather is an Android app written in Java that provides current weather forecasts using various REST APIs.\
The app displays information such as temperature, humidity, visibility, pressure, wind speed, and more.\

## Features
- Current weather conditions for the user's current location.
- Retrieval of weather data using [IPinfo](https://ipinfo.io) and [OpenWeather](https://openweathermap.org) APIs.
- Simple and gorgeous UI with Material You design.

## Usage
Launch the SimpleWeather app on your Android device (if it's the first start up you'll be asked to grant aproximate or fine location permision).\
SimpleWeather will send and API request to OpenWeather asking for the forecast at the detected location.\
If the user denies all location permission requests, SimpleWeather will use the user's IP address to aquire an aproximate location and run the service.\
SimpleWeather uses APINinjas to do a reverse geocoding (City -> Coordinates).\

## Contributing
Contributions are really welcome!

## License
This project is licensed under the GNU GPLv3 License. See the [LICENSE file](\LICENSE.md) for more information.

### Author
Bulgaru George Ionut - [gbulgaru](https://github.com/gbulgaru)
