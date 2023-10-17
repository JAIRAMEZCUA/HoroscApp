# DOCUMENTACION DE MI CODIGO


# ESTRUCUTURA DE CLEAN ARCHITECTURE
 * DATA --> nos provee de toda la informacion , guarda info , envia info y recupera
 * UI --> VAN IR TODAS LAS PANTALLAS , EL VIEWMODEL, el que pinta las pantallas
 * DOMAIN --> Calculara toda la logica de negocio


* La idea es tener una activity y muchos fragments.

# SETUP VIEWBINDING 
Para viewBinding es con :
buildFeatures{
viewBinding = true
buildConfig = true
}

# En una activity se implementa:
private lateinit var binding: ActivityMainBinding
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)
}


# En un fragment se implementa:
private var _binding: FragmentLuckBinding? = null
private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLuckBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

# Navigation component setUp
val navVersion = "2.7.1"
val cameraVersion = "1.2.3"

    //NavComponent
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

Paso 1 : Crear un paquete *navigation*
Paso 2 : Crear mi main_graph --> las rutas de  lo que tenemos que cargar
Paso 3 : Crear un FragmentContainerView
paso 4 : En MainActivity esta mi FragmentContainerView el cual voy a enganchar con mi graph  con  
        app:navGraph="@navigation/main_graph"
        android:name="androidx.navigation.fragment.NavHostFragment"
        app:defaultNavHost="true"





# PARA CREAR UN MENU 

Paso 1 : Crear un paquete llamado menu en res
Paso 2 : Crear un un menu.xml donde agreguemos en este los items en este ejemplo son las referencia a  fragments

        <item
        android:id="@+id/horoscopeFragment" //ID DEL FRAGMENT QUE DEBE CONCIDIR CON EL id del fragment en el graph --> horoscopeFragment
        android:title="@string/horoscope"
        android:icon="@drawable/ic_horoscope"/>

Paso 3: Integrarlo en la activity , en la mayoria de los casos es  donde esta mi FragmentContainer

        <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavView"
        android:layout_width="0dp"
        android:background="@color/primaryDark"
        android:layout_height="wrap_content"
        app:itemIconTint="@color/bottom_nav_selector"
        app:itemTextColor="@color/bottom_nav_selector"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:itemActiveIndicatorStyle="@null"
        app:menu="@menu/bottom_menu"
        />

NOTA:           app:itemIconTint="@color/bottom_nav_selector" Este color esta en la carpeta de color, y es un selector el cual cuando esta clickeado cambia de color
                app:itemTextColor="@color/bottom_nav_selector" Cambia el color del texto

Paso 4: En mi Activity anclar el menu con el navigationGraph

        private fun initNavigation() {
                val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
                navController = navHost.navController
                binding.bottomNavView.setupWithNavController(navController)
        }


# Formato en el estilo de las letras:

*    Las fuentes se descargan de : https://fonts.google.com/
*    se integran en res -> font -> fuente(.ttf)
*    en el texto del xml  se agrega  style="@style/Title" , y el estilo se añade la caperta   res ->values -> themes.xml
    
    <style name="Title">
                <item name="android:textStyle">bold</item>
                <item name="android:fontFamily">@font/dancing</item>
                <item name="android:textSize">30sp</item>
                <item name="android:textColor">@color/secondary</item>
    </style>


