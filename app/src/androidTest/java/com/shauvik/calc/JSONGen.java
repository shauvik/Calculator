package com.shauvik.calc;

import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Model {
    public String name;
    public Map<String, ModelMethod> methods;
}

class ModelMethod {
    public String[] args;
    public String result;
}

public class JSONGen {
    @Test
    public void generate() {
        final Set<Model> models = genModels(com.shauvik.calc.model.Calculator.class);

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d("MODEL OUTPUT", gson.toJson(models));
    }

    public Set<Model> genModels(Class target) {
        final HashSet<Model> models = new HashSet<>();
        genModels(models, target, new HashSet<>());
        return models;
    }

    public void genModels(Set<Model> models, Class target, Set<Class> visited) {
        if (visited.contains(target)) {
            return;
        }
        visited.add(target);

        final Model model = new Model();

        model.name = target.getSimpleName();

        model.methods = Arrays.stream(target.getDeclaredMethods())
                .filter((m) -> ((m.getModifiers() & Modifier.PUBLIC) != 0) && ((m.getModifiers() & Modifier.STATIC) == 0))
                .map((m) -> {
                    final ModelMethod method = new ModelMethod();

                    method.args = Arrays.stream(m.getParameterTypes())
                            .map(Class::getSimpleName)
                            .toArray(String[]::new);
                    method.result = m.getReturnType().getSimpleName();

                    genModels(models, m.getReturnType(), visited);

                    return new Pair<>(m.getName(), method);
                })
                .collect(HashMap::new, (m, method) -> m.put(method.first, method.second), HashMap::putAll);

        models.add(model);
    }
}