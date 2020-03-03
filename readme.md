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

