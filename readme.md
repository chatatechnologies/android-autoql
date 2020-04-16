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

>`isVisible`: Determines whether Data Messenger is open in the
application interface or not. You have full control over the visibility
of the widget by using your own state.
```kotlin
//isChecked is a Boolean
bubbleHandle.isVisible = isChecked
```
>`placement`: Determines the edge of the screen where Data Messenger is placed.
```kotlin
//placement is a Integer
//Constant -> TOP_PLACEMENT || BOTTOM_PLACEMENT || LEFT_PLACEMENT || RIGHT_PLACEMENT || NOT_PLACEMENT
bubbleHandle.placement = placement
```
>`title`: Text that appears in the header of the default Data Messenger
view. You must provide an empty string if you do not want text to appear
here, otherwise the default text (Data Messenger) will apply.
```kotlin
//title is a String
bubbleHandle.title = title
```
>`userDisplayName`: Name used in the intro message (ex. "Hi Carlos!").
You can customize this value using names from your own database.
```kotlin
//userDisplayName is a String
bubbleHandle.userDisplayName = userDisplayName
```
>`introMessage`: Customize the default intro message using your own brand
voice and custom copy. The userDisplayName prop will be ignored if this
is provided.
```kotlin
//introMessage is a String
bubbleHandle.introMessage = introMessage
```
>`inputPlaceholder`: Customize the placeholder for the Query Input
(natural language query search bar).
```kotlin
//inputPlaceholder is a String
bubbleHandle.inputPlaceholder = inputPlaceholder
```
>`maxMessages`: Maximum number of messages that can be displayed in the
Data Messenger interface at one time. A message is any input or any
output in the interface. This means a query entered by a user
constitutes one message, and the response returned in the Data Messenger
interface constitutes another message. If a new message is added and you
have reached the maximum, the oldest message will be erased.
```kotlin
//maxMessages is a Int
bubbleHandle.maxMessages = maxMessages
```
>`clearOnClose`: Determines whether or not to clear all messages when the
widget is closed. Note: The default intro message will appear when you
reopen the widget after closing it.
```kotlin
//clearOnClose is a Boolean
bubbleHandle.clearOnClose = clearOnClose
```
>`enableVoiceRecord`: Enables the voice-to-text button.
Note: The voice-to-text feature uses Web Speech API, so this feature is
only available for Chrome users.
```kotlin
//enableVoiceRecord is a Boolean
bubbleHandle.enableVoiceRecord = enableVoiceRecord
```

#### autoQLConfig Prop
<table>
  <thead>
    <tr><th>Key</th><th>Data Type</th><th>Default Value</th></tr>
  </thead>
  <tbody>
    <tr><td>enableAutocomplete</td><td>Boolean</td><td>true</td></tr>
    <tr><td>enableQueryValidation</td><td>Boolean</td><td>true</td></tr>
    <tr><td>enableQuerySuggestions</td><td>Boolean</td><td>true</td></tr>
    <tr><td>enableDrilldowns</td><td>Boolean</td><td>true</td></tr>
    <tr><td>enableColumnVisibilityManager</td><td>Boolean</td><td>true</td></tr>
    <tr><td>debug</td><td>Boolean</td><td>true</td></tr>
  </tbody>
</table>

>enableAutocomplete: Automatically populates similar query suggestions as users enter a query, so they get results more quickly and easily. If enabled, suggested queries will begin to appear above the Query Input bar as the user types.
```kotlin
//enableAutocomplete is a Boolean
bubbleHandle.autoQLConfig.enableAutocomplete = enableAutocomplete
```
>enableQueryValidation: Catches and verifies references to unique data, so users always receive the data they need. If enabled, the natural language query entered by a user will first go through the validate endpoint. If the query requires validation (ex. the input contains reference to a unique data label), suggestions for that label will be returned in a subsequent message, allowing the user to verify their input before executing their query.
```kotlin
//enableQueryValidation is a Boolean
bubbleHandle.autoQLConfig.enableQueryValidation = enableQueryValidation
```
>>For example: If you query, 'How much money does Nikki owe me?', validation may detect that there is no 'Nikki' label, but there are labels called 'Nicki', and 'Nik' in your database. The message will then let you select the appropriate label and run the corresponding query.

>>If this value is false, the query will bypass the validate endpoint and be sent straight to the query endpoint.

>enableQuerySuggestions: Enables option for user to clarify meaning in cases where their original query lacked context or could be interpreted in multiple different ways. If enabled, in cases where the query input was ambiguous, a list of suggested queries will be returned for the user to choose from, leading to more efficient and accurate responses. If this is false, a general error message will appear in its place.
```kotlin
//enableQuerySuggestions is a Boolean
bubbleHandle.autoQLConfig.enableQuerySuggestions = enableQuerySuggestions
```
>enableDrilldowns: When a table or chart element is clicked by a user, a new query will run automatically, allowing the user to "drilldown" into the data to obtain a detailed breakdown of the figure returned by entry. If this is false, nothing will happen when a table or chart element is clicked.
```kotlin
//enableDrilldowns is a Boolean
bubbleHandle.autoQLConfig.enableDrilldowns = enableDrilldowns
```
>enableColumnVisibilityManager: Column Visibility Manager allows the user to control the visibility of individual columns when query results are returned in a table. Users can access the Column Visibility Manager to adjust their visibility preferences by clicking the "eye" icon in the Options Toolbar and selecting or deselecting columns. Once set, visibility preferences will be persisted. Any future query containing columns that were previously shown or hidden by the user will also reflect these changes. The user can access the Column Visibility Manager to make changes to these visibility preferences at any time.
```kotlin
//enableColumnVisibilityManager is a Boolean
bubbleHandle.autoQLConfig.enableColumnVisibilityManager = enableColumnVisibilityManager
```
>debug: If this value is true, the user can copy the full query language (QL) statement (ex. SQL statement) that was dynamically generated from their natural language query input by clicking "Copy generated query to clipboard".
 ```kotlin
//debug is a Boolean
bubbleHandle.autoQLConfig.debug = debug
```
#### dataFormatting Prop
<table>
  <thead>
    <tr><th>Key</th><th>Data Type</th><th>Default Value</th></tr>
  </thead>
  <tbody>
    <tr><td>currencyCode</td><td>String</td><td>"USD"</td></tr>
    <tr><td>languageCode</td><td>String</td><td>"en-US"</td></tr>
    <tr><td>currencyDecimals</td><td>Int</td><td>2</td></tr>
    <tr><td>quantityDecimals</td><td>Int</td><td>1</td></tr>
    <tr><td>monthYearFormat</td><td>String</td><td>"MMM YYYY"</td></tr>
    <tr><td>dayMonthYearFormat</td><td>String</td><td>"MMM DD, YYYY"</td></tr>
  </tbody>
</table>