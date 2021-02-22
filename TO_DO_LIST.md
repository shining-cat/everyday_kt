# EVERYDAY TODO BUCKET LIST

- [x] fix bug on vectorDrawable color => android:fillColor="?attr/colorOnPrimary" works on all others vectors, but crashes on this one
- [x] check arch still conforms to updated standards
- [x] chek and update, then insert copyright license mention
- [ ] fix and update tests to conform to updated standards
  - [ ] commons module -> write tests on SharedPrefsHelper
  - [ ] locale module
  - [ ] navigation module -> write tests on Actions
  - [ ] repositories module
- [ ] maybe improve tests auto-run + reporting with jacoco, see:https://prokash-sarkar.medium.com/multi-module-multi-flavored-test-coverage-with-jacoco-in-android-bc4fb4d135a3
- [ ] find info on building optional feature: rewards mechanism and statistics will only be added as a feature later, in order to be able to build a working version faster => we may have to split the screens module up to dissociate "bonus" features from "core" features, question is where to put the associated models, dto, repos, if not in the corresponding modules?
- [ ] check if possible to set github actions up, have some kind of simple CI/CD running maybe? (see:https://medium.com/google-developer-experts/github-actions-for-android-developers-6b54c8a32f55)
- [ ] build launch icon from rewards assets, insert as vector adaptative icon (see https://medium.com/androiddevelopers/vectordrawable-adaptive-icons-3fed3d3205b5)
- [ ] update file access method for import/export (Storage Access Framework API aka Scoped Storage). As I understand it for now, since the access we need is for non-media files (csv), we will have to use the system file picker, which is already  planned for the import, and is actually a better way for the export than the present version with a dialog showing the export file location. Maybe some difficulties regarding compatibility with older versions of android. (see: https://www.youtube.com/watch?v=UnJ3amzJM94 and https://medium.com/swlh/sample-for-android-storage-access-framework-aka-scoped-storage-for-basic-use-cases-3ee4fee404fc)
