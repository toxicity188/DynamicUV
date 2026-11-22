# projectmem - DynamicUV

_Last updated: 2026-11-22_

## Project purpose
A simple library for Minecraft Java Edition models with in-game configurable textures.

## Recent issues
- [DONE] #legacy_78b0 Legacy issue: fix: texture indexing (fixed)

## Decisions
- No decisions logged yet.

## Notes
- refactor: cleanup code
- chore: update gradle to v9.8.1
- feat: remove default namespace
- chore: update gradle to v9.8.2
- refactor: simplify UVByteBuilder to record
- feat: uv model identifier
- docs: write Javadocs
- update: 1.4.0
- update: 1.4.1
- gotcha: Java 21 is the published library target. The Paper test-plugin currently uses a Java 25 toolchain, so library-only checks do not validate that module. [test-plugin/build.gradle.kts]

## Key files
- `build.gradle.kts`
- `library/src/main/java/kr/toxicity/library/dynamicuv/ModelJson.java`
- `library/src/main/java/kr/toxicity/library/dynamicuv/UVElement.java`
- `library/src/main/java/kr/toxicity/library/dynamicuv/UVMappedFace.java`
- `library/src/main/java/kr/toxicity/library/dynamicuv/UVModel.java`
- `gradle/wrapper/gradle-wrapper.properties`
- `v9.3.1`
- `library/src/main/java/kr/toxicity/library/dynamicuv/UVAxis.java`
- `UVAxis.java`
- `gradle/wrapper/gradle-wrapper.jar`
- `v9.4.0`
- `.idea/misc.xml`
- `gradlew`
- `library/src/main/java/kr/toxicity/library/dynamicuv/UVUtil.java`
- `v9.4.1`
- `.idea/vcs.xml`
- `gradle.properties`
- `library/build.gradle.kts`
- `.idea/gradle.xml`
- `.idea/kotlinc.xml`

## Open questions
- None logged yet.
