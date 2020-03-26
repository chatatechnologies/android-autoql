# Views

## ChatDrawer
> This view execute autocomplete and requesting queries

## DrawerHandle
> This view is the base widget circle in main view

#### Integrate lambdas in library

_Date Mar 03, 2020_
> Support on [Stack Overflow](https://stackoverflow.com/a/57821039)
```kotlin
fun setInitializationCallback(listener: () -> Unit): Builder
{
    bubblesManager?.listener = object: OnInitializedCallback {
        override fun onInitialized()
        {
            listener()
        }
    }
    return this
}
```

_replace by_
```kotlin
fun setInitializationCallback(listener: OnInitializedCallback): Builder
{
    bubblesManager?.listener = listener
    return this
}
```

# Queries
- All invoices: <b>table</b>
- All invoices to Billy Bob: <b>table</b>
- All sales: <b>table</b>
- All invoices last 4 types: <b>suggestion</b>
- All invoices above 4
- Totel salas: <b>full suggestion</b>
- Total Sales: <b>table</b>
- Total invoices: <b>table</b> -> $
- Totel opereting expinses bu accaunt: <b>full suggestion</b>
