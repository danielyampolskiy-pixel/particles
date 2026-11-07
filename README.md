# No Particles — configurable Fabric mod (Minecraft Java 1.21.11)

This is a **build-ready source project**, not a `.jar` yet. It builds with GitHub Actions.
It is based on the idea of the supplied No Particles resource pack, but **does not include the pack's particle shaders**, because those shaders globally discard particles and would override every ON setting.

## Building on GitHub without a local Java installation
1. Create a GitHub repository (such as `No-Particles-Fabric`).
2. Extract this ZIP. Upload **all the files inside this folder** to the repository root; preserve `.github/workflows/build.yml` and `src`.
3. In GitHub click **Actions → Build Fabric mod → Run workflow** (or simply push to `main`).
4. When the build is green, open the workflow run → **Artifacts → Particle-Toggle-JAR**.
5. Extract the artifact ZIP. Place the mod JAR from `build/libs` in `.minecraft/mods` with Fabric Loader for 1.21.11.

If a build is red, share the errors to correct mapping and dependency incompatibilities.

## Particle controls
The mod writes `.minecraft/config/particle-toggle.properties` on its first successful launch.
Each particle has a separate `minecraft:<id>=true/false` setting, for example:

```properties
minecraft:totem_of_undying=true
minecraft:block=false
minecraft:poof=true
minecraft:explosion=false
```

Change values and restart the game. Modded particles are added to the config when registered.
**Caveat**: the filtering hook is in the client particle manager; effects rendered outside it (such as some breaking overlays) are not covered. No in-game toggle menu is implemented yet. The GitHub build has not been verified to succeed.
