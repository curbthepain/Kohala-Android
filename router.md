# Router — Kohala Android Module Map

<!--
  This file routes each code module to its corresponding section
  in claude-project-plan.md. Think of it as a symbol table:
  given a module, look up where it's defined in the plan.

  Upstream:  claude-include.md  (includes this file)
  Downstream: claude-project-plan.md  (this file points into it)
-->

## MODULE ROUTING TABLE

| Module | Package Path | Plan Section | Buildout Step | Status |
|--------|-------------|--------------|---------------|--------|
| **MainActivity** | `com.sigand.kohala.MainActivity` | §1 App Structure | Step 1: Skeleton APK | DONE |
| **HomeScreen** | `com.sigand.kohala.ui.HomeScreen` | §3a HomeScreen | Step 2: HomeScreen UI | DONE (live) |
| **SettingsScreen** | `com.sigand.kohala.ui.SettingsScreen` | §3b SettingsScreen | Step 5: SettingsScreen | DONE |
| **AboutScreen** | `com.sigand.kohala.ui.AboutScreen` | §3c AboutScreen | Step 7: AboutScreen + polish | DONE |
| **LayerInstaller** | `com.sigand.kohala.installer.LayerInstaller` | §2 Installer Flow | Step 3: Installer logic | DONE |
| **LayerValidator** | `com.sigand.kohala.installer.LayerValidator` | §2 Installer Flow | Step 3: Installer logic | DONE |
| **Uninstaller** | `com.sigand.kohala.installer.Uninstaller` | §2b Uninstall Flow | Step 3: Installer logic | DONE |
| **LayerStatusService** | `com.sigand.kohala.service.LayerStatusService` | §1 App Structure | Step 6: LayerStatusService | DONE |
| **Build Config** | `build.gradle.kts` | §4 Build Config | Step 1: Skeleton APK | DONE |
| **Release Pipeline** | CI/signing | §4 Build Config | Step 8: Release build | NOT STARTED |

## DEPENDENCY GRAPH

```
MainActivity
    ├── HomeScreen
    │     ├── LayerInstaller ──→ LayerValidator
    │     └── Uninstaller ──→ LayerValidator
    ├── SettingsScreen
    │     └── (layer config file I/O)
    └── AboutScreen

LayerStatusService (independent background service)
    └── LayerValidator
```

## ROUTE RESOLUTION

To find where a module is defined in the plan:

1. Look up the module in the routing table above
2. Follow the **Plan Section** column to `claude-project-plan.md`
3. The **Buildout Step** tells you when it gets built
4. **Status** tracks current progress

## FLOW ROUTES

| User Action | Entry Point | Route Through | Plan Reference |
|-------------|-------------|---------------|----------------|
| App launch | MainActivity | → HomeScreen → status check | §1, §3a |
| Tap Install | HomeScreen | → LayerInstaller → LayerValidator | §2, §2a |
| Tap Uninstall | HomeScreen | → Uninstaller → LayerValidator | §2b |
| Open Settings | HomeScreen | → SettingsScreen | §3b |
| Open About | HomeScreen | → AboutScreen | §3c |
| Background check | System | → LayerStatusService → LayerValidator | §1 |

## CHAIN LINK

- **Included by:** `claude-include.md` (order 2)
- **Points to:** `claude-project-plan.md` (order 3)
