# FlickrCoolApp
Flickr Cool App - Search - Share and enjoy

      Functional requirements
      1.	the app must use the Flickr API (https://www.flickr.com/services/api/) to allow user searching for photos with specific words
      2.	the app must show the results of the search in an infinite scroll list where each cell contains at least a photo
      3.	when tapping on a cell the user of the app must see the full screen photo and its details

The App has been deigned with Android Studio 2.1.2

        dependencies {
            compile fileTree(dir: 'libs', include: ['*.jar'])
            testCompile 'junit:junit:4.12'
            compile 'com.android.support:appcompat-v7:23.0.1'
            compile 'com.android.support:design:23.0.1'
            compile 'com.android.support:support-v4:23.+'
            compile 'com.android.support:recyclerview-v7:23.0.+'
            compile 'com.squareup.retrofit2:retrofit:2.0.0-beta4'
            compile 'com.squareup.retrofit2:converter-gson:2.0.0-beta4'
            compile 'com.android.support:cardview-v7:23.+'
            compile 'com.squareup.picasso:picasso:2.5.2'
            compile 'com.github.bumptech.glide:glide:3.5.2'
            compile 'com.mcxiaoke.volley:library:1.0.+'
            compile 'com.google.code.gson:gson:1.7.2'
            //**** For Butterknife
            apt 'com.jakewharton:butterknife-compiler:8.1.0'
            compile 'com.jakewharton:butterknife:8.1.0'
        }
