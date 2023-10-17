# DESING 

Cambiar el color de arriba  de android en donde muestra el reloj:

        <item name="android:statusBarColor">@color/secondary</item>

Cuando tenemos un diseño reutilizable se crea  style en themes.xml:

    <style name="Title">
            <item name="android:textStyle">bold</item>
            <item name="android:fontFamily">@font/dancing</item>
            <item name="android:textSize">30sp</item>
            <item name="android:textColor">@color/secondary</item>
    </style>

# LLamadas API

1- Permiso de internet : 

        <uses-permission android:name="android.permission.INTERNET"/>

2- Llamada a retrofit:

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.3.1")

API : https://newastro.vercel.app/

3. Tenemos que crear nuestra interfaz de APIService donde es importante poner la repsuesta y el suspend

    interface HoroscopeApiService {
        @GET("/{sign}") //Editamos con {} debido que enviaremos un parametro
        suspend fun getHoroscope(@Path("sign") sign:String):PredictionResponse //PredictionResponse es el modelo de la respuesta
    }
4. La respuesta que recuperamos es con :

    data class PredictionResponse(
    @SerializedName("date") val date: String, //El SerializedName es debido que cuando se ofusque se cambia de nombre y el nombre con la respuesta no hara match
    @SerializedName("horoscope") val horoscope: String,
    @SerializedName("sign") val sign: String, )

**NOTA: Es data object cuando no se necesiten pasar parametros y data class cuando es con parametros 
5. Ahora para comunicarnos getHoroscope con el viewModel osea para la comunicacion de la data y domain se necesita "REPOSITORY" y REPOSITORYIMPL
CON LA finalidad de que cada modulo este sin dependencia uno de otro.

5.1 El repositoryImpl nos ayudara a mandar a llamar desde ahi a los servicios de services de intenet o de cualquier funte externa de datos

5.1 Los CASOS DE USO mandan a llamar al repositoryImpl
    Su unica responsabilidad es mandar a llamar los impl 
    Los Casos de uso pueden llamar a la funcion invoke que con este cuando llamamos el caso de uso entra directo a este ejemplo:
    
    suspend operator fun invoke(sign: String) = repository.getPrediction(sign)

    y cuando se quiera llamar el invoke se mandara a llamar con el GetPredictionUseCase cuando se instancia el metodo por lo tanto no sera necesario poner invoke al llamar al caso de uso



# RETROFIT

-- El interceptor sirve para agregarle informaciòn extra a la peticion
-- El interceptor nos ayudar a que antes que pase de cliente a servidor pase por medio del interceptor aqui podamos agregar o modificar algun dato

IMPLEMENTAR:

    implementation ("com.squareup.okhttp3:logging-interceptor:4.3.1")

para proveer interceptor es con :

    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient{
    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build()
        }

    Tener un interceptor nos ayuda a ver cuanto tiempo tarda, si existe algun error , si necesitamos meter datos en la cabecera


# CREAR UN INTERCEPTOR DESDE 0(lo de arriba es con una ya creada)

1. Crear una clase que exttienda de interceptor , creamos interceptor normalmente para pasar tokens como en este ejemplo:
    ejemplo : class AuthInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor
    AQUI agregamos el inject para que este se inyecte en dagger

2. lo inyectamos en los providers del module :             .addInterceptor(authInterceptor)
   3. Ahora en la clase Interceptor extiende del intercpetor en su metodo de este :

       override fun intercept(chain: Interceptor.Chain): Response {
       val request =
       chain.request().newBuilder().header("Autorization", tokenManager.getToken()).build()
       return chain.proceed(request)
       }
   
   3.1. Decimos que a la peticion chain le agregaremos un header con la clave y valor Autorization y valor tokenManager.getToken()

Donde tokenManager màs adelante podremos agregarle ese token del lugar donde lo recuperemos.


# BuildTypes

**Nos ayudarà a que cambiemos de entorno , dando  nuevos valores dependiendo el rumbo
se activa con :

    buildFeatures{
    buildConfig = true
    }

se implementa en el gradle para darle el nombre correspondiente:

    buildTypes {
        getByName("release") {
        //Code
        }
    }