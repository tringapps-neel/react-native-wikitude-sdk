# React Native Wikitude Bridge

# Introduction 
This is a React Native bridge module for [Wikitude](https://www.wikitude.com/) which provides a simple integration to the Wikitude AR SDK.
At the moment, the module only supports loading AR experiences from Wikitude Studio URLs. These can be local or online locations.
You would normally export the files from Wikitude Studio and then host them in a directory somewhere yourself. Point your app to that URL and it should load your project.


# PERMISSIONS
You need to ensure that the user already give permission before display the wikitude View

## How to install

First install the module via npm and link it up:

```bash
npm install react-native-wikitude-sdk

react-native link react-native-wikitude-sdk
```
After that completes, you will need to do additional steps for each platform you are supporting:

#### Android

1. Unfortunately the gradle system does not seem to allow [sub-linking aar files](https://issuetracker.google.com/issues/36971586). To get around this you will have to install the `wikitudesdk` folder manually into each project you plan to use this module with. 

	Copy the `wikitudesdk` folder from the `node-modules/react-native-wikitude/android` folder into your project's `android` folder: 

	On Mac / Linux: 
	
	```bash
	cd YourReactNativeProject
	cp -R ./node_modules/react-native-wikitude/android/wikitudesdk ./android/wikitudesdk
	```
	
	or on Windows:
	
	```dos
	cd YourReactNativeProject
	xcopy node_modules\react-native-wikitude\android\wikitudesdk android\wikitudesdk /E
	```

2. And then in your `android/settings.gradle` file, modify the existing `include ':react-native-wikitude'` line to also include the `wikitudesdk`:
	```gradle
	include ':wikitudesdk', ':react-native-wikitude'
	```
	
3. In your `android/build.gradle` file, modify the minimum SDK version to at least version 19:
	```gradle
	android {
		defaultConfig {
			...
			minSdkVersion 19
			...
		}
	```
4. In your `android/app/src/main/AndroidManifest.xml` file, If you have it, remove the `android:allowBackup="false"` attribute from the `application` node. If you want to set allowBackup, follow the method [here](https://github.com/OfficeDev/msa-auth-for-android/issues/21).
	
5. Optionally: In your `android/build.gradle` file, define the versions of the standard libraries you'd like WikitudeBridge to use:
	```gradle
	...
	ext {
		// dependency versions
		compileSdkVersion = "<Your compile SDK version>" // default: 27
		buildToolsVersion = "<Your build tools version>" // default: "27.0.3"
		targetSdkVersion = "<Your target SDK version>" // default: 27
		constraintLayoutVersion = "<Your com.android.support.constraint:constraint-layout version>" //default "1.0.2"
	}
	...
	```

### iOS
At the moment, iOS integration is not documented. Please contribute to update me! 


## Usage

The module exposes just one function: 
```typescript
function startAR(architectWorldURL: string, hasGeolocation:boolean, hasImageRecognition:boolean, hasInstantTracking:boolean, wikitudeSDKKey:string)
```




## New way to insert Wikitude into the React App
You can only have one feature enable, the default is all.
To change the feature you will need to destroy and recreate the component.

if you have a memory leak or a crash because you switch screens, you need to tell Wikitude to 
Stop rendering , to do this you will need to ref the component and call 
```typescript
stopRendering() and resumeRendering()
```



```ecmascript 6
<WikitudeView
        ref="wikitudeView"
        style={{ flex: 1 }}
        url={arCloud}
        licenseKey={licenseKey}
        feature={WikitudeView.Geo}
        onJsonReceived={this.onJsonReceived}
        onFinishLoading={this.onFinishLoading}
      />
```
# Methods

- setWorldUrl(url)
	Set the URL to load into the Wikitude View

- callJavascript(str)
	Send a String as a JS, to inject into the ARView and call it.
	
- injectLocation(lat,ln)
	Send location into the view to inject the location of the device

- stopRendering
	Stops all rendering of the Wikitude View, this will stops the camera also

- resumeRendering
	Resume the current experience, you need to stop first in order to resume.

# Events
- onJsonReceived(event)
	handles the JSON received event from the Experience

- onFinishLoading(event)
	handles the event when the Experience is finish loading

- onFailLoading(event)
	handles the event when the experinces has an error loading

## ChangeLog
- 1.0.0
	First Commit