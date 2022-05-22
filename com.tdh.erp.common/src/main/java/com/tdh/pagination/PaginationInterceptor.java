package com.tdh.pagination;

import com.tdh.common.Global;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//只拦截select部分
@Intercepts({ @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,ResultHandler.class }) })
public class PaginationInterceptor implements Interceptor {

	private final static Log log = LogFactory.getLog(PaginationInterceptor.class);

	Dialect dialect = new OracleDialect();

	public Object intercept(Invocation invocation) throws Throwable {

		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		Integer pageIndex=new Integer(1);
		Integer pageSize=new Integer(RowBounds.NO_ROW_LIMIT);
		
		if(parameter instanceof Map){
			Map params=(Map)parameter;
			//获取分页信息
			try{
				Object pageIndexObj=params.get(Global.PAGINATION_PAGE_INDEX);
				Object pageSizeObj=params.get(Global.PAGINATION_PAGE_SIZE);
				if(pageSizeObj==null || pageSizeObj==null){
					throw new BindingException();
				}
				pageIndex=new Integer(pageIndexObj.toString());
				pageSize=new Integer(pageSizeObj.toString());
				if(pageIndex.intValue()<=0 || pageSize.intValue()<=0){
					throw new NumberFormatException("page index : "+pageIndex.intValue()+" page size : "+pageIndex.intValue());
				}
			}catch(BindingException ex){
				return invocation.proceed();
			}catch(NumberFormatException ex){
				ex.printStackTrace();
				return invocation.proceed();
			}
		}else{
			return invocation.proceed();
		}
		
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		if(boundSql==null){
			return null;
		}
		String sql=boundSql.getSql().trim();
		if (sql == null || "".equals(sql)){
			return null;
		}
		RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
		
		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
			int offset=(pageIndex-1)*pageSize.intValue();
			rowBounds = new RowBounds( offset , pageSize.intValue());
		}

		// 分页查询 本地化对象 修改数据库注意修改实现
		String pagesql = dialect.getLimitString(sql, rowBounds.getOffset(), rowBounds.getLimit());
		invocation.getArgs()[2] = new RowBounds();
		BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), pagesql, boundSql.getParameterMappings(), boundSql.getParameterObject());
		Field metaParamsField = ReflectUtil.getFieldByFieldName(boundSql, "metaParameters");
		if (metaParamsField != null) {
            MetaObject mo = (MetaObject) ReflectUtil.getValueByFieldName(boundSql, "metaParameters");
            ReflectUtil.setValueByFieldName(newBoundSql, "metaParameters", mo);
        }
		MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));

		invocation.getArgs()[0] = newMs;
		
		return invocation.proceed();

	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties arg0) {

	}

	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.
	 * DefaultParameterHandler
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName
							.startsWith(ForEachSqlNode.ITEM_PREFIX)
							&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
		
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		//builder.keyProperty(ms.getKeyProperties()[0]);
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		
		MappedStatement newMs = builder.build();
		
		return newMs;
	}

}