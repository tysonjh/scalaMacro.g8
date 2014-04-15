# Scala Macro giter8 template

A giter8 template for a Scala macro project that uses the [macro paradise](http://docs.scala-lang.org/overviews/macros/paradise.html) plugin. This is essentially the giter8ion of [scalamacros/sbt-example-paradise](https://github.com/scalamacros/sbt-example-paradise). It includes an sbt configuration for a `macro` project where the macro should be implemented and a `core` project which depends on the `macro` project.

The intent is for a simple and quick way to set up a macro project and explore using the REPL. If you're new start out using the [Quasiquote guide](http://den.sh/quasiquotes.html) as a resource. Some REPL examples taken from the Quasiquote guide,

```scala
sbt> project macros
sbt> console
scala> val tree = q"i am { a quasiquote }"
tree: reflect.runtime.universe.Apply = i.am(a.quasiquote)

scala> val code = q"""println("compiled and run at runtime!")"""
code: reflect.runtime.universe.Apply = println("compiled and run at runtime!")

scala> val compiledCode = toolbox.compile(code)
compiledCode: () => Any = <function0>

scala> val result = compiledCode()
compiled and run at runtime!
result: Any = ()
```

## Notes

* The macro paradise plugin and quasiquote dependency are only included for Scala 2.10.x.
* If you use Scala 2.11.x make sure to check out `showCode` because it is very cool

```scala
scala> val code = q"""println("compiled and run at runtime!")"""
code: reflect.runtime.universe.Apply = println("compiled and run at runtime!")

scala> showRaw(code)
res7: String = Apply(Ident(TermName("println")), List(Literal(Constant("compiled and run at runtime!"))))

scala> showCode(code)
res6: String = println("compiled and run at runtime!")
```