# Rhythm - Musical Structure App

![GitHub](https://img.shields.io/github/license/kaushiknsanji/RhythmApp)  ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/kaushiknsanji/RhythmApp)  ![GitHub repo size](https://img.shields.io/github/repo-size/kaushiknsanji/RhythmApp)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/kaushiknsanji/RhythmApp)  ![GitHub All Releases](https://img.shields.io/github/downloads/kaushiknsanji/RhythmApp/total) ![GitHub search hit counter](https://img.shields.io/github/search/kaushiknsanji/RhythmApp/Musical%20Structure%20App) ![Minimum API level](https://img.shields.io/badge/API-15+-yellow)

This App has been developed as part of the **Udacity Android Basics Nanodegree Course** for the Exercise Project **"Musical Structure App"**. App showcases a structure/approach typically used for Apps that play music, without implementing its functionality. Project mainly focuses on App designing. 

---

## App Compatibility

Android device running with Android OS 4.0.4 (API Level 15) or above. Best experienced on Android Nougat 7.1 and above. Designed for Phones and NOT for Tablets.

---

## Rubric followed for the Project

* App's Structure needs to be suitable for a Music Player App.
* Each Screen has a TextView that explains the purpose of it in a concise way.
* App must also contain a Payment Activity that requires user to make Payment for some situation, describing how the library/API will be used.
* App has atleast 3 - 6 activities which makes a cohesive music app.
* Each activity has buttons that enables a user to reach other activities.
* Uses Explicit intents to launch activities.
* Should make use of OnClickListeners instead of XML attributes.
* Should not mock up or add real content. Static data from resources are allowed. As mock up is not allowed, Adapter Views and `RecyclerView` should not be used. This enables more practice with using layouts.

---

## Design Workflow

### The Home Screen or the Main Activity of the App

|Home Drawer|Home Content(1)|Home Content(2)|
|---|---|---|
|![home_drawer](/art/screenshots/home_drawer.png)|![home_1](/art/screenshots/home_1.png)|![home_2](/art/screenshots/home_2.png)|

The [HomeActivity](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/home/HomeActivity.java) of the App is the Main Activity of the App that has a Navigation Drawer. The Main content will have some of the Drawer Items shown by the content fragment [HomeFragment](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/home/HomeFragment.java).

### Drawer Menu : Albums

|Album List(1)|Album List(2)|Album Detail(1)|Album Detail(2)|
|---|---|---|---|
|![album_1](/art/screenshots/album_1.png)|![album_2](/art/screenshots/album_2.png)|![album_detail_1](/art/screenshots/album_detail_1.png)|![album_detail_2](/art/screenshots/album_detail_2.png)|

* List of Albums available are shown by the content fragment [AlbumListFragment](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/album/AlbumListFragment.java). 
* Clicking on each Album will launch the respective Album Details shown by the [AlbumDetailActivity](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/album/AlbumDetailActivity.java). 
* `AlbumDetailActivity` shows relevant info on the Album and list of Songs available in the Album.
* Each Song has click interaction that starts playing the Song track clicked (simulation only).

### Drawer Menu : Artists

|Artist List(1)|Artist List(2)|Artist Detail(1)|Artist Detail(2)|
|---|---|---|---|
|![artist_1](/art/screenshots/artist_1.png)|![artist_2](/art/screenshots/artist_2.png)|![artist_detail_1](/art/screenshots/artist_detail_1.png)|![artist_detail_2](/art/screenshots/artist_detail_2.png)|

* List of Artists available are shown by the content fragment [ArtistListFragment](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/artist/ArtistListFragment.java).
* Clicking on each Artist will launch the respective Artist Details shown by the [ArtistDetailActivity](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/artist/ArtistDetailActivity.java). 
* `ArtistDetailActivity` shows the Albums of the Artist available and the Artist's Songs available in each of those Albums.
* Each Song has click interaction that starts playing the Song track clicked (simulation only).

### Drawer Menu : Songs

|Song List(1)|Song List(2)|
|---|---|
|![song_list_1](/art/screenshots/song_list_1.png)|![song_list_2](/art/screenshots/song_list_2.png)|

* List of Songs available are shown by the content fragment [SongListFragment](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/song/SongListFragment.java).
* Each Song has click interaction that starts playing the Song track clicked (simulation only).

### Drawer Menu : Playlists

|Playlist (1)|Playlist (2)|Playlist Detail(1)|Playlist Detail(2)|
|---|---|---|---|
|![playlist_1](/art/screenshots/playlist_1.png)|![playlist_2](/art/screenshots/playlist_2.png)|![playlist_detail_1](/art/screenshots/playlist_detail_1.png)|![playlist_detail_2](/art/screenshots/playlist_detail_2.png)|

* List of Playlists (User/Smart) available are shown by the content fragment [PlayQueueListFragment](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/queue/PlayQueueListFragment.java).
* Clicking on each Playlist will launch the respective Playlist Details shown by the [PlayQueueDetailActivity](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/queue/PlayQueueDetailActivity.java).
* `PlayQueueDetailActivity` shows the list of Songs enqueued in the Playlist.
* Each Song has click interaction that starts playing the Song track clicked (simulation only).

### Drawer Menu : Jukebox

|Jukebox List(1)|Jukebox List(2)|Jukebox Detail(1)|Jukebox Detail(2)|
|---|---|---|---|
|![jukebox_1](/art/screenshots/jukebox_1.png)|![jukebox_2](/art/screenshots/jukebox_2.png)|![jukebox_detail_1](/art/screenshots/jukebox_detail_1.png)|![jukebox_detail_2](/art/screenshots/jukebox_detail_2.png)|

**Bottom Sheet Dialog for Payment request**

<img src="/art/screenshots/jukebox_detail_payment.png" width="25%" /> 

* List of Jukebox channels available are shown by the activity [JukeboxListActivity](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/jukebox/JukeboxListActivity.java).
* Clicking on each Jukebox channel will launch the respective Jukebox Details shown by the [JukeboxDetailActivity](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/jukebox/JukeboxDetailActivity.java).
* If the user has not yet subscribed (made payment) to the channel, then a `BottomSheetDialog` will be shown (on click of any Song) to request and initiate the Payment. The Payment captured here is sent back to the `JukeboxDetailActivity`, and the activity starts playing the song clicked after payment confirmation. The `BottomSheetDialog` is shown by [JukeboxDetailPaymentDialogFragment](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/jukebox/JukeboxDetailPaymentDialogFragment.java). Payment is just a simulation here to show the use of Payment requests and how it can be implemented in terms of design.

### Bottom Sheet Player

|Player Portait(1)|Player Portait(2)|Player Landscape|
|---|---|---|
|![bottom_sheet_player_1](/art/screenshots/bottom_sheet_player_1.png)|![bottom_sheet_player_2](/art/screenshots/bottom_sheet_player_2.png)|![bottom_sheet_player_land](/art/screenshots/bottom_sheet_player_land.png)|

* Bottom Sheet Player is a persistent Bottom Sheet that is shown in all Fragments and Activities that require it to be shown.
* Fragments that extend [PlayerFragment](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/common/fragments/PlayerFragment.java) and Activities that extend [PlayerActivity](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/common/activities/PlayerActivity.java) needs to include the Bottom Sheet Player [layout_all_player](/app/src/main/res/layout/layout_all_player.xml) to show the persistent collapsed Bottom Sheet Player.
* When clicked on the collapsed Bottom Sheet Player, it expands to fill the entire screen, to show the "Now Playing" information along with all the Music Player controls.
* Music Player Controls simulate the functionality by binding to the [PlayerService](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/common/services/PlayerService.java). The `PlayerService` provides methods for Play/Pause/Restart that control the player progress value generated by an internal worker thread. 
* Player information stays persisted across all Fragments and Activities by saving the info in [PlayerComposition](/app/src/main/java/com/example/kaushiknsanji/rhythm/data/local/PlayerComposition.java) which is tied to the Application Lifecycle.

### Scrolling Behavior for AppBarLayouts

* Nested Scrolls initiated by Bottom Sheet are by default, accepted by layouts behind the Bottom Sheet, causing some weird UI motion. 
* In order to prevent this, all layouts that have Bottom Sheet, has this behavior [BottomSheetAwareAppBarBehavior](/app/src/main/java/com/example/kaushiknsanji/rhythm/extensions/BottomSheetAwareAppBarBehavior.java) attached to their `AppBarLayout`. 
* This custom behavior denies the Nested scrolls generated by Bottom Sheets, so that the scrolling only happens on the Bottom Sheet shown and not the layout behind it.

### Anchored FAB Behaviors

* `FloatingActionButton` anchored to `AppBarLayout` and CoordinatorLayouts with Bottom Sheet, manage the scale and the visibility of the FAB depending on the changes in Anchored View.
* For any other Anchored Views like `CollapsingToolbarLayout`, we need to take care of the same. This is provided by the behavior [ScrollAwareAnchoredFabBehavior](/app/src/main/java/com/example/kaushiknsanji/rhythm/extensions/ScrollAwareAnchoredFabBehavior.java) attached to such FABs.
* This behavior looks for the change in the visible height of the dependent/anchored view and applies the same to scale the FAB and change its visibility accordingly.

### About Activity

<img src="/art/screenshots/about_1.png" width="40%" />   <img src="/art/screenshots/about_2.png" width="40%" />

* Launches via the **"About"** Menu and Drawer Menu available in the `HomeActivity`.
* This is shown by the [AboutActivity](/app/src/main/java/com/example/kaushiknsanji/rhythm/ui/about/AboutActivity.java)
* This page describes in brief about the app, and has links to my bio and the course details hosted by Udacity. 

---

## Branches in this Repository

* **[udacity](https://github.com/kaushiknsanji/RhythmApp/tree/udacity)**
	* Contains the code submitted for review, along with review suggestions incorporated.

---

## Icon/Image credits

All colorful icons used in the App are from <a href="https://icons8.com">Icons8</a>.

---

## Review from the Reviewer (Udacity)

![Review_Musical_Structure](/art/review/review_musical_structure.png)

---

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