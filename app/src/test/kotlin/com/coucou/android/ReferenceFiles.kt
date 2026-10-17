package com.coucou.android

import java.io.File

/** Locates reference sources so tests can compare against them. */
object ReferenceFiles {
    private fun repoRoot(): File {
        var dir: File? = File(System.getProperty("user.dir")).absoluteFile
        while (dir != null) {
            if (File(dir, "settings.gradle.kts").exists() || File(dir, "LICENSE").exists()) return dir
            dir = dir.parentFile
        }
        return File(System.getProperty("user.dir")).absoluteFile
    }

    private fun resolve(path: String): File {
        val root = repoRoot()
        val direct = File(root, path)
        if (direct.exists()) return direct
        if (path.startsWith("android/")) {
            val stripped = File(root, path.removePrefix("android/"))
            if (stripped.exists()) return stripped
        }
        return direct
    }

    fun has(path: String): Boolean = resolve(path).exists()

    fun read(path: String): String {
        val f = resolve(path)
        if (!f.exists()) return ""
        return f.readText()
    }

    fun file(path: String): File = resolve(path)
}
