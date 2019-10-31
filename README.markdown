# liferay-project-sdk

##Build from source
If you would like to build from source, use the following command:
```
./gradlew clean build -PinstallBuilderHome="" -PstudioDownloadBase="" -PstudioLinuxLatestMD5="" -PstudioWinLatestMD5="" -PstudioMacLatestMD5=""
```

The files will be generated in the following directory
```
./outputs/
```
If you would like to build the installers separately, refer to "About Tasks" section for more details.

##About Tasks
1 buildWorkspaceInstallers
```
./gradlew clean buildWorkspaceInstallers -PinstallBuilderHome=""
```
2 buildWorkspacewithDevStudioCEInstallers
```
./gradlew clean buildWorkspacewithDevStudioCEInstallers -PinstallBuilderHome="" -PstudioDownloadBase="" -PstudioLinuxLatestMD5="" -PstudioWinLatestMD5="" -PstudioMacLatestMD5=""
```
3 buildWorkspacewithDevStudioDXPInstallers
```
./gradlew clean buildWorkspacewithDevStudioDXPInstallers -PinstallBuilderHome=""  -PstudioDownloadBase="" -PstudioLinuxLatestMD5="" -PstudioWinLatestMD5="" -PstudioMacLatestMD5=""
```
4 buildWorkspacewithDevStudioCEWindows
```
./gradlew clean buildWorkspacewithDevStudioCEWindows -PinstallBuilderHome="" -PstudioDownloadBase="" -PstudioWinLatestMD5=""
```
5 formatSource
```
./gradlew formatSource
```
6 clean
Delete outputs folder.

##About Parameters
```
installBuilderHome
```
Set this to your installBuilder home directory.

```
studioDownloadBase
```
The uri should contains the following files:
  > com.liferay.ide.studio-linux.gtk.x86_64.zip
  > com.liferay.ide.studio-macosx.cocoa.x86_64.zip
  > com.liferay.ide.studio-win32.win32.x86_64.zip