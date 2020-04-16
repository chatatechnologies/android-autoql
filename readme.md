# Data Messenger
> Deliver the power of AutoQL to your users through Data Messenger, a state-of-the-art conversational interface you can easily build into your existing application.

## Setup View
> Init BubbleHandle view

#### import Class

```kotlin
import chata.can.chata_ai.view.bubbleHandle.BubbleHandle
```

###### Initialize object bubble Handle
```kotlin
private lateinit var bubbleHandle: BubbleHandle

bubbleHandle = BubbleHandle(this@MainActivity)
```

## Note: support by Android version
Tenemos que evaluar la version de Android donde va correr nuestra
aplicación. Esto se puede revisar con el siguiente código:

```kotlin
/**
 * Build.VERSION_CODES.M is 23
 * Build.VERSION_CODES.M in android.os.Build class
 */
private fun isMarshmallow() = Build.VERSION.SDK_INT >= 23
```
El fragmento de código anterior se puede usar de la siguiente manera.
```kotlin
if (isMarshmallow())
{
  //Code when Android version is major Marshmallow 
}
else
{
  // code for to init BubbleHandle
}
```

Otra cosa a tener en cuenta es la superposición de vistas en pantalla.
Vamos a preguntarle a nuestro dispositivo si la aplicación podrá
dibujar elementos super posicionados en pantalla. Esto se hace de la
siguiente manera:
```kotlin
/**
 * Build.VERSION_CODES.M is 23
 * M is for Marshmallow!
 */
@RequiresApi(api = Build.VERSION_CODES.M)
private fun canDrawOverlays() = Settings.canDrawOverlays(/*context*/this)
```

A continuación se muestra como verificar la super posición en nuestra
aplicación:

```kotlin
if (!canDrawOverlays())
{
  with(Intent(
    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
    Uri.parse("package:$packageName")))
  {
    startActivityForResult(this, overlayPermission)
  }
}
else
{
  // code for to init BubbleHandle
}
```

A continuación se mostrara una ventana que le preguntara al usuario si
desea permitir la super posición de la aplicación en la pantalla.
Si el usuario o no acepta la condición de la super posición recibiremos
la respuesta en otro método. A continuación el método y el como
aprovechar el llamado del método `onActivityResult()`:

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
{
  super.onActivityResult(requestCode, resultCode, data)
  if (isMarshmallow())
  {
    // code for to init BubbleHandle 
  }
}
```
Con estas configuraciones son suficientes para que la vista bubbleHandle
sea visible.