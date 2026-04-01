# Hot Context — Kohala Android

<!--
  HOT CONTEXT: A snapshot of current session state for cross-session transfer.
  
  Commands:
    OUT: "output hot context" — Claude regenerates this file from current state
    IN:  "load hot context"   — Claude reads this file to restore session state
    
  This is the volatile register. Include/Router/Plan are the stable store.
-->

## LAST UPDATED
2026-04-01 — Session 003

## SESSION STATE

```
BUILDOUT:     8/8 steps COMPLETE
ROUTER:       10/10 modules DONE
OPEN QUESTIONS: 4/5 resolved (license validation remains)
APK STATUS:   BUILT + SIGNED
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

## CURRENT FILE MAP

```
com.sigand.kohala/
├── MainActivity.kt                    — Entry, edge-to-edge Compose
├── ui/
│   ├── KohalaApp.kt                  — NavHost: home/settings/about
│   ├── HomeScreen.kt                 — Live status, install/uninstall/toggle
│   ├── SettingsScreen.kt             — Radio preset selector
│   ├── AboutScreen.kt                — Branding, tiers, links, legal
│   ├── LayerViewModel.kt             — Bridges installer+config to UI
│   └── SettingsViewModel.kt          — Preset persistence via LayerConfig
├── installer/
│   ├── LayerInstaller.kt             — Root su copy .so + JSON manifest
│   ├── LayerValidator.kt             — Root file existence checks
│   ├── Uninstaller.kt                — Root removal + validation
│   └── LayerConfig.kt                — JSON config, quality presets
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

- [ ] License validation — phone home or offline key check? (last open question)
- [ ] `SettingsViewModel.exportLogs()` is a Toast stub
- [ ] No actual `libVkLayer_kohala.so` in assets/ (expected — needs real layer binary)
- [ ] Per-game overrides stub in SettingsScreen
- [ ] Vulkan instance validation in LayerValidator (currently: file check only)

## WHAT TO TELL NEXT SESSION

> All 8 buildout steps are done. The app compiles, builds, and produces a
> signed release APK. The installer uses root `su` to copy the Vulkan layer
> .so and JSON manifest to `/data/local/vulkan/implicit_layer.d/`. The UI
> is Jetpack Compose with Navigation, ViewModel state management, and a
> foreground status service. The remaining work is: real .so binary,
> license validation, log export, per-game overrides, and Vulkan instance
> validation. Branch: `claude/kohala-android-scaffold-1GFaz`.
