# Vamos a proveer la informacion de manera local

** Para proveer la informaciòn de manera local sin necesidad de la conexion a intenet entonces necesitamos :
    
    class HoroscopeProvider @Inject constructor() {
        fun getHoroscopes(): List<HoroscopeInfo> {
         return listOf(Aries,......)
        }   
    }

Donde HoroscopeInfo se inyecta:

    data object Aries:HoroscopeInfo(R.drawable.aries, R.string.aries) .......


# Animaciòn en android : 

En este caso en el HoroscopeViewHolder a traves de la funcion lambda estamos ejecutando invocando la accion  en el click

Aqui mandamos a traves del metodo de animacion una funcion lambda la cual se ejecutara cuando acabe startRitation

    binding.parent.setOnClickListener {
        //onItemSelected se ejecuta cuando se invoque en el withEndAction
        startRotationAnimation(binding.ivHoroscope, newLambda = {onItemSelected(horoscopeInfo)} )
    }


    private fun startRotationAnimation(view:View, newLambda:()->Unit){
        view.animate().apply {
            duration = 500 //tiempo de animacion
            interpolator = LinearInterpolator() //Una animacion constante de inicio a fin, osea la misma velocidad de inicio a fin
            rotationBy(360f) // hara una vuelta de los objetos
            withEndAction { newLambda() } // se ejecuta al acabar la animacion
            start() //invocacion de la animacion 
        }
    }



# Animacion con xml en android creamos el slide_up.xml:

    <set xmlns:android="http://schemas.android.com/apk/res/android">
        <translate
            android:duration="400" //duracion
            android:fromYDelta="100%" //y empieza desde abajo
            android:toYDelta="0"/> // 0 es elpunto del medio donde cruz
    </set> //el set es para muchas animaciones en una sola


    <scale xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="1000"
    android:fromXScale="1.0" // toma x y y en su es
    android:fromYScale="1.0"
    android:pivotX="50%"
    android:pivotY="30%"
    android:toXScale="3.0" //escala su tamño del ancho un 3%
    android:toYScale="3.0" />

# En la animaciòn es debemos tener cuidado con el gone y invisible

            android:visibility="invisible" //si se renderiza

            android:visibility="gone" //se renderiza hasta que android se ejecuta

cuando se implementa de esta manera , entonces debemos linkearlo en codigo:

    val slideUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up) //animation
    
            slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) { //antes de iniciar con la animacion hacer
                    binding.reverse.isVisible = true
                }
    
                override fun onAnimationEnd(p0: Animation?) {//cuando acaba la animacion
                    growCard()
                }
    
                override fun onAnimationRepeat(p0: Animation?) {}
    
            })
    
            binding.reverse.startAnimation(slideUpAnimation) //le pasamos a la vista el efecto de la animacion
        


# NAVIGACION SEGURA CON PARAMETROS

En plugins del gradle y en el de la app agregar :

    id ("androidx.navigation.safeargs.kotlin") version "2.7.1" apply false

para la Navegacion en android es a traves de acciones :

    findNavController().navigate(HoroscopeFragmentDirections.actionHoroscopeFragmentToHoroscopeDetailActivity(type))

para mandar el argumento en este caso "type" entonces debemos CONFIGURAR en el main_graph En la actividad que espera recibir el argumento en este caso en horoscopeDetailActivity:

    <argument
    android:name="type" //nombre de la variable
    app:argType="com.aristidevs.horoscapp.domain.model.HoroscopeModel" /> //tipo de la variable en este caso ENUM

**IMPORTANTE EN ANDROID NO ES VIABLE MANDAR TODO EL OBJETO , DEBEMOS ENVIAR UNA REFERENCIA POR ESO EL ENUM**


Para recibir los parametros enviados es con :

    private val args: HoroscopeDetailActivityArgs by navArgs() //Nombre de la activity en este caso HoroscopeDetailActivity segguido del delegado   by AnavArgs

y en el OnCreate se recupera con :

    args.type

**data object es un tipo de dato nuevo en kotlin que  lo que realiza es que permite el .toString**


# Drawable y Res como recursos forzosos y asi evitar la ambiguedad

    @DrawableRes val image:Int, //etiqueta que solo admite recurso drawable
    @StringRes val text:Int //solo admite etiqueta string

# Intent Explicito

    private fun shareResult(prediction:String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, prediction)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
