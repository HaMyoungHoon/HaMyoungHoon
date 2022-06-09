package hamyounghoon.back.config.mybatis

import org.apache.ibatis.executor.statement.StatementHandler
import org.apache.ibatis.plugin.*
import org.apache.ibatis.reflection.DefaultReflectorFactory
import org.apache.ibatis.reflection.MetaObject
import org.apache.ibatis.reflection.ReflectorFactory
import org.apache.ibatis.reflection.factory.DefaultObjectFactory
import org.apache.ibatis.reflection.factory.ObjectFactory
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory
import org.apache.ibatis.session.RowBounds
import java.sql.Connection
import java.util.*

@Intercepts(Signature(type = StatementHandler::class, method = "prepare", args = [Connection::class, Integer::class]))
class MhhaInterceptor: Interceptor {
    companion object {
        private val DEFAULT_OBJECT_FACTORY: ObjectFactory = DefaultObjectFactory()
        private val DEFAULT_OBJECT_WRAPPER_FACTORY: ObjectWrapperFactory = DefaultObjectWrapperFactory()
        private val DEFAULT_REFLECTOR_FACTORY: ReflectorFactory = DefaultReflectorFactory()
    }
    /**
     * sql paging 을 위한 intercept methods
     * @param invocation sql proceed
     * @return sql proceed
     */
    override fun intercept(invocation: Invocation?): Any {
        val handler = invocation?.target as StatementHandler
        val metaHandler = MetaObject.forObject(handler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY)
        val rowBounds = metaHandler.getValue("delegate.rowBounds") as RowBounds?
        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed()
        }
        if (rowBounds.offset >= 0 && rowBounds.limit >= 0) {
//            val sql = "${handler.boundSql.sql}\nOFFSET ${rowBounds.offset} ROW\nFETCH NEXT ${rowBounds.limit} ROW ONLY"
            val sql = handler.boundSql.sql.replace("ret", "ret\nWHERE rownum BETWEEN ${rowBounds.offset} AND ${rowBounds.limit + rowBounds.offset} ")
            metaHandler.setValue("delegate.boundSql.sql", sql)
        }
        metaHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET)
        metaHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT)
//        val param = if (handler.parameterHandler.parameterObject != null) handler.parameterHandler.parameterObject.toString() else ""

        return invocation.proceed()
    }

    override fun plugin(target: Any?): Any {
        return Plugin.wrap(target, this)
    }

    override fun setProperties(properties: Properties?) {}
}
