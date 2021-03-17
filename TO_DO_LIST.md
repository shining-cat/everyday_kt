# EVERYDAY TODO BUCKET LIST

- [x] fix bug on vectorDrawable color => android:fillColor="?attr/colorOnPrimary" works on all others vectors, but crashes on this one
- [x] check arch still conforms to updated standards
- [x] check and update, then insert copyright license mention
- [x] fix and update tests to conform to updated standards
  - [x] locale module
  - [x] repositories module
  - [x] commons module -> write tests on SharedPrefsHelper
- [x] fix Jacoco error log at each app launch
- [x] home activity is launched twice at startup
- [x] find a cleaner way to handle duration in BottomDialogDismissibleSpinnersDurationAndConfirm
- [x] implement detekt plugin
- [x] missing failing test cases in repositories in repositories, incomplete error output and exception error output
- [ ] build launch icon from rewards assets, insert as vector adaptive icon (see https://medium.com/androiddevelopers/vectordrawable-adaptive-icons-3fed3d3205b5)
- [x] build about dialog
- [ ] tests on HomeViewModel
- [ ] write about text + build about dialog
- [ ] add custom rules to detekt to put else, catch, finally on new line => this seems quite heavy work, we may skip this, but the pb is that ktlint rules contradict our own
- [ ] decide how to handle remaining errors reported by detekt (too many functions in SharedPrefsHelper is ok, too many parameters in method, etc.)
- [ ] find info on building optional feature: rewards mechanism and statistics will only be added as a feature later, in order to be able to build a working version faster => we may have to split the screens module up to dissociate "bonus" features from "core" features, question is where to put the associated models, dto, repos, if not in the corresponding modules?
- [ ] check if possible to set github actions up, have some kind of simple CI/CD running maybe? (see: https://medium.com/google-developer-experts/github-actions-for-android-developers-6b54c8a32f55)

##  roadmap:
### launching app
  - [x] initUseCase (splashViewModel) : load user prefs or set defaults in if first launch
  - [x] tests on initUseCase

### sessions presets
  - [x] addSessionPresetUseCase (homeViewModel)
  - [x] addSessionPresetUseCase tests
  - [x] loadSessionsPresetsUseCase (homeViewModel)
  - [x] loadSessionsPresetsUseCase tests
  - [x] deleteSessionPresetUseCase (homeViewModel)
  - [x] deleteSessionPresetUseCase tests
  - [x] editSessionPresetUseCase (homeViewModel)
  - [x] editSessionPresetUseCase tests
  - [x] plug add session preset to FAB on Home Fragment, display and plug usecase to creation dialog (without audio mechanics)
  - [x] display sessions presets on Home fragment
  - [x] plug edit session preset to swipe left on preset on Home Fragment, display and plug usecase to edition/suppression dialog
  - [ ] handle audio session preset: open file picker or smth similar, get the audio file uri and store along in SessionPreset, handle display in homefragment list
  - [ ] find a way to animate hide/show "add session preset" fab when scroll detected, show again on release, for now it only works without animation

### launch session
  - [ ] launchSessionUseCase (SessionViewModel) => don't know yet how to do this: need something capable of running in the background whatever happens to process
  - [ ] recordStartMoodUseCase + start session mood input dialog -> temp save to sharedPrefs?
  - [ ] recordStartMoodUseCase tests
  - [ ] recordEndMoodUseCase + end session mood input dialog -> temp save to sharedPrefs?
  - [ ] recordEndMoodUseCase tests
  - [ ] recordCompletedSessionUseCase
  - [ ] recordCompletedSessionUseCase tests
  - [ ] session running screen countdown + play audio if required

### stats
  - [ ] loadStatsSummaryUseCase (homeViewModel)
  - [ ] display stats summary on Home fragment
  - [ ] importSessionsUseCase (from settings screen) / warning: conform to Storage Access Framework aka Scoped Storage, conditions depending on running API version may be different :/
  - [ ] exportSessionsUseCase (from settings screen) / warning: conform to Storage Access Framework aka Scoped Storage, conditions depending on running API version may be different :/
  - [ ] loadSessionsRecordedUseCase
  - [ ] display recorded sessions as list in Sessions fragment
  - [ ] loadSpecificSessionDetails
  - [ ] display one session details
