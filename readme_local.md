# Documentation
    https://chata.readme.io/docs/react-data-messenger?

# Website demo
    https://github.com/chatatechnologies/react-autoql
    https://chata-ai-test-page.herokuapp.com
    https://vicente_rincon.gitlab.io/widget/

# Views

## ChatDrawer
> This view execute autocomplete and requesting queries
F
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
- Count invoices: simpleText
- All invoices: <b>table</b>
- All invoices to Billy Bob: <b>table</b>
- All sales: <b>table</b>
- All invoices last 4 types: <b>suggestion</b>
- All invoices above 4
- Totel salas: <b>full suggestion</b>
- Total Sales: <b>table</b>
- Total invoices: <b>table</b> -> $
- Totel opereting expinses bu accaunt: <b>full suggestion</b>
- pie chart total sales

# Queries on dashboard (Title - Query)
- (Historical Cash on Hand) - monthly cash on hand last 12 months
- (Total Profit by Customer (2019)) - Total profit by customer last year
- (2019 Tracking Category Revenue) - total revenue by month by tracking category
- (Monthly Gross Margin 2) - who owes me money

Pass apichata
key apichata123


[Links](#links)
## Links