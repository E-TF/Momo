package com.project.momo.event;

import com.project.momo.common.lock.DistributedLock;
import com.project.momo.common.lock.LockName;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LockNameVerifyEvent implements ApplicationListener<ApplicationStartingEvent> {

    private static final String SCAN_PACKAGE_PATH = "com/project/momo/service";
    private static final String CLASS_EXTENSION_NAME = ".class";
    private static final String CLASS_NOT_FOUND_ERROR_MSG = "error thrown during application ready event\n";
    private static final String LOCK_NAME_ERROR_MSG_FORMAT = "package : %s\n" +
            "method : %s\n" +
            "Parameters : %s\n" +
            "Error : @LockName Not Found";

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        List<? extends Class<?>> collect = new BufferedReader(new InputStreamReader(Objects.requireNonNull(ClassLoader
                .getSystemClassLoader()
                .getResourceAsStream(SCAN_PACKAGE_PATH))))
                .lines().filter(line -> line.endsWith(CLASS_EXTENSION_NAME))
                .map(line -> {
                    try {
                        return Class.forName(SCAN_PACKAGE_PATH
                                .replaceAll("/", ".")
                                + "."
                                + line.substring(0, line.lastIndexOf('.')
                                )
                        );
                    } catch (ClassNotFoundException e) {
                        System.err.println(CLASS_NOT_FOUND_ERROR_MSG + e);
                        System.exit(-1);
                    }
                    return null;
                })
                .collect(Collectors.toList());
        for (Class clazz : collect) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(DistributedLock.class)) {
                    boolean hasLockNameAnnotation = false;
                    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                    for (Annotation[] annotations : parameterAnnotations) {
                        for (Annotation annotation : annotations) {
                            if (annotation instanceof LockName) {
                                hasLockNameAnnotation = true;
                                break;
                            }
                        }
                    }
                    if (!hasLockNameAnnotation) {
                        System.err.printf(LOCK_NAME_ERROR_MSG_FORMAT,
                                method.getClass(),
                                method.getName(),
                                Arrays.toString(method.getParameters()));
                        System.exit(-1);
                    }
                }
            }
        }
    }
}
