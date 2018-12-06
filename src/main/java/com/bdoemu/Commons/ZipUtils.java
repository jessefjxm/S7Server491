package com.bdoemu.Commons;

import java.net.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;

public class ZipUtils {
    public static FileSystem createZipFileSystem(final String zipFilename, final boolean create) throws IOException {
        final Path path = Paths.get(zipFilename, new String[0]);
        final URI uri = URI.create("jar:file:" + path.toUri().getPath());
        final Map<String, String> env = new HashMap<String, String>();
        if (create) {
            env.put("create", "true");
        }
        return FileSystems.newFileSystem(uri, env);
    }
}
