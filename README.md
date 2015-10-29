# android6-permission-bug
Android 6.0 Marshmallow has a bug when revoking a permission. The process is killed, but somehow a DialogFragment manages to stay alive and is being restored.
