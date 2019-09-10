# PokemonApp
Android Pokemon Challenge

Project built for the Android Pokemon Challenge using the Pokemon TCG API https://docs.pokemontcg.io/

Because use of LiveData libraries was not explicitly allowed this project uses a retained headless fragment to handle
configuration changes and loading data from the pokemon api. 

Used default libraries provided by challenge with the only extra libraries provided being: 

```
    // additional dependencies
    implementation "com.squareup.moshi:moshi:1.8.0"
    implementation "com.squareup.moshi:moshi-kotlin:1.8.0"
    implementation "com.squareup.retrofit2:converter-moshi:2.5.0"
    implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofit_version"
```

No dependency injection or architecture components libraries were used.
