[![Discord](https://img.shields.io/discord/1115727214827278446)](https://discord.gg/X8QB9DJXX6)
![Apache-2.0](https://img.shields.io/badge/license-Apache-blue)
<!--
// TODO - enable when the repo gets released and is public
![GitHub contributors](https://img.shields.io/github/contributors/openmobilehub/omh-storage)
-->

[![Publish Storage API](https://github.com/openmobilehub/omh-storage/actions/workflows/publish_storage_api.yml/badge.svg)](https://github.com/openmobilehub/omh-storage/actions/workflows/publish_storage_api.yml)
[![Publish Storage API Google Drive Implementation](https://github.com/openmobilehub/omh-storage/actions/workflows/publish_storage_api_gms.yml/badge.svg)](https://github.com/openmobilehub/omh-storage/actions/workflows/publish_storage_api_gms.yml)
[![Publish Storage API NonGMS Implementation](https://github.com/openmobilehub/omh-storage/actions/workflows/publish_storage_api_ngms.yml/badge.svg)](https://github.com/openmobilehub/omh-storage/actions/workflows/publish_storage_api_ngms.yml)


# OMH Storage Client Library

## Overview
OMH Storage is an Android client library that makes it easy to integrate storage on both Google Mobile Services (GMS) and non-GMS devices. It eliminates the need for separate codebases for different Android builds.

With the OMH Storage Client Library, you can easily add Google Drive and other third-party storage to your applications, regardless of whether the device has GMS or not. The library takes care of the technical details, providing a unified interface and components for a consistent storage experience.

## A single codebase, running seamlessly on any device
For instance, the following screenshots showcase multiple devices with Android, both with GMS and Non-GMS. The same app works without changing a single line of code, supporting multiple storage provider implementations.
<div align="center">

| Non-GMS</br>Huawei Nova 9 SE | GMS</br>Moto g(6) Plus | Non-GMS Device</br>Moto g(6) Plus |
|------------------------------|------------------------|-----------------------------------|
| <img src="images/omh-storage-huawei-9se.gif"> | <img src="images/omh-storage-gms-motog6-plus.gif"> | <img src="images/omh-storage-non-gms-motog6-plus.gif"> |
 
</div>

# Getting started
This section describes how to setup an Android Studio project to use the OMH Storage SDK for Android. For greater ease, a base code will be used within the repository.

**Note: To quickly run a full-featured app with all OMH Storage functionality, refer to the [`Sample App`](#sample-app) section and follow the provided steps.**

## Set up the development environment
1. Android Studio is required. If you haven't already done so, [download](https://developer.android.com/studio/index.html) and [install](https://developer.android.com/studio/install.html?pkg=studio) it.
2. Ensure that you are using the [Android Gradle plugin](https://developer.android.com/studio/releases/gradle-plugin) version 7.0 or later in Android Studio.

## Clone the repository
To clone the repository and checkout the `code-starter` branch, use the following command in your Terminal:

   ```
   git clone --branch code-starter https://github.com/openmobilehub/omh-storage.git omh-storage-starter-code
   ```

## Set up your Google Cloud project for applications with Google Services (Google Auth)
To access Google APIs, generate a unique client_id for your app in the Google API Console, for additional information [see](https://github.com/openmobilehub/omh-auth). Add the client_id to your app's code and complete the required Cloud Console setup steps:

### Steps
1. [Go to the Google Cloud Console and open the project selector page.](https://console.cloud.google.com/projectselector2/home/dashboard?utm_source=Docs_ProjectSelector&_gl=1*1ylhfe0*_ga*MTUwMDIzODY1Ni4xNjc1OTYyMDgw*_ga_NRWSTWS78N*MTY4MjA4ODIyNS44NS4xLjE2ODIwODgyMzcuMC4wLjA.)
2. Click on "Create Project" to start creating a new Cloud project.
3. [Go to the Credentials page](https://console.cloud.google.com/apis/credentials).
4. On the Credentials page, click on "Create credentials" and choose "OAuth Client ID".
5. In the "Application Type" option, select "Android".
6. Set your application package name.
   
   **Note:** If you are running the sample app or following the steps in the `code-starter` branch, use `com.omh.android.storage.sample`.

7. Update the Android Client ID's debug/release SHA-1 certificate fingerprint by running the following command in your terminal:
 
   ```bash
    keytool -list -v -alias androiddebugkey -keystore ~/.android/debug.keystore
   ```
 
   **Note:** Use `android` as the default password. For instructions on other operating systems, refer to [these guidelines](https://developers.google.com/android/guides/client-auth#using_keytool_on_the_certificate).

8. In the [OAuth consent screen](https://console.cloud.google.com/apis/credentials/consent) add the test users that you will be using for QA and development. Without this step you won't be able to access the application while it's in testing mode.
9. Go to the [API library](https://console.cloud.google.com/apis/library) section on the project you created
10. Once there, make sure enable [Google Drive Api](https://console.cloud.google.com/apis/library/drive.googleapis.com)
11. You're all set!

## Add the Client ID to your app
You should not check your Client ID into your version control system, so it is recommended storing it in the `local.properties` file, which is located in the root directory of your project.
For more information about the `local.properties` file, see [Gradle properties](https://developer.android.com/studio/build#properties-files) and [files](https://developer.android.com/studio/build#properties-files).

1. Open the `local.properties` in your project level directory, and then add the following line:

   ```
   CLIENT_ID = "YOUR_CLIENT_ID"
   ```

   *Note:* Replace `YOUR_CLIENT_ID` with your API key client id.

2. Save the file and [sync your project with Gradle](https://developer.android.com/studio/build#sync-files).

## Gradle configuration
To incorporate OMH Storage into your project, you have two options: utilize the OMH Core Plugin or directly include the OMH Client libraries dependencies. This plugin simplifies the addition of Gradle dependencies, allowing you to effortlessly manage and include the necessary dependencies for seamless integration.

### Add OMH Core plugin
The subsequent instructions will outline the necessary steps for including the OMH Core Plugin as a Gradle dependency.

1. In your `storage-starter-sample` module-level `build.gradle` under the `plugins` element add the plugin id. If you're configuring this step by step on the `code-starter` branch, look for the comment `// Paste below this line the snippet of core plugin` and paste there the plugin id as shown on the next snippet:

   ```
   plugins {
      ...
      id("com.openmobilehub.android.omh-core")
   }
   ```

2. Save the file and [sync Project with Gradle Files](https://developer.android.com/studio/build#sync-files).

### Configure the OMH Core plugin

In your `storage-starter-sample` module-level `build.gradle.kts` search the comment `// replace this block with README omhConfig snippet` and replace the block with this snippet:

   ```
   omhConfig {
       bundle("singleBuild") {
           storage() {
               gmsService {
                   dependency = "com.openmobilehub.android:storage-api-drive-gms:1.0.7-beta"
               }
               nonGmsService {
                   dependency = "com.openmobilehub.android:storage-api-drive-nongms:1.0.8-beta"
               }
           }
           auth {
               gmsService {
                   dependency = "com.openmobilehub.android:auth-api-gms:1.0.1-beta"
               }
               nonGmsService {
                   dependency = "com.openmobilehub.android:auth-api-non-gms:1.0.1-beta"
               }
           }
       }
       bundle("gms") {
           storage {
               gmsService {
                   dependency = "com.openmobilehub.android:storage-api-drive-gms:1.0.7-beta"
               }
           }
           auth {
               gmsService {
                   dependency = "com.openmobilehub.android:auth-api-gms:1.0.1-beta"
               }
           }
       }
       bundle("nongms") {
           storage {
               nonGmsService {
                   dependency = "com.openmobilehub.android:storage-api-drive-nongms:1.0.8-beta"
               }
           }
           auth {
               nonGmsService {
                   dependency = "com.openmobilehub.android:auth-api-non-gms:1.0.1-beta"
               }
           }
       }
   }
   ```

_**NOTE: This section covers concepts about the core plugin**_

In your "storage-starter-sample" module-level `build.gradle` file is required to configure the `omhConfig`. The `omhConfig` definition is used to extend the existing Android Studio variants in the core plugin. For more details about `omhConfig` see [OMH Core](https://github.com/openmobilehub/omh-core/tree/release/1.0).

#### Basic configuration
In this step, you will define the OMH Core Plugin bundles to generate multiple build variants with specific suffixes as their names. For example, if your project has `release` and `debug` variants with `singleBuild`, `gms`, and `nonGms` OMH bundles, the following build variants will be generated:

- `releaseSingleBuild`, `releaseGms`, and `releaseNonGms`
- `debugSingleBuild`, `debugGms`, and `debugNonGms`

##### Variant singleBuild
    - Define the `Service`. In this example is storage.
    - Define the `ServiceDetails`. In this example are `gmsService` and `nonGmsService`.
    - Define the dependency and the path. In this example
      are `com.openmobilehub.android:storage-api-drive-gms:1.0-rc"`
      and `com.openmobilehub.android:storage-api-drive-nongms:1.0-rc`.

**Note:** It's important to observe how a single build encompasses both GMS (Google MobileServices) and Non-GMS configurations.

##### Variant gms
    - Define the `Service`. In this example is storage.
    - Define the `ServiceDetails` . In this example is `gmsService`.
    - Define the dependency and the path. In this example
      is `com.openmobilehub.android:storage-api-drive-gms:1.0-rc"`.

**Note:** gms build covers only GMS (Google Mobile Services).

##### Variant nongms
    - Define the `Service`. In this example is storage.
    - Define the `ServiceDetails` . In this example is `nonGmsService`.
    - Define the dependency and the path. In this example
      is `com.openmobilehub.android:storage-api-drive-non-gms:1.0-rc`.

**Note:** nongms build covers only Non-GMS configurations.

1. Save and [sync Project with Gradle Files](https://developer.android.com/studio/build#sync-files).

2. Rebuild the project to ensure the availability of `BuildConfig.AUTH_GMS_PATH`, `BuildConfig.AUTH_NON_GMS_PATH`, 
   `BuildConfig.STORAGE_GMS_PATH` and `BuildConfig.STORAGE_NON_GMS_PATH` variables.

3. Now you can select a build variant. To change the build variant Android Studio uses, do one of the following:
   - Select "Build" > "Select Build Variant..." in the menu.
   - Select "View" > "Tool Windows" > "Build Variants" in the menu.
   - Click the "Build Variants" tab on the tool window bar.

4. You can select any of the 3 variants for the `:storage-starter-sample`:
   - "singleBuild" variant builds for GMS (Google Mobile Services) and Non-GMS devices without
     changes to the code.(Recommended)
   - "gms" variant builds for devices that has GMS (Google Mobile Services).
   - "nongms" variant builds for devices that doesn't have GMS (Google Mobile Services).

5. Go to the `providesOmhAuthClient` function in the `SingletonModule` file. Look for the comment `// Add here snippet for provide auth client`, and replace the existing code below this comment with the following snippet:

   ```kotlin
   return OmhAuthProvider.Builder()
         .addNonGmsPath(BuildConfig.AUTH_NON_GMS_PATH)
         .addGmsPath(BuildConfig.AUTH_GMS_PATH)
         .build()
         .provideAuthClient(
             context = context,
             scopes = listOf(
                 "openid",
                 "email",
                 "profile",
                 "https://www.googleapis.com/auth/drive",
                 "https://www.googleapis.com/auth/drive.file"
             ),
             clientId = BuildConfig.CLIENT_ID
         )
   ```

   **Note: Make sure to use the full implementation of [`providesOmhAuthClient`](https://github.com/openmobilehub/omh-storage/blob/release/1.0/storage-sample/src/main/java/com/omh/android/storage/sample/di/SingletonModule.kt#L36-L52) for a fully functional sample.**

6. Go to the `providesOmhStorageClient` function in the `SingletonMOdule` file. Look for the comment `// Add here snippet for provide storage client`, and replace the existing code below this comment with the following snippet:

   ```kotlin
   return OmhStorageProvider.Builder()
            .addGmsPath(BuildConfig.STORAGE_GMS_PATH)
            .addNonGmsPath(BuildConfig.STORAGE_NON_GMS_PATH)
            .build()
            .provideStorageClient(omhAuthClient, context)
   ```

   **Note: Make sure to use the full implementation of [`providesOmhStorageClient`](https://github.com/openmobilehub/omh-storage/blob/release/1.0/storage-sample/src/main/java/com/omh/android/storage/sample/di/SingletonModule.kt#L55-L64) for a fully functional sample.**

## Adding Storage to your app
First and foremost, the main interface that you'll be interacting with is called OmhStorageClient. In contains all your basic storage functionalities: list, create, delete, download, update and upload files.

You can checkout the branch `code-starter` and copy/paste the following snippets in the right place or if you want to see the full implementation you can just see the `main` branch

### List files
For list files, just use the instance you created of the `omhStorageClient` and call method `listFiles` sending as parameter the desired parent id.

If you are configuring this step by step on the `code-starter` branch:

1. Go to the `refreshFileListEvent` function in the `FileViewerViewModel` file. Look for the comment `// Add here snippet for list files`, and replace the existing code below this comment with the following snippet:
   
   ```kotlin
   val cancellable = omhStorageClient.listFiles(parentId)
               .addOnSuccess { result: GetFilesListUseCaseResult ->
                   // Get the files list
                   val filesList: List<OmhFile> = result.files
                   // TODO - Developer: Manage success
               }
               .addOnFailure { exception: Exception ->
                   // TODO - Developer: Manage error
               }
               .execute()
   cancellableCollector.addCancellable(cancellable)
   ```

   **Note: Make sure to use the full implementation of [`refreshFileListEvent`](https://github.com/openmobilehub/omh-storage/blob/release/1.0/storage-sample/src/main/java/com/omh/android/storage/sample/presentation/file_viewer/FileViewerViewModel.kt#L88-L113) for a fully functional sample.**

2. Run the sample app to see the list of files.

### Create files
For create files, just use the instance you created of the `omhStorageClient` and call method `createFile` sending as parameter the desired name, mime type and parent id.

If you are configuring this step by step on the `code-starter` branch:

1. Go to the `createFileEvent` function in the `FileViewerViewModel` file. Look for the comment `// Add here snippet for create files`, and replace the existing code below this comment with the following snippet:

   ```kotlin
    val cancellable = omhStorageClient.createFile(name, mimeType, parentId)
                .addOnSuccess { result: CreateFileUseCaseResult ->
                   // An instance of OmhFile with the information of the created file. In case the file was not created, will be null
                   val file: OmhFile? = result.file
                    // TODO - Developer: Manage success
                }
                .addOnFailure { exception: Exception ->
                    // TODO - Developer: Manage error
                }
                .execute()
    cancellableCollector.addCancellable(cancellable)
   ```

   **Note: Make sure to use the full implementation of [`createFileEvent`](https://github.com/openmobilehub/omh-storage/blob/release/1.0/storage-sample/src/main/java/com/omh/android/storage/sample/presentation/file_viewer/FileViewerViewModel.kt#L221-L238) for a fully functional sample.**

2. Run the sample app to create files.

### Delete files
For delete files, just use the instance you created of the `omhStorageClient` and call method `deleteFile` sending as parameter the id of the file you want to delete.

If you are configuring this step by step on the `code-starter` branch:

1. Go to the `deleteFileEvent` function in the `FileViewerViewModel` file. Look for the comment `// Add here snippet for delete files`, and replace the existing code below this comment with the following snippet:

   ```kotlin
    val cancellable = omhStorageClient.deleteFile(fileId)
                .addOnSuccess { result: DeleteFileUseCaseResult ->
                   // The success variable indicates if the file was deleted or not
                   val success: Boolean = result.isSuccess
                    // TODO - Developer: Manage success
                }
                .addOnFailure { exception: Exception ->
                    // TODO - Developer: Manage error
                }
                .execute()
    cancellableCollector.addCancellable(cancellable)
   ```

   **Note: Make sure to use the full implementation of [`deleteFileEvent`](https://github.com/openmobilehub/omh-storage/blob/release/1.0/storage-sample/src/main/java/com/omh/android/storage/sample/presentation/file_viewer/FileViewerViewModel.kt#L282-L302) for a fully functional sample.**

2. Run the sample app to delete files.

### Upload files
For upload files, just use the instance you created of the `omhStorageClient` and call method `uploadFile` sending as parameter the local path of the file you want to upload and the id of the remote folder where you want to place it (parent id).

If you are configuring this step by step on the `code-starter` branch:

1. Go to the `uploadFile` function in the `FileViewerViewModel` file. Look for the comment `// Add here snippet for upload files`, and replace the existing code below this comment with the following snippet:

   ```kotlin
    val cancellable = omhStorageClient.uploadFile(filePath, parentId)
                .addOnSuccess { result: UploadFileUseCaseResult ->
                   // An instance of OmhFile with the information of the uploaded file. In case the file was not uploaded, will be null
                   val file: OmhFile? = result.file
                    // TODO - Developer: Manage success
                }
                .addOnFailure { exception: Exception ->
                    // TODO - Developer: Manage error
                }
                .execute()
    cancellableCollector.addCancellable(cancellable)
   ```

   **Note: Make sure to use the full implementation of [`uploadFile`](https://github.com/openmobilehub/omh-storage/blob/release/1.0/storage-sample/src/main/java/com/omh/android/storage/sample/presentation/file_viewer/FileViewerViewModel.kt#L240-L268) for a fully functional sample.**

2. Run the sample app to upload files.

### Update files
For update files, just use the instance you created of the `omhStorageClient` and call method `updateFile` sending as parameter the local path of the file you want to update and the id of the remote file you want to replace (file id).

If you are configuring this step by step on the `code-starter` branch:

1. Go to the `updateFileEvent` function in the `FileViewerViewModel` file. Look for the comment `// Add here snippet for update files`, and replace the existing code below this comment with the following snippet:

   ```kotlin
    val cancellable = omhStorageClient.updateFile(filePath, fileId)
                .addOnSuccess { result: UpdateFileUseCaseResult ->
                   // An instance of OmhFile with the information of the updated file. In case the file was not updated, will be null
                   val file: OmhFile? = result.file
                    // TODO - Developer: Manage success
                }
                .addOnFailure { exception: Exception ->
                    // TODO - Developer: Manage error
                }
                .execute()
    cancellableCollector.addCancellable(cancellable)
   ```

   **Note: Make sure to use the full implementation of [`updateFileEvent`](https://github.com/openmobilehub/omh-storage/blob/release/1.0/storage-sample/src/main/java/com/omh/android/storage/sample/presentation/file_viewer/FileViewerViewModel.kt#L187-L219) for a fully functional sample.**

2. Run the sample app to update files.

### Download files
For download files, just use the instance you created of the `omhStorageClient` and call method `createFile` sending as parameter the id of the file you want to download and the mime type you desire to have locally (once downloaded)

If you are configuring this step by step on the `code-starter` branch:

1. Go to the `downloadFileEvent` function in the `FileViewerViewModel` file. Look for the comment `// Add here snippet for download files`, and replace the existing code below this comment with the following snippet:

   ```kotlin
    val cancellable = omhStorageClient.downloadFile(id, mimeTypeToSave)
                .addOnSuccess { result: DownloadFileUseCaseResult ->
                   // An instance of ByteArrayOutputStream with the downloaded file
                   val outputStream: ByteArrayOutputStream = result.outputStream
                    // TODO - Developer: Manage success
                }
                .addOnFailure { exception: Exception ->
                   // TODO - Developer: Manage error
                }
                .execute()
    cancellableCollector.addCancellable(cancellable)
   ```

   **Note: Make sure to use the full implementation of [`downloadFileEvent`](https://github.com/openmobilehub/omh-storage/blob/release/1.0/storage-sample/src/main/java/com/omh/android/storage/sample/presentation/file_viewer/FileViewerViewModel.kt#L143-L185) for a fully functional sample.**

2. Run the sample app to download files.

# Sample App: All-Feature Demonstration
This repository also contains a [Storage-sample](/storage-sample), which demonstrates all the functionalities of the OMH Storage Client Library. To get started, follow these steps:

1. Clone the repository to your local machine.
2. Set up your Google Cloud project. You can refer to the [instructions](#set-up-your-google-cloud-project-for-applications-with-google-services-google-auth) for guidance.
3. Add the client id to your app using the instructions provided [here](https://github.com/openmobilehub/omh-storage#set-up-your-google-cloud-project-for-applications-with-google-services-google-auth).
4. Set the variant to `debugSingleBuild`
5. Run the `storage-sample` on your device

However, if you prefer a more structured approach to learn the client library from scratch, it's recommended to begin with the [Getting Started section](#getting-started).

# Documentation
See example and check the full documentation and add custom implementation at our [Wiki](https://github.com/openmobilehub/omh-storage/wiki).

Additionally for more information about the OMH Storage functions, [Docs](https://openmobilehub.github.io/omh-storage).

# Provider Implementations / Plugins
OMH Storage SDK is open-source, promoting community collaboration and plugin support from other storage providers to enhance capabilities and expand supported storage services. More details can be found at [the wiki](https://github.com/openmobilehub/omh-storage/wiki/Creating-a-custom-implementation).

# Contributing
Please contribute! We will gladly review any pull requests. Make sure to read the [CONTRIBUTING](https://github.com/openmobilehub/omh-storage/blob/main/CONTRIBUTING.md) page first though.

# Governance

For details on our project's governance model and how decisions are made, please see our [Governance Policy](https://github.com/openmobilehub/admin/blob/main/GOVERNANCE.md).

# License
```
Copyright 2023 Open Mobile Hub
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
