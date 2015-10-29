# android6-permission-bug
Android 6.0 Marshmallow has a bug when revoking a permission. The process is killed, but somehow a DialogFragment manages to stay alive and is being restored.

## How to reproduce

* Run the app
* Press the link to the second screen
* Allow the permission
* Observe the DialogFragment opening
* Go to System Settings -> Apps -> PermissionBug -> Permissions - Revoke Phone permission
* Multitask back to the app
* Observe that you're now on the Index page, but the DialogFragment remains open

## Expected

* When revoking a permission, the process should be killed
* The main activity/process is killed (you can see this in logcat), but the DialogFragment process remains

## Issue

[Issue 191810](https://code.google.com/p/android/issues/detail?id=191810)