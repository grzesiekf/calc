package plugin;


import operation.Operation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class PluginLoader {

    private List<String> scanJarFileForClasses(File file) throws IOException, IllegalArgumentException {
        if (file == null || !file.exists())
            throw new IllegalArgumentException("Invalid jar-file to scan provided");
        if (file.getName().endsWith(".jar")) {
            List<String> foundClasses = new ArrayList<String>();
            try (JarFile jarFile = new JarFile(file)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String name = entry.getName();
                        name = name.substring(0, name.lastIndexOf(".class"));
                        name = name.replaceAll("/", ".");
                        foundClasses.add(name);
                    }
                }
            }
            return foundClasses;
        }
        throw new IllegalArgumentException("No jar-file provided");
    }

    private List<Class<?>> findImplementingClassesInJarFile(File file, Class<?> iface, ClassLoader loader) throws Exception {
        List<Class<?>> implementingClasses = new ArrayList<>();
        for (String classFile : scanJarFileForClasses(file)) {
            Class<?> clazz;
            try {
                if (loader == null)
                    clazz = Class.forName(classFile);
                else
                    clazz = Class.forName(classFile, true, loader);
                if (iface.isAssignableFrom(clazz) && !clazz.equals(iface))
                    implementingClasses.add(clazz);
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
        return implementingClasses;
    }

    private List<Operation> loadJar(File jar) {
        List<Operation> operationList = new ArrayList<>();
        if (jar == null || !jar.exists())
            return operationList;
        try {
            URL downloadURL = jar.toURI().toURL();
            URL[] downloadURLs = new URL[]{downloadURL};
            URLClassLoader loader = URLClassLoader.newInstance(downloadURLs, getClass().getClassLoader());

            List<Class<?>> implementingClasses = findImplementingClassesInJarFile(jar, Operation.class, loader);
            for (Class<?> clazz : implementingClasses) {
                try {
                    Operation instance = (Operation) clazz.newInstance();
                    operationList.add(instance);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return operationList;
    }

    public List<Operation> loadPlugins() {
        List<Operation> operationList = new ArrayList<>();
        try {
            List<File> jars = Files.find(Paths.get("plugins"), 1,
                (path, basicFileAttributes) -> String.valueOf(path).endsWith(".jar")).map(Path::toFile).collect(Collectors.toList());

        for (File jar : jars) {
            operationList.addAll(loadJar(jar));
        }
    } catch (IOException e) {
        System.err.println(e.getMessage());
    }
        return operationList;
    }
}
