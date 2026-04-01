# Hot Context — Kohala Android

<!--
  HOT CONTEXT: A snapshot of current session state for cross-session transfer.
  
  Commands:
    OUT: "output hot context" — Claude regenerates this file from current state
    IN:  "load hot context"   — Claude reads this file to restore session state
    
  This is the volatile register. Include/Router/Plan are the stable store.
-->

## LAST UPDATED
2026-04-01 — Session 004

## SESSION STATE

```
BUILDOUT:       8/8 steps COMPLETE
ROUTER:         10/10 modules DONE
OPEN QUESTIONS: 5/5 resolved
STUBS/TODOS:    0 remaining
APK STATUS:     BUILT + SIGNED (release 8.2MB)
```

## DECISIONS IN EFFECT

| Decision | Value | Made In |
|----------|-------|---------|
| UI Framework | Jetpack Compose | Session 001 |
| Min SDK | API 28 (Android 9+) | Session 001 |
| Root strategy | Root-only v1 (su commands) | Session 001 |
| .so delivery | Bundled in APK assets/ | Session 001 |
| Service type | Foreground (SPECIAL_USE) | Session 003 |
| Signing | kohala-release.jks (gitignored) | Session 003 |
| License validation | Offline SHA-256 checksum | Session 004 |

## CURRENT FILE MAP

```
com.sigand.kohala/
├── MainActivity.kt                    — Entry, edge-to-edge Compose
├── ui/
│   ├── KohalaApp.kt                  — NavHost: home/settings/about
│   ├── HomeScreen.kt                 — Live status, install/uninstall/toggle
│   ├── SettingsScreen.kt             — Quality presets, per-game overrides, log export
│   ├── SettingsViewModel.kt          — Preset + override persistence, log collection + share
│   ├── AboutScreen.kt                — Branding, license activation, links, legal
│   ├── AboutViewModel.kt             — License key activation/deactivation
│   └── LayerViewModel.kt             — Bridges installer+config to HomeScreen
├── installer/
│   ├── LayerInstaller.kt             — Root su copy .so + JSON manifest
│   ├── LayerValidator.kt             — Root file existence checks
│   ├── Uninstaller.kt                — Root removal + validation
│   ├── LayerConfig.kt                — JSON config, quality presets, per-game overrides
│   └── LicenseValidator.kt           — Offline key validation (SHA-256 checksum)
└── service/
    └── LayerStatusService.kt          — Foreground service, 60s health check
```

## BUILD ARTIFACTS

| Artifact | Path | Size |
|----------|------|------|
| Debug APK | `app/build/outputs/apk/debug/app-debug.apk` | 24 MB |
| Release APK | `app/build/outputs/apk/release/app-release.apk` | 8.2 MB |
| Keystore | `kohala-release.jks` (gitignored) | — |

## KNOWN GAPS / NEXT WORK

- [ ] No actual `libVkLayer_kohala.so` in assets/ (needs real layer binary from C++ build)
- [ ] Vulkan instance validation in LayerValidator (currently: file check only)
- [ ] Notification permission runtime request (API 33+)

## WHAT TO TELL NEXT SESSION

> Plan is fully executed. All 8 buildout steps done, all 5 open questions
> resolved, zero TODOs/stubs in code. The signed release APK builds clean
> at 8.2MB. Remaining work is integration: drop in the real .so binary,
> add Vulkan instance validation, and request notification permission on
> API 33+. Branch: `claude/kohala-android-scaffold-1GFaz`.
