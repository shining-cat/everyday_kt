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
- [x] build about dialog
- [x] upgrade LongExtensions duration formatting
- [x] tests on LongExtensions
- [x] modification on LongExtensions duration parsing: remove modulo on 24 hours, allow forms like 127h 23mn 07s.update tests, simplify params with identical default values...
- [x] update dependencies, kotlin, gradle and AGP
- [ ] remove jacoco, ktlint and detekt as we don't use them yet, static analysis will be implemented later on, through a github CI
- [ ] prevent buildconfig generation for all modules except App: https://medium.com/dipien/stop-generating-the-buildconfig-on-your-android-modules-7d82dd7f20f1
- [ ] move from livedata used as events to recommended observable objects now available in kotlin (SingleLiveEvent) or even move completely from livedata to StateFlow + ShareFlow?
- [ ] add language setting to allow change without following the OS language
- [ ] build launch icon from rewards assets, insert as vector adaptive icon (see https://medium.com/androiddevelopers/vectordrawable-adaptive-icons-3fed3d3205b5)
- [ ] use launch icon to build a splashscreen, see: https://medium.com/geekculture/implementing-the-perfect-splash-screen-in-android-295de045a8dc
- [ ] write about text
- [ ] add custom rules to detekt to put else, catch, finally on new line => this seems quite heavy work, we may skip this, but the pb is that ktlint rules contradict ours
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
  - [x] modified SessionPreset to hold more fields => must update tests and usages
  - [x] modified CreateSessionPresetUseCase and UpdateSessionPresetUseCase to require editTime on execute => update tests and usages
  - [x] added a viewmodel for sessionpreset dialog => plug sessionpreset dialog UI to viewmodel's sessionPresetUpdatedLiveData
      - [x] when audio file is selected, display metadata
      - [x] when audio file is selected, if duration is -1L (= could not retrieve) we need user duration selection, maybe set duration to 0s and prevent dialog validation, deactivate toast on duration clicked in this case
  - [x] handle audio session preset: open file picker or smth similar, get the audio file uri and store along in SessionPreset, handle display in homefragment list
  - [x] pb when accessing files with uris. find the way to handle api <29 , api = 29, and api >29 -> ok now for session presets (persisting uri access grant needed to use the intent ACTION_OPEN_DOCUMENT and not ACTION_GET_CONTENT, only for immediate access)     
  - [x] maybe remove the duration field for audio guided sessions, we will probably rather measure the elapsed time between the start and end of the audio file...
  - [x] pb when deleting last preset: icon drawn be swipelistener is still present => find a way to reset canvas!
  - [x] switch FAB action to speed dial behaviour. Options: "add free audio session preset", "add audio session preset", "add timed session preset", "add free timed session preset" => now the sessionpresetdialog will have 2 separate versions for each type
    - [x] create drawables for speed dial items
  - [x] split session preset dialog into dedicated versions (audio vs timed)
  - [x] create corresponding dedicated sessionPresets viewholders
  - [x] bug when "editing" and "canceling" sessionpreset, item is not put back in place in the list
  - [x] auto-close fab speed dial when session preset has been created (or maybe even when simply opening the dialog)
  - [x] sessionpresets FAB chips background color is too close to the one of elements behind it, readability is bad, explore solutions as: semi-opaque background on whole screen? morph main FAB into a background for the speeddials? add a border on chips?
  - [x] include implementation AndroidLibraries.lifecycle_viewmodel_ktx and switch every mainScope.launch { ... ioscope.async{...}.await} to viewModelScope.launch{ ...withContext(Dispatchers.IO){..}}, and remove all appDispatchers
  - [x] fix tests broken by sessionPreset division into dedicated types: createSessionPresetUsecase, UpdateSessionPresetUsecase, RewardRepositoryImpl, SessionPresetRepositoryImpl, SessionPresetRepositoryImpl
  - [x] fix test in HomeViewModelTest not passing when running the whole jacoco testing gradle task, but passing individually
  - [x] tests on
     - [x] HomeViewModel
     - [x] SplashScreenViewModel
     - [x] AbstractSessionPresetViewModel => no, test concrete methods of this abstract class inside each of its concrete implementation, this will ensure that the end-of-the-line implementation is tested
         - [x] AudioFreeSessionPresetViewModel
         - [x] AudioSessionPresetViewModel
         - [x] TimedFreeSessionPresetViewModel
         - [x] TimedSessionPresetViewModel
  - [ ] find a way to animate hide/show "add session preset" fab when scroll detected, show again on release

### launch session
  - [ ] launchSessionUseCase (SessionViewModel) => don't know yet how to do this: need something capable of running in the background whatever happens to process
  - [ ] launchSessionUseCase tests
  - [ ] recordStartMoodUseCase + start session mood input dialog -> temp save to sharedPrefs?
  - [ ] recordStartMoodUseCase tests
  - [ ] recordEndMoodUseCase + end session mood input dialog -> temp save to sharedPrefs?
  - [ ] recordEndMoodUseCase tests
  - [ ] recordCompletedSessionUseCase
  - [ ] recordCompletedSessionUseCase tests
  - [ ] session running screen countdown + play audio if required

### reminder
  - [ ] setup alarm feature

### stats
  - [ ] loadStatsSummaryUseCase (homeViewModel)
  - [ ] display stats summary on Home fragment
  - [ ] importSessionsUseCase (from settings screen) / warning: conform to Storage Access Framework aka Scoped Storage, conditions depending on running API version may be different :/
  - [ ] exportSessionsUseCase (from settings screen) / warning: conform to Storage Access Framework aka Scoped Storage, conditions depending on running API version may be different :/
  - [ ] loadSessionsRecordedUseCase
  - [ ] display recorded sessions as list in Sessions fragment
  - [ ] loadSpecificSessionDetails
  - [ ] display one session details
