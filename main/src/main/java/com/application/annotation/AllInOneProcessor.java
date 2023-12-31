package com.application.annotation;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

//thx to https://stackoverflow.com/a/30199085

@SupportedAnnotationTypes("com.application.ALLInOneGenerator")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class AllInOneProcessor extends AbstractProcessor {

    private Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elements = processingEnv.getElementUtils();
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(AllInOneGenerator.class)) {
            Class elementClass = element.getClass();

            System.out.println("dwadwadwadwwadadwdwaw");
            PackageElement pacakgeElement = elements.getPackageOf(element);
            String packageName = pacakgeElement.getQualifiedName().toString();
            String controllerClassName = elementClass.getSimpleName() + "Controller";

            try {
                try (Writer file = processingEnv.getFiler().createSourceFile(packageName + "." + controllerClassName).openWriter()) {
                    file.write(String.format("package %s; public class %s {}", packageName, controllerClassName));
                    file.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(AllInOneProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    /*
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(AllInOneGenerator.class)) {
            TypeMirror mirror = ((TypeElement) element).getSuperclass();

            PackageElement pacakgeElement = elements.getPackageOf(element);
            String packageName = pacakgeElement.getQualifiedName().toString();
            String superClassName = ((DeclaredType) mirror).asElement().getSimpleName().toString();

            try {
                try (Writer file = processingEnv.getFiler().createSourceFile(packageName + "." + superClassName).openWriter()) {
                    file.write(String.format("package %s; public class %s {}", packageName, superClassName));
                    file.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(AllInOneProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }
*/
}