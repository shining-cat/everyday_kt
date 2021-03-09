
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
 
## Architecture
project objectives include:

 - Kotlin
 - Modular architecture
 - MVVM
 - Tests
 - Coroutines
 - Room + DAO + Repositories with Converters + Usecases + Models
 - WorkManager component
 - Navigation component
 - MotionLayout
 - Lottie Animations

## Gradle plugins included
### Jake Wharton's ProjectDependencyGraph to vizualize dependencies between modules
To use it:
- install Graphviz on your computer
- add install path to PATH windows var
- launch gradle task other>projectDependencyGraph
- find .png file in build\reports\dependency-graph

### Ben Manes' Gradle Versions Plugin to check for dependencies available updates
To use it: launch gradle task everyday>everydayDependencyUpdates

## "Roadmap" for Everyday:

### first deployed version will include:

 - settings
 - import/export data
 - create, edit and delete sessions presets swipe right to open edit screen (delete button is inside edit screen)
 - launching session from preset
 - mood recording
 - session run with simple countdown and animation (pulse) based on audio-file duration or preset length. Play audio if selected
 - recording session (with audio metadata when adequate)
 - displaying sessions recorded as list (no calendar view, no statistics) + detail view per session
 - about page + text

**=> this will then be referred to as the basic version of the app: EVERYDAY LITE.
this version will continue to exist and should be updated and deployed alongside the full version when it is ready**

### Features planned for the full version of the app which will be the one called EVERYDAY, random order for now:

 - manual session input and editing
 - reorder sessions preset -> swipe left to move preset to top of list
 - create shortcut on device home screen to launch session according to preset -> long press on preset offers option in dialog
 - display recorded sessions in a calendar view
 - rewards basic mechanism (acquisition of one reward per session the reward's level depends on the session length, display as mini-cards in dedicated screen)
 - rewards complete mechanism: gamification: increment number of rewards for longer streaks, lose rewards for streak breaking
 - rewards customization display reward selected in big card and display date, level, optional name, etc. allow name and colors customization
 - statistics module

### Future features ideas, random order:

 - achievements badges? (1 week, 1 month etc)
 - gamification upgrade: user can choose to exchange rewards (not escaped) for a better one (ex: éxlevel1 for 1xlevel2 etc)
 - different session countdown animations (lava-lamp, screen filling, etc)
 - user can choose to have an irregular sound played during a session in addition to a regular interval or no sound
 - security lock on app start, with fingerprint/pin code + setting to activate/deactivate it (see Nextcloud Password android app for reference) see: https://proandroiddev.com/biometrics-in-android-50424de8d0e
 - add "type" attribute for sessions, associated with label, color, description
	 - the type is shown on the session short view (list view and calendar view) as a color sticker, and on the session detailed view as a label
	 - also on the preset button
	 - Clicking on this label displays the type description in a bottom dialog
	 - Users can attribute a type to any session (in the “start session” dialog)
	 - screen to admin sessions types, accessible from “Settings”
	 - shortcut to the “admin sessions types” screen from the “attribute a type to a session” one
	 - add a filter for session type to the list view filters
	 - integrate this feature to statistics view
		
