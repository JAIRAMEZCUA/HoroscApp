# Injection of dependency

* SETUP HILT
  //DaggerHilt
  implementation("com.google.dagger:hilt-android:2.48")
  kapt("com.google.dagger:hilt-compiler:2.48")

y en el build en plugins del proyecto :
    id ("com.google.dagger.hilt.android") version "2.48" apply false


* en caso que marque error en como("" COMPILE DEBUG  WITH JAVA ") VERSION 17, Se soluciona agregando en build.gradle del modulo:
    kotlin {
        jvmToolchain(8)
    }

# Integracion Hilt

1: Crear una clase que extienda de Application y con la anotacion @HiltAndroidApp
2: En el manifest integrarla -->         android:name=".HoroscApp"
3: Las activitys y fragments que queramos inyectar  para prepararlas llevan : @AndroidEntryPoint
4: Si necesitamos que un viewModel este preparado sera con @HiltViewModel
5: Si necesitamos inyectar en el constructor de activity, fragment, o viewModel es con :   @Inject constructor(private val getPredictionUseCase: GetPredictionUseCase) 

5.1 : Necesitamos inyectar los providers , para hilt se necesita inyectar todo!
    ejemplo : class HoroscopeProvider @Inject constructor() 

6. Para inyectar librerias e interfaces con dagger es a traves de modulos

    EJEMPLO: Necesita ser de tipo object y con la anotacion @Module  y poner el scope @InstallIn(SingletonComponent::class)
    
        @Module
        @InstallIn(SingletonComponent::class)
        object NetworkModule {

6.1 : El modulo nos puede proveer retrofit o cualquier otra libreria o interfaces con la anotacion:

    @Provides --> esto es para todas las que queramos inyectar
    @Singleton --> Esto es para el caso en el que queramos hacer lo que proveemos singleton, una sola intancia

Ejemplo de la interaccion entre providers de hilt

    //Necesita proveer de un retrofit por lo tanto busca en el modulo  la etiqueta     @Provides , para ver quien lo puede proveer
    @Provides
    fun provideHoroscopeApiService(retrofit: Retrofit): HoroscopeApiService {
    return retrofit.create(HoroscopeApiService::class.java)
    }

    //Nosotros necesitamos de algun provider que retorne retrofit por lo cual se va al metodo de arriba
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
                        .Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient) --> este iinterceptor tambièn se debe de proveer en hilt
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
    }



# Integracion del viewModels
1 : activity : conectar viewModel con la activity por medio de delegado --> private val horoscopeViewModel by viewModels<HoroscopeViewModel>()
2 : viewModel : Necesitamos agregar el valor que queremos que se observe:

    private var _horoscope = MutableStateFlow<List<HoroscopeInfo>>(emptyList())
    val horoscope: StateFlow<List<HoroscopeInfo>> = _horoscope

3: activity: se debe implementar el collector del evento para cualquier modificacion en  "horoscope" se detecte:

    private fun initUIState() {
        lifecycleScope.launch {
             repeatOnLifecycle(Lifecycle.State.STARTED) {
                horoscopeViewModel.horoscope.collect {
                //CAMBIOS EN HOROSCOPE
                horoscopeAdapter.updateList(it)
                 }
            }
        }
    }



# SEALED CLASS
* Son utiles cuando tengamos subclases especificas que ya estan definidas.
  * Ayuda a tener un control en codigo
Una buena practica es pasarle la referencia por medio del R.string. o R.drawable ya que nadie puede tener el contexto màs que el fragment.
  
    sealed class HoroscopeInfo(val img:Int, val name:Int){
          data object Aries:HoroscopeInfo(R.drawable.aries, R.string.aries)
  }
  


# Integrar imagenes en android 
1: Una vez agregadas las imagenes en png o jpg se debe de comprimir haciendo  -> dar click derecho y convertir a webp


# Realizar un recyclerView
1: Se crea un recycler View rvHoroscope , en caso que este en un fragment
2: se debe añadir : 

        binding.rvHoroscope.apply {
            layoutManager = GridLayoutManager(context, 2) // context y numero de columnas 
            adapter = horoscopeAdapter
        }

3: Se debe crear previamente el el HoroscopeAdapter el cual debe exteder del ViewHolder

    class HoroscopeAdapter(private var horoscopeList: List<HoroscopeInfo> = emptyList():
    RecyclerView.Adapter<HoroscopeViewHolder>() {

**NOTA SE IMPLEMENTARAN 3 METODOS SOBREESCRITOS EN LA CUAL en el uno de ellos es onCreateViewHolder NO necesitamos pasar nunca el contexto en el adapter porque se recupera por medio del ViewGroup**

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
            return HoroscopeViewHolder( 
            //ASI SE RECUPERA
            LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope, parent, false)
            )
        }

4: Se debe crear un ViewHolder y que este este extendido de RecyclerView.ViewHolder(view)

    class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

5: El adapter y viewHolder necesitan estan separados en DISTINTOS  archivos

6:  otro metodo sobreescrito en el adapter es el OnViewHolder ,  se necesita render en el ViewHolder   para pintar los elementos del item:

7: Creamos el diseño diseño del xml  de como se veran los items "item_horoscope" y lo anclamos con binding

HoroscopeViewHolder el context LO PUEDO SACAR DE UNA VISTA:

        private val binding = ItemHoroscopeBinding.bind(view)

        fun render(horoscopeInfo: HoroscopeInfo, onItemSelected: (HoroscopeInfo) -> Unit) {
        val context = binding.tvTitle.context
        binding.ivHoroscope.setImageResource(horoscopeInfo.img)
        binding.tvTitle.text = context.getString(horoscopeInfo.name)
        
                binding.parent.setOnClickListener {
                    startRotationAnimation(binding.ivHoroscope, newLambda = {onItemSelected(horoscopeInfo)} )
        //            onItemSelected(horoscopeInfo)
        }
        }