# KOHALA MOBILE — ANDROID APP FRAMEWORK

**Project:** Kohala Android — Display UI + Layer Installer
**Author:** A. Wisniewski — Sigand, Inc.
**Classification:** CONFIDENTIAL
**Revision:** 0.1 — Scaffold

---

## SESSION LOG

| Session | Date | What Got Built | Notes |
|---------|------|----------------|-------|
| 001 | 2026-04-01 | Skeleton APK + full project scaffold | Compose UI, API 28+, all stubs in place |
| 002 | 2026-04-01 | Steps 3-7: Full installer, settings, about, service | Root su copy, manifest JSON, ViewModel, config, status service, about polish |
| 003 | 2026-04-01 | Step 8: Release build pipeline + gap fixes | Foreground service, adaptive icon, gradlew, signing, ProGuard, APK built |
| 004 | 2026-04-01 | Close all stubs + license validation | Log export, per-game overrides, offline license, FileProvider, zero TODOs |

---

## 1. APP STRUCTURE

Single APK. Two jobs: show the UI, install the layer.

```
com.sigand.kohala/
├── MainActivity          # Entry point, hosts the UI
├── ui/
│   ├── HomeScreen        # Main dashboard — layer status, toggle, version info
│   ├── SettingsScreen    # User-facing config (quality presets, enable/disable)
│   └── AboutScreen       # Sigand branding, licenses, links
├── installer/
│   ├── LayerInstaller    # Copies .so to correct path, sets permissions
│   ├── LayerValidator    # Confirms layer is registered and loadable
│   └── Uninstaller       # Clean removal
├── service/
│   └── LayerStatusService  # Background check — is the layer active and healthy
├── assets/
│   └── libVkLayer_kohala.so  # The actual Vulkan layer binary, bundled in APK
└── res/
    ├── layout/           # XML layouts (or Compose if preferred)
    └── drawable/         # Icons, branding assets
```

---

## 2. INSTALLER FLOW

```
User opens app
    → Check: is layer already installed?
        → YES: Show status (version, health, active/inactive)
        → NO: Show install prompt
            → User taps Install
            → Copy libVkLayer_kohala.so to layer path
            → Write layer JSON manifest
            → Validate layer loads (quick Vulkan instance test)
            → Report success or failure
```

### 2a. Layer Install Path

- **Rooted devices:** `/data/local/vulkan/implicit_layer.d/`
- **Non-rooted (debug/dev):** App-local path via `VK_LAYER_PATH` (requires dev options enabled)
- **Decision needed:** Root-only for v1, or pursue non-root path?

### 2b. Uninstall Flow

```
User taps Uninstall
    → Remove .so from layer path
    → Remove JSON manifest
    → Validate layer no longer loads
    → Report clean removal
```

---

## 3. UI SCREENS

### 3a. HomeScreen

What the user sees on launch:

- Kohala logo + version
- Layer status badge: Installed / Not Installed / Error
- Big toggle: Enable / Disable
- Current config summary (quality preset, active game count)
- Install / Uninstall button (context-dependent)

### 3b. SettingsScreen

- Quality preset selector (Performance / Balanced / Quality)
- Per-game overrides (future — stub for now)
- Layer log viewer (collapsible, for debug)
- Export logs button

### 3c. AboutScreen

- Sigand, Inc. branding
- License tier display (Community / Pro / Studio)
- Links: website, docs, support
- Legal notices

---

## 4. BUILD CONFIG

| Field | Value |
|-------|-------|
| Language | Kotlin |
| UI framework | Jetpack Compose |
| Min SDK | API 28 (Android 9+) |
| Target SDK | Latest stable |
| Build system | Gradle |
| Native dependency | `libVkLayer_kohala.so` (bundled in `assets/` or `jniLibs/`) |
| Signing | Release key managed by Austin |

---

## 5. BUILDOUT ORDER

Do these in order across sessions. Each one is a standalone deliverable.

1. **Skeleton APK** — Empty app, compiles and runs, Sigand branding on launch screen
2. **HomeScreen UI** — Static layout with placeholder status badges and toggle
3. **Installer logic** — Copy .so, write manifest, validate
4. **Wire UI to installer** — Install/uninstall buttons actually work, status updates live
5. **SettingsScreen** — Quality presets wired to layer config file
6. **LayerStatusService** — Background health check, notification if layer fails
7. **AboutScreen + polish** — Branding, legal, links
8. **Release build pipeline** — Signed APK, ProGuard, version tagging

---

## 6. OPEN QUESTIONS

Resolve these before or during buildout:

- [x] Root-only for v1 or attempt non-root layer loading? → **Root-only for v1**
- [x] Jetpack Compose or XML layouts? → **Jetpack Compose**
- [x] Min SDK level — how far back do we reach? → **API 28 (Android 9)**
- [x] Does the .so ship inside the APK or download on first run? → **Bundled in APK assets/**
- [x] License validation — phone home or offline key check? → **Offline key check (SHA-256 checksum validation)**
