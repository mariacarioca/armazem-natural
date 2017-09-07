package br.com.armazemnatural.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ListaArquivosVisitor extends SimpleFileVisitor<Path> {

    private PathMatcher matcher;
    private List<Path> arquivos = new ArrayList<>();

    public ListaArquivosVisitor(PathMatcher matcher) {
        this.matcher = matcher;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (matcher.matches(file)) {
            arquivos.add(file);
        }
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getArquivos() {
        return arquivos;
    }
 }
