package com.shauvik.calc

import android.util.Log
import android.util.Pair
import com.google.gson.GsonBuilder
import org.junit.Test
import java.lang.reflect.Modifier
import java.util.*

internal class Model {
    var name: String? = null
    var methods: Map<String, ModelMethod>? = null
}

internal class ModelMethod {
    var args: Array<String>? = null
    var result: String? = null
}

class JSONGen {
    @Test
    fun generate() {
        val models = genModels(com.shauvik.calc.model.Calculator::class.java)

        val gson = GsonBuilder().setPrettyPrinting().create()
        Log.d("MODEL OUTPUT", gson.toJson(models))
    }

    internal fun genModels(target: Class<*>): Set<Model> {
        val models = HashSet<Model>()
        genModels(models, target, HashSet())
        return models
    }

    internal fun genModels(models: MutableSet<Model>, target: Class<*>, visited: MutableSet<Class<*>>) {
        if (visited.contains(target)) {
            return
        }
        visited.add(target)

        val model = Model()

        model.name = target.simpleName

        model.methods = Arrays.stream(target.declaredMethods)
                .filter { m -> m.modifiers and Modifier.PUBLIC != 0 && m.modifiers and Modifier.STATIC == 0 }
                .map { m ->
                    val method = ModelMethod()

                    method.args = Arrays.stream(m.parameterTypes)
                            .map { m -> m.simpleName }
                            .toArray { s -> arrayOfNulls<String>(s) }
                    method.result = m.returnType.simpleName

                    genModels(models, m.returnType, visited)

                    Pair<String, ModelMethod>(m.name, method)
                }
                .collect<HashMap<String, ModelMethod>>(
                        ::HashMap,
                        { m, method -> m[method.first] = method.second },
                        { obj, m -> obj.putAll(m) }
                )

        models.add(model)
    }
}