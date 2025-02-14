## 1.5.0

# needed lib from linux

    sudo apt install net-tools  // wifi
    sudo apt install poppler-utils //pdf thumbnail
    sudo apt install ffmpeg //video thumbnail

# Android && linux

    //new method
    await ThanPkg.platform.getAppRootPath();
    await ThanPkg.platform.getAppExternalPath();

    await ThanPkg.platform.getWifiAddressList();
    await ThanPkg.platform.genPdfCover(outDirPath: '', pdfPathList: []);
    await ThanPkg.platform.genVideoCover(outDirPath: '', videoPathList: []);

# Android Permission

    //android any version can handle
    await ThanPkg.platform.isStoragePermissionGranted();
    await ThanPkg.platform.requestStoragePermission();
    await ThanPkg.platform.checkAndRequestPackageInstallPermission();

# Android only

    new methods
    await ThanPkg.platform.getAppFilePath();
    await ThanPkg.platform.isAppSystemThemeDarkMode();
    await ThanPkg.platform.isAppInternetConnected();
    await ThanPkg.platform.getAppBatteryLevel();
    await ThanPkg.platform.getLastKnownLocation();
    await ThanPkg.platform.getInstalledApps();

    await ThanPkg.platform.checkScreenOrientation();
    await ThanPkg.platform.requestScreenOrientation(type: ScreenOrientationTypes.Portrait);

    //android device info <Map> type
    await ThanPkg.platform.getAndroidDeviceInfo()

    await ThanPkg.platform.openUrl(url: '');
    await ThanPkg.platform.getPlatformVersion();
    await ThanPkg.platform.getDeviceId();
    await ThanPkg.platform.toggleKeepScreen(isKeep: false);
    await ThanPkg.platform.toggleFullScreen(isFullScreen: !isFullScreen);
    await ThanPkg.platform.getLocalIpAddress();
    await ThanPkg.platform.getWifiAddress();

# android AndroidManifest

    //need permission
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>

    //ThanPkg.platform.isAppInternetConnected();
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

    //ThanPkg.platform.getLastKnownLocation();
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"
        tools:ignore="CoarseFineLocation" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
