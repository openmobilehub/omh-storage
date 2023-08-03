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
| <img src="https://github.com/openmobilehub/omh-storage/assets/124717244/4de1a1c7-1f51-492c-bfc7-53e475d06b32"> | <img src="https://github.com/openmobilehub/omh-storage/assets/124717244/6aa26b0b-87a9-42dc-a3b8-27a3a3ff04b5"> | <img src="https://github.com/openmobilehub/omh-storage/assets/124717244/66d371c3-728b-4d0f-baf6-b4ebd77a058d"> |
 
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

## Set up your Google Cloud project for applications with Google Services(Google Auth)
To access Google APIs, generate a unique client_id for your app in the Google API Console, for additional information [see](https://github.com/openmobilehub/omh-auth). Add the client_id to your app's code and complete the required Cloud Console setup steps:

### Steps
1. [Go to the Google Cloud Console and open the project selector page.](https://console.cloud.google.com/projectselector2/home/dashboard?utm_source=Docs_ProjectSelector&_gl=1*1ylhfe0*_ga*MTUwMDIzODY1Ni4xNjc1OTYyMDgw*_ga_NRWSTWS78N*MTY4MjA4ODIyNS44NS4xLjE2ODIwODgyMzcuMC4wLjA.)
2. Click on "Create Project" to start creating a new Cloud project.
3. [Go to the Credentials page](https://console.cloud.google.com/apis/credentials).
4. On the Credentials page, click on "Create credentials" and choose "OAuth Client ID".
5. In the "Application Type" option, select "Android".
6. Set your application package name (Use "com.omh.android.storage.sample" if you are following the
   starter-code)
7. Update the debug/release SHA-1 certificate fingerprint for Android's Client ID.

   Note: The debug build is automatically signed with the debug keystore. Obtain the certificate fingerprint from it by following the guidelines in the official Google Developers documentation: ["Using keytool on the certificate"](https://developers.google.com/android/guides/client-auth#using_keytool_on_the_certificate).
8. In the [OAuth consent screen](https://console.cloud.google.com/apis/credentials/consent) add the
   test users that you will be using for QA and development. Without this step you won't be able to
   access the application while it's in testing mode.
9. You're all set!

## Add the Client ID to your app
You should not check your Client ID into your version control system, so it is recommended
storing it in the `local.properties` file, which is located in the root directory of your project.
For more information about the `local.properties` file,
see [Gradle properties](https://developer.android.com/studio/build#properties-files)
[files](https://developer.android.com/studio/build#properties-files).

1. Open the `local.properties` in your project level directory, and then add the following code.
   Replace `YOUR_CLIENT_ID` with your API key.
   `CLIENT_ID=YOUR_CLIENT_ID`
2. Save the file and [sync your project with Gradle](https://developer.android.com/studio/build#sync-files).

## Gradle configuration
To incorporate OMH Storage into your project, you have two options: utilize the OMH Core Plugin or directly include the OMH Client libraries dependencies. This plugin simplifies the addition of
Gradle dependencies, allowing you to effortlessly manage and include the necessary dependencies for seamless integration.

### Add OMH Core plugin
The subsequent instructions will outline the necessary steps for including the OMH Core Plugin as a
Gradle dependency.

1. In your "storage-starter-sample" module-level `build.gradle` under the `plugins` element add the plugin id.

   ```
   plugins {
      ...
      id("com.openmobilehub.android.omh-core")
   }
   ```

2. Save the file and [sync Project with Gradle Files](https://developer.android.com/studio/build#sync-files).

### Configure the OMH Core plugin

In your `storage-starter-sample` module-level `build.gradle` file add the following code at the end of the file.

   ```
   omhConfig {
      bundle("singleBuild") {
         storage {
            gmsService {
                dependency = "com.openmobilehub.android:storage-api-drive-gms:1.0.1-rc"
            }
            nonGmsService {
                dependency = "com.openmobilehub.android:storage-api-drive-nongms:1.0.1-rc"
            }
         }
         auth {
            gmsService {
                dependency = "com.openmobilehub.android:auth-api-gms:1.0"
            }
            nonGmsService {
                dependency = "com.openmobilehub.android:auth-api-non-gms:1.0"
            }
        }
      }
      bundle("gms") {
         storage {
            gmsService {
                dependency = "com.openmobilehub.android:storage-api-drive-gms:1.0.1-rc"
            }
         }
         auth {
            gmsService {
                dependency = "com.openmobilehub.android:auth-api-gms:1.0"
            }
         }
      }
      bundle("nongms") {
         storage {
            nonGmsService {
                dependency = "com.openmobilehub.android:storage-api-drive-nongms:1.0.1-rc"
            }
         }
         auth {
            nonGmsService {
                dependency = "com.openmobilehub.android:auth-api-non-gms:1.0"
            }
         }
      }
   }
   ```

_**NOTE: This section covers concepts about the core plugin**_

In your "storage-starter-sample" module-level `build.gradle` file is required to configure
the `omhConfig`. The `omhConfig` definition is used to extend the existing Android Studio
variants in the core plugin. For more details `omhConfig`
see [OMH Core](https://github.com/openmobilehub/omh-core/tree/release/1.0).

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

**Note:** It's important to observe how a single build encompasses both GMS (Google MobileServices)
and Non-GMS configurations.

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

5. To obtain an instance of the OMH Storage client, you need an instance of OmhAuthClient.
 
   ```kotlin
   val omhAuthProvider: OmhAuthProvider = OmhAuthProvider.Builder()
       .addNonGmsPath(BuildConfig.AUTH_NON_GMS_PATH)
       .addGmsPath(BuildConfig.AUTH_GMS_PATH)
       .build()
   
   val omhAuthClient: OmhAuthClient = omhAuthProvider.provideAuthClient(
       scopes = listOf("openid", "email", "profile"),
       clientId = BuildConfig.CLIENT_ID,
       context = context
   )
   ```
   
6. To instantiate the OmhStorageClient, add the following code.
   
   ```kotlin
   val omhStorageProvider: OmhStorageProvider = OmhStorageProvider.Builder()
       .addNonGmsPath(BuildConfig.STORAGE_NON_GMS_PATH)
       .addGmsPath(BuildConfig.STORAGE_GMS_PATH)
       .build()
   
   val omhStorageClient: OmhStorageClient = omhStorageProvider.provideStorageClient(
       authClient = omhAuthClient,
       context = context
   )
   ```

*Note*: we'd recommend to provide the auth client and the storage client as a singleton with your preferred dependency injection library as this will be your only gateway to the OMH Auth SDK and OMH Storage SDK; and it doesn't change in runtime at all. If you're checking the `starter-code` or the `main` branches you will find this on `SingletonModule` class.

## Adding Storage to your app
First and foremost, the main interface that you'll be interacting with is called OmhStorageClient. In contains all your basic storage functionalities: list, create, delete, download, update and upload files.

You can checkout the branch `code-starter` and copy/paste the following snippets in the right place or if you want to see the full implementation you can just see the `main` branch

### List files
For list files, just use the instance you created of the `omhStorageClient` and call method `listFiles` sending as parameter the desired parent id.

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

If you are configuring this step by step on the `code-starter` branch:
1. Copy this snippet
2. Go to the file `FileViewerViewModel`
3. Search the comment `// Add here snippet for list files`
4. Paste there the snippet

### Create files
For create files, just use the instance you created of the `omhStorageClient` and call method `createFile` sending as parameter the desired name, mime type and parent id.

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

If you are configuring this step by step on the `code-starter` branch:
1. Copy this snippet
2. Go to the file `FileViewerViewModel`
3. Search the comment `// Add here snippet for create files`
4. Paste there the snippet

### Delete files
For delete files, just use the instance you created of the `omhStorageClient` and call method `deleteFile` sending as parameter the id of the file you want to delete.

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

If you are configuring this step by step on the `code-starter` branch:
1. Copy this snippet
2. Go to the file `FileViewerViewModel`
3. Search the comment `// Add here snippet for delete files`
4. Paste there the snippet

### Upload files
For upload files, just use the instance you created of the `omhStorageClient` and call method `uploadFile` sending as parameter the local path of the file you want to upload and the id of the remote folder where you want to place it (parent id).

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

If you are configuring this step by step on the `code-starter` branch:
1. Copy this snippet
2. Go to the file `FileViewerViewModel`
3. Search the comment `// Add here snippet for upload files`
4. Paste there the snippet

### Update files
For update files, just use the instance you created of the `omhStorageClient` and call method `updateFile` sending as parameter the local path of the file you want to update and the id of the remote file you want to replace (file id).

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

If you are configuring this step by step on the `code-starter` branch:
1. Copy this snippet
2. Go to the file `FileViewerViewModel`
3. Search the comment `// Add here snippet for update files`
4. Paste there the snippet

### Download files
For download files, just use the instance you created of the `omhStorageClient` and call method `createFile` sending as parameter the id of the file you want to download and the mime type you desire to have locally (once downloaded)

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

If you are configuring this step by step on the `code-starter` branch:
1. Copy this snippet
2. Go to the file `FileViewerViewModel`
3. Search the comment `// Add here snippet for download files`
4. Paste there the snippet

# Sample App
This repository includes a [Storage-sample](/storage-sample) that demonstrates the functionality of the OMH Storage Client Library. 
By cloning the repo and executing the app, you can explore the various features offered by the library. 
However, if you prefer a step-by-step approach to learn the SDK from scratch, we recommend following the detailed Getting Started guide provided in this repository.
The guide will walk you through the implementation process and help you integrate the OMH Storage Client Library into your projects effectively.

**Note: Before running the sample application, make sure to follow the steps in Setup your Google Cloud project for application with Google Services to configure your Google Cloud project.**

# Documentation
See example and check the full documentation and add custom implementation at our [Wiki](https://github.com/openmobilehub/omh-storage/wiki).

Additionally for more information about the OMH Storage functions, [Docs](https://openmobilehub.github.io/omh-storage).

# Provider Implementations / Plugins
OMH Storage SDK is open-source, promoting community collaboration and plugin support from other storage providers to enhance capabilities and expand supported storage services. More details can be found at [the wiki](https://github.com/openmobilehub/omh-storage/wiki/Creating-a-custom-implementation).

# Contributing
Please contribute! We will gladly review any pull requests. Make sure to read the [CONTRIBUTING](https://github.com/openmobilehub/omh-storage/blob/main/CONTRIBUTING.md) page first though.

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
