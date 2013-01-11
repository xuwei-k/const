package com.github.xuwei_k

import scala.reflect._
import reflect.macros._
import language.experimental.macros

object Const{

  def const: String = macro Const.impl

  private def definingValName(c: Context): String = {
    import c.universe.{Apply=>ApplyTree,_}
    val methodName = c.macroApplication.symbol.name.decoded
    def enclosingVal(trees: List[c.Tree]): String =
      trees match {
        case vd @ ValDef(_, name, _, _) :: ts => name.decoded
        case (_: ApplyTree | _: Select | _: TypeApply) :: xs => enclosingVal(xs)
        case _ =>
          c.error(c.enclosingPosition, "error")
          "<error>"
      }
    enclosingVal(enclosingTrees(c).toList)
  }

  private def enclosingTrees(c: Context): Seq[c.Tree] =
    c.asInstanceOf[reflect.macros.runtime.Context].callsiteTyper.context.enclosingContextChain.map(_.tree.asInstanceOf[c.Tree])

  def impl(c: Context): c.Expr[String] = {
    import c.universe._
    val enclosingValName = definingValName(c)
    val name = c.Expr[String]( Literal(Constant(enclosingValName)) )
    reify { name.splice }
  }

}

