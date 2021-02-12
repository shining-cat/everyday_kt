# everyday_kt
This application aims at helping the user to attain consistency in a daily practice.
Primarily designed for meditation, it can actually be used for any regular practice, such as arts, sport and so on.

The user can log practice sessions with some details, and set an alarm to be reminded to practice.
At the end of every session, a reward is awarded, in the form of a (not yet) cute creature picture.
Creatures are sorted in 5 levels of complexity, and awarded depending on the length of the session.
A longer uninterrupted streak of sessions grants a higher chance of being awarded more than one creature.
A day without practice will break the streak, and cause the loss of a creature.
Similarly, the longer the user skips practice, the higher the chance of losing more than one creature per day.

The user can review the sessions log, sorting them on date, length, and with/without guiding sound file.
Sessions details can be edited and manually created or deleted.
the whole log can also be exported (and reimported) as .csv, for backup purposes or an external more-in-depth analysis.

The user can also review the rewards acquired, and sort them on level, date of acquisition, and their lost/not lost status.
Creatures can be shared and customized, setting for a name and different colors for different body parts.
 
### Architecture


project objectives include:
- Kotlin
- Modular architecture
- Tests
- Coroutines
- Room + DAO + Repositories with Converters + Models
- WorkManager component
- Navigation component
- MotionLayout
- Lottie Animations
- Proguard or R8

### Project sources includes Jake Wharton's ProjectDependencyGraph to vizualize dependencies between modules
To use it:
- install Graphviz
- add install path to PATH windows var
- launch Gradle task projectDependencyGraph
- find .png file in build\reports\dependency-graph
