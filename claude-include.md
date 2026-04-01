# Claude Include — Kohala Android

<!--
  C++ style include guard pattern for Claude context chaining.
  This file acts as the #include directive — it defines what gets
  pulled into context and in what order, preventing circular refs.

  Usage: Reference this file to bootstrap the full project context.
  Think of it like:

    #ifndef KOHALA_ANDROID_CONTEXT_H
    #define KOHALA_ANDROID_CONTEXT_H

    #include "hot-context.md"          // Volatile state — session transfer
    #include "router.md"               // Routing table — maps modules to plans
    #include "claude-project-plan.md"  // Master build plan

    #endif

  SESSION TRANSFER COMMANDS:
    "output hot context" → Claude regenerates hot-context.md from current state
    "load hot context"   → Claude reads hot-context.md to restore session state
-->

## INCLUDE GUARD

```
KOHALA_ANDROID_CONTEXT := LOADED
```

## INCLUDES

| Order | File | Purpose | Status |
|-------|------|---------|--------|
| 0 | `hot-context.md` | Volatile session state — cross-session transfer | ACTIVE |
| 1 | `claude-include.md` | THIS FILE — entry point, include guard | ACTIVE |
| 2 | `router.md` | Module routing table — maps components to plan sections | ACTIVE |
| 3 | `claude-project-plan.md` | Master project plan — full spec and buildout order | ACTIVE |

## CONTEXT CHAIN

When Claude loads this file, the following chain executes:

```
claude-include.md          // Entry point — define scope, set guard
    ├── hot-context.md     // Volatile register — session state snapshot
    └── router.md          // Route: which module maps to which plan section
         └── claude-project-plan.md  // Full plan: structure, flows, buildout
```

## SCOPE DEFINITION

- **Project:** Kohala Android — Vulkan Layer Installer + UI
- **Language:** Kotlin
- **Platform:** Android (API 28+)
- **Architecture:** Single-activity, Compose UI, installer service
- **Repo:** curbthepain/Kohala-Android
- **Branch:** claude/kohala-android-scaffold-1GFaz

## DIRECTIVES

1. All code changes follow the buildout order in `claude-project-plan.md` §5
2. Each session completes one deliverable from the buildout order
3. Session log in `claude-project-plan.md` §SESSION LOG gets updated after each session
4. Open questions in §6 are resolved as decisions are made
5. Router maps every module to its plan section — no orphan code
