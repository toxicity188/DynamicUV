# DynamicUV Contributor Guide

This guide applies to the entire repository. Follow a more specific `AGENTS.md` within its directory.

## Purpose and modules

DynamicUV is a Java library for Minecraft Java Edition item models with textures configurable in game. It uses the item model format introduced in Minecraft 1.21.4.

- `library/` contains the published API and implementation under `kr.toxicity.library.dynamicuv`. `UVModel` builds resource pack assets and maps image pixels to custom model data for tint colors, flags, and floats.
- `test-plugin/` is a Paper plugin for in-game verification. It consumes `library`. Keep server APIs and test setup in this module.
- `buildSrc/` contains shared Gradle conventions. `gradle/` contains the wrapper and version catalog. The project version comes from `project_version` in `gradle.properties`.

## Java conventions

- Target Java 21 for `library`, including JDK APIs used by its tests. Follow the toolchain declared in each module. The Paper test plugin currently uses Java 25.
- Keep application and test source in Java. Follow `.editorconfig` and nearby code for formatting, imports, and JetBrains nullability annotations.
- Use `var` for locals when the type is clear. Declare classes `final` when inheritance is unnecessary. Prefer records for immutable data carriers and package-private visibility for library internals.
- Name static factories `of(...)` and builder entry points `builder()` where those patterns fit. Prefer an enum for a singleton when one is needed.
- Use the existing fastutil primitive collections for primitive data. Reuse existing helpers and JDK APIs before adding dependencies. Justify new dependencies and follow the existing Gradle version conventions.

## Javadoc

- Write English Javadoc for public API types, constructors, and methods. Update affected Javadoc when changing a public API.
- Document parameters with `@param`, including every record component at the type level. Use `@return` for non-void methods and `@throws` for exceptions callers are expected to handle.
- Use `@since` for new public APIs with the version that will introduce them, based on `project_version`. Preserve existing introduction versions.
- Provide a short usage example for new public entry points using Java 21's `{@snippet}` or a `<pre>{@code ...}</pre>` block.
- Use consistent Minecraft terms. Explain resource pack paths, custom model data indices, defaults, and ordering where callers rely on them.

## Exceptions and compatibility

- Prefer a specific unchecked exception for new failures, such as `IllegalArgumentException` for invalid input. Preserve the cause and add useful context when wrapping an exception.
- Keep checked exceptions out of new public API signatures unless the change explicitly requires them. Preserve existing exception behavior by default.
- Preserve public signatures, behavior, generated JSON, resource paths, and custom model data index ordering unless the task requires a change. Document the impact and migration path for breaking changes. Prefer deprecation before removal when practical.

## Changes and verification

- Make the smallest change that solves the task. Reuse existing code and keep unrelated refactoring and formatting out of the diff.
- For library changes, run `./gradlew :library:test`. Run `./gradlew :library:javadoc` when public Javadoc changes. On Windows, use `.\gradlew.bat`.
- For test plugin changes, run `./gradlew :test-plugin:build`. Use `./gradlew :test-plugin:runServer` when in-game behavior needs verification.
- Add focused regression coverage when existing checks miss the changed behavior. Report which checks ran and any limits.
- Use Conventional Commits. In PRs, explain the reason for the change, affected modules, compatibility impact, and verification.
