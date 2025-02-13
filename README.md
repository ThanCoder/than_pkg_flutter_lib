## 1.3.0

# linux

# needed lib from linux

    sudo apt install net-tools  // wifi
    sudo apt install poppler-utils //pdf thumbnail
    sudo apt install ffmpeg //video thumbnail

# android && linux

    //new method
    await ThanPkg.platform.getAppRootPath();
    await ThanPkg.platform.getAppExternalPath();

    await ThanPkg.platform.getWifiAddressList();
    await ThanPkg.platform.genPdfCover(outDirPath: '', pdfPathList: []);
    await ThanPkg.platform.genVideoCover(outDirPath: '', videoPathList: []);

# android only

    အသုံးပြုနိုင်တဲ့ Methods များ

    await ThanPkg.platform.checkScreenOrientation();
    await ThanPkg.platform.requestScreenOrientation(type: ScreenOrientationTypes.Portrait);

    //android device info <Map> type
    await ThanPkg.platform.getAndroidDeviceInfo()

    //android any version can handle
    await ThanPkg.platform.isStoragePermissionGranted();
    await ThanPkg.platform.requestStoragePermission();

    await ThanPkg.platform.openUrl(url: '');
    await ThanPkg.platform.getPlatformVersion();
    await ThanPkg.platform.getDeviceId();
    await ThanPkg.platform.toggleKeepScreen(isKeep: false);
    await ThanPkg.platform.toggleFullScreen(isFullScreen: !isFullScreen);
    await ThanPkg.platform.checkAndRequestPackageInstallPermission();
    await ThanPkg.platform.getLocalIpAddress();
    await ThanPkg.platform.getWifiAddress();

# android AndroidManifest

    //need permission
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
