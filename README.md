# Soul AI Android starter source

## Included
- Dark-themed Android app
- Gallery video picker and local video playback (Media3)
- Test a Hindi Text-to-Speech voice installed on the phone
- GitHub Actions workflow for cloud APK builds

## Not included yet
- Automatic English speech recognition/transcription
- Translation of video speech into Hindi
- Synchronised Hindi dubbing mixed/replaced over video audio
- YouTube in-app playback or YouTube audio dubbing
- Guaranteed natural voice quality (uses device TTS for sample)

## Build from a phone using GitHub Actions
1. Extract this ZIP.
2. Create a GitHub repository and upload the extracted files/folders, including `.github/workflows/android.yml` (uploading only the ZIP file is not enough).
3. Open the repository's Actions tab and run **Build Soul AI APK** (or push files to the `main` branch).
4. Open the successful run and download the `SoulAI-debug-apk` artifact.
5. Extract the artifact ZIP and install `app-debug.apk` on your Android phone.

This is a starter prototype, not the finished automatic dubbing app. The Hindi button plays a fixed sample only.
