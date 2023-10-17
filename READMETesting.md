# Testing

dependencias :

Testear unitTest , donde aqui testeamos pequeñas partes de la funcionalidad

    //UnitTesting
    testImplementation("junit:junit:4.13.2")
    testImplementation ("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation ("io.mockk:mockk:1.12.3")


// el nombre de los test debe ser largo y se puede poner con comillas y espacios `toDomain should return a correct PredictionModel`

    @Test
    fun `toDomain should return a correct PredictionModel`(){
            //Given --> dame infor necesaria
            val horoscopeResponse = anyResponse
            
            //When --> cuando esto ocurra
            val predictionModel = horoscopeResponse.toDomain()
    
            //Then --> entonces debemos ...
            predictionModel.sign shouldBe horoscopeResponse.sign
            predictionModel.horoscope shouldBe  horoscopeResponse.horoscope
        }


//Para mockear una clase la cual dependenda de otra clase o algun elemento es con  mock:


    @Test
    fun `when viewmodel is created then horoscopes are loaded`(){ 
        every { horoscopeProvider.getHoroscopes() } returns horoscopeInfoList //quiere decir casa que se llame al mètodo horoscopeProvider.getHoroscopes() vamos a retornar algo
        viewModel = HoroscopeViewModel(horoscopeProvider) //dentro de su inicializacion se manda a llamar horoscopeProvider.getHoroscopes()
        val horoscopes = viewModel.horoscope.value //por lo cual nos aseguramos de de al iniciar no este vacia
        assertTrue(horoscopes.isNotEmpty())
    }