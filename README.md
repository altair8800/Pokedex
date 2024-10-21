# Pokedex
My priorities on this project were to deliver a working product that lets the user browse the Pokemon API and see details about any chosen Pokemon.

I used some slightly outdated technologies in order to save time - these include:

- Material 1 instead of Material 3 for the UI design language
- Groovy gradle scripts instead of Kotlin DSL
- Manual version resolution instead of a Version Catalog
- KAPT instead of KSP
- Moshi instead of KSerialization

If I had more time I would migrate these technologies to their appropriate modern equivalents, along with the other improvements detailed in the TODOs throughout the codebase.

Missing features include:

- Pull to refresh
- More interesting list items + dividers
- A local DB to cache pokemon data
- A more graceful way to handle and display errors to the user