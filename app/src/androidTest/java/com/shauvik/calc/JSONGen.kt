package com.shauvik.calc

import android.util.Log
import com.google.gson.GsonBuilder
import org.junit.Test
import java.lang.reflect.Modifier
import java.util.*

internal class Model {
    lateinit var name: String
    lateinit var methods: Map<String, ModelMethod>
}

internal class ModelMethod {
    lateinit var args: Array<String>
    lateinit var result: String
}

class JSONGen {
    @Test
    fun generate() {
        val models = genModels(com.shauvik.calc.model.Calculator::class.java)

        val gson = GsonBuilder().run {
            setPrettyPrinting()
            create()
        }
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

        val model = Model().apply {
            name = target.simpleName

            methods = Arrays.stream(target.declaredMethods)
                    .filter { it.modifiers and Modifier.PUBLIC != 0 && it.modifiers and Modifier.STATIC == 0 }
                    .map {
                        val method = ModelMethod().apply {
                            args = Arrays.stream(it.parameterTypes)
                                    .map { it.simpleName }
                                    .toArray { arrayOfNulls<String>(it) }

                            result = it.returnType.simpleName
                        }

                        genModels(models, it.returnType, visited)

                        it.name to method
                    }
                    .collect<HashMap<String, ModelMethod>>(
                            ::HashMap,
                            { m, (name, method) -> m[name] = method },
                            { obj, m -> obj.putAll(m) }
                    )
        }

        models.add(model)
    }
}