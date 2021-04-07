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
- Average revenue by area last year: bi -> 13/7/2020
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
- total tickets by customer per month: stacked_area; stacked_line
- jobs by area by customer: stacked_area; stacked_line
- total revenue by month

# Queries on dashboard (Title - Query)
- (Historical Cash on Hand) - monthly cash on hand last 12 months
- (Total Profit by Customer (2019)) - Total profit by customer last year
- (2019 Tracking Category Revenue) - total revenue by month by tracking category
- (Monthly Gross Margin 2) - who owes me money

Pass apichata
key apichata123


[Links](#links)
## Links

Error reference id
- Error: 1.1.430
- Query: all sales
- This is a suggestion
- Content
    {
      "data": {
        "query_id": "q_z7raa9mIRV2BziraFoeP2Q"
      },
      "message": "I want to make sure I understood your query. Did you mean:",
      "reference_id": "1.1.430"
    }

- Error: 1.1.420
- Query: Total Profit by Customer (2019), all estimates last year
- This is a text with report link
- Content
    {
      "data": {
        "query_id": "q_OVeP8TsLRfuBQ_kzfDTLsg"
      },
      "message": "Connection Issue: It looks like you're having trouble accessing AutoQL. Try querying again. If this problem persists, please <report> this issue.",
      "reference_id": "1.1.420"
    }

- Error: 1.9.502
- Query: total hours utilization for equipment by month in 2017 for water trailer
- This is a Query Translation Error
- Content
    {
      "data": {
        "query_id": "q_pio9lOQnRWu7C-jLYvXH_Q"
      },
      "message": "Query Translation Error: We've encountered an error in the query translation process. We're aware of this issue and are working to fix it as soon as possible.",
      "reference_id": "1.9.502"
    }