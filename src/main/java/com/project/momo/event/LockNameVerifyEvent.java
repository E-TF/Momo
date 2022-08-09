package com.project.momo.event;

import com.project.momo.common.lock.DistributedLock;
import com.project.momo.common.lock.LockName;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LockNameVerifyEvent implements ApplicationListener<ApplicationStartingEvent> {
    private static final int UNSUCCESSFUL_TERMINATION = -1;
    private static final String SERVICE_PACKAGE_PATH = "com/project/momo/service";
    private static final String CLASS_EXTENSION_NAME = ".class";
    private static final String CLASS_NOT_FOUND_ERROR_MSG = "error thrown during application ready event\n";
    private static final String LOCK_NAME_NOT_FOUND_MSG = "@LockName Not Found";
    private static final String ERROR_MSG_FORMAT = "Error : %s\n" +
            "path : %s\n" +
            "method : %s\n" +
            "Parameters : %s\n\n";

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        List<? extends Class<?>> classList = getClassesByPackageName(SERVICE_PACKAGE_PATH);
        List<Method> noLockNameMethodList = getMethodsWithDistributedLock(classList)
                .filter(Predicate.not(this::hasLockName))
                .collect(Collectors.toList());
        if (!noLockNameMethodList.isEmpty()) {
            noLockNameMethodList.forEach(method -> System.err.printf(ERROR_MSG_FORMAT,
                    LOCK_NAME_NOT_FOUND_MSG,
                    method.getDeclaringClass().getName(),
                    method.getName(),
                    Arrays.stream(method.getParameters())
                            .map(Parameter::getType)
                            .collect(Collectors.toList())));
            System.exit(UNSUCCESSFUL_TERMINATION);
        }
    }

    private Stream<Method> getMethodsWithDistributedLock(List<? extends Class<?>> classList) {
        return classList
                .stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Arrays::stream)
                .filter(method -> method.isAnnotationPresent(DistributedLock.class));
    }

    private boolean hasLockName(Method method) {
        return Arrays.stream(method.getParameterAnnotations())
                .flatMap(Arrays::stream)
                .anyMatch(annotation -> annotation instanceof LockName);
    }

    private List<? extends Class<?>> getClassesByPackageName(String packagePath) {
        return new BufferedReader(new InputStreamReader(Objects.requireNonNull(ClassLoader
                .getSystemClassLoader()
                .getResourceAsStream(packagePath))))
                .lines().filter(line -> line.endsWith(CLASS_EXTENSION_NAME))
                .map(className -> loadClassByName(className, packagePath))
                .collect(Collectors.toList());
    }

    private Class<?> loadClassByName(String line, String packagePath) {
        try {
            return Class.forName(packagePath
                            .replaceAll("/", ".")
                            + "."
                            + line.substring(0, line.lastIndexOf('.')
                    )
            );
        } catch (ClassNotFoundException e) {
            System.err.println(CLASS_NOT_FOUND_ERROR_MSG + e);
            System.exit(-1);
            return null;
        }
    }
}
