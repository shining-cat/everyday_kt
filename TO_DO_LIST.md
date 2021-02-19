# EVERYDAY TODO BUCKET LIST

- [x] fix bug on vectorDrawable color => android:fillColor="?attr/colorOnPrimary" works on all others vectors, but crashes on this one
- [x] check arch still conforms to updated standards
- [ ] fix and update tests to conform to updated standards
- [ ] check if possible to set github actions up, have some kind of simple CI/CD running maybe?
- [ ] build launch icon from rewards assets, insert as vector adaptative icon (see https://medium.com/androiddevelopers/vectordrawable-adaptive-icons-3fed3d3205b5)
- [ ] find info on building optional feature: rewards mechanism and statistics will only be added as a feature later, in order to be able to build a working version faster => we may have to split the screens module up to dissociate "bonus" features from "core" features, question is where to put the associated models, dto, repos, if not in the corresponding modules?
- [ ] update file access method for import/export (Storage Access Framework API aka Scoped Storage). As I understand it for now, since the access we need is for non-media files (csv), we will have to use the system file picker, which is already  planned for the import, and is actually a better way for the export than the present version with a dialog showing the export file location. Maybe some difficulties regarding compatibility with older versions of android. see: https://www.youtube.com/watch?v=UnJ3amzJM94
