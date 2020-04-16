# Data Messenger
> Deliver the power of AutoQL to your users through Data Messenger, a state-of-the-art conversational interface you can easily build into your existing application.
<!-- [Support by Android version](#support-by-android-version) -->

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

## Support by Android version
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

## Props
 <table>
  <thead>
    <tr><th>Prop Name</th><th>Data Type</th><th>Default Value</th></tr>
  </thead>
  <tbody>
    <tr><td>isVisible</td><td>Boolean</td><td>true</td></tr>
    <tr><td>placement</td><td>Int: TOP_PLACEMENT || BOTTOM_PLACEMENT || LEFT_PLACEMENT || RIGHT_PLACEMENT || NOT_PLACEMENT</td><td>RIGHT_PLACEMENT</td></tr>
    <tr><td>title</td><td>String</td><td>"Data Messenger"</td></tr>
    <tr><td>userDisplayName</td><td>String</td><td>"there"</td></tr>
    <tr><td>introMessage</td><td>String</td><td>"Hi %s! Let\'s dive into your data. What can I help you discover today?"</td></tr>
    <tr><td>inputPlaceholder</td><td>String</td><td>"Type your queries here"</td></tr>
    <tr><td>maxMessages</td><td>Int</td><td>0</td></tr>
    <tr><td>clearOnClose</td><td>Boolean</td><td>false</td></tr>
    <tr><td>enableVoiceRecord</td><td>Boolean</td><td>true</td></tr>
    <tr><td>autoQLConfig</td><td>AutoQLConfig</td><td>AutoQLConfig()</td></tr>
    <tr><td>dataFormatting</td><td>DataFormatting</td><td>DataFormatting()</td></tr>
    <tr><td>themeConfig</td><td>ThemeConfig</td><td>ThemeConfig()</td></tr>
  </tbody>
</table>

`isVisible`: Determines whether Data Messenger is open in the
application interface or not. You have full control over the visibility
of the widget by using your own state.
<br>
`placement`: Determines the edge of the screen where Data Messenger is placed.
<br>