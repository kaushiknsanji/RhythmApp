# Rhythm App - v1.0

![GitHub](https://img.shields.io/github/license/kaushiknsanji/RhythmApp) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/kaushiknsanji/RhythmApp) ![GitHub repo size](https://img.shields.io/github/repo-size/kaushiknsanji/RhythmApp) ![GitHub Releases](https://img.shields.io/github/downloads/kaushiknsanji/RhythmApp/v1.0/total)

This is the Release version 1.0 of the **Rhythm** App.

## Changes done in this Release

* Configured an Activity Alias to launch the [HomeActivity](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/home/HomeActivity.java).
* Added a Keep entry in [Proguard](/app/proguard-rules.pro) to the keep the names of the UI Classes and their Fragment TAGs AS-IS. Without this, fragments will NOT get launched, since the TAGs are dependent on their Class names.

## License

```
Copyright 2019 Kaushik N. Sanji

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
   
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
