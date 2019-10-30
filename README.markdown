# liferay-project-sdk

##Build from source
If you would like to build from source, use the following command:
```
./gradlew clean build -PinstallBuilderHome=""  -PstudioDir=""
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
./gradlew clean buildWorkspacewithDevStudioCEInstallers -PinstallBuilderHome=""  -PstudioDir=""
```
3 buildWorkspacewithDevStudioDXPInstallers
```
./gradlew clean buildWorkspacewithDevStudioDXPInstallers -PinstallBuilderHome=""  -PstudioDir=""
```
4 formatSource
```
./gradlew formatSource
```
5 clean
Delete outputs folder.

##About Parameters
```
installBuilderHome
```
Set this to your installBuilder home directory.

```
studioDir
```
Specify a directory which contians the studio zip files. Both absolute path and relative path are supported.