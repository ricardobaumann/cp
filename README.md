# Chargers
Manages charging points through geolocation positions

## Running
On your command line, run
```
./mvnw clean package && java -jar target/chargers-0.0.1-SNAPSHOT.jar
```

## Usage
1. To create a charging point use
```
PUT /chargers/{id}
{
  "zipCode": "12207",
  "latitude": "1.23",
  "longitude": "2.33"
}
```
Expected response code is 200 OK. 

2. To fetch a charging point by id, use `GET /chargers/{id}`

3. To fetch charging points by zip code, use `GET /search/zip-code/{zipCode}`

4. To fetch charging points inside a perimeter, use `GET /search/circle/?latitude={latitude}&longitude={longitude}&radiusInKm={radiusInKm}`

## Architecture and implementation
This application was built using standard Spring Boot framework with embedded mongo database. 
To make it production-ready, please configure a real mongo database instance.
In case of any questions or trouble running the service, please contact me. 