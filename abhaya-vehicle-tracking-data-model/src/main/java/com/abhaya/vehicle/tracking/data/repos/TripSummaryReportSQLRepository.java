package com.abhaya.vehicle.tracking.data.repos;

import com.abhaya.vehicle.tracking.utils.TripSummaryVO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
@Repository
public class TripSummaryReportSQLRepository {

    @PersistenceContext
    private EntityManager manager;

    //Until Hibernate 6 release ,you have to use these deprecated methods only,no other alternative to these deprecated methods in 5.3	
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<TripSummaryVO> tripSummaryReport(String searchDate, Boolean isDistrictWise, Long districtId) {
        StringBuilder builder = new StringBuilder();
        StringBuilder sqlBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(searchDate)) {
            builder.append(" AND date_trunc('day',request_time) = to_date('" + searchDate + "','YYYY-MM-DD')");
        }
        if (!StringUtils.isEmpty(districtId)) {
            builder.append(" AND district_id = " + districtId);
        }
        sqlBuilder.append(" select 'tripCount' As type, count(tr.is_trip_closed) As count, tr.is_trip_closed As isClosed");
        if (isDistrictWise) {
            sqlBuilder.append(", di.name As districtName, di.id As districtId ");
        }
        sqlBuilder.append(" from trip_details tr ");
        sqlBuilder.append(" JOIN vehicle_details v ON v.id = tr.vehicle_id");
        sqlBuilder.append(" JOIN districts di ON di.id = v.district_id");
        sqlBuilder.append(" where 1=1 ");
        sqlBuilder.append(builder.toString());
        sqlBuilder.append(" GROUP BY tr.is_trip_closed ");
        if (isDistrictWise) {
            sqlBuilder.append(", di.name, di.id ");
        }

        Session session = (Session) manager.getDelegate();
        Query query = session.createSQLQuery(sqlBuilder.toString())
                .addScalar("type", StringType.INSTANCE)
                .addScalar("count", LongType.INSTANCE)
                .addScalar("isClosed", BooleanType.INSTANCE);
        if (isDistrictWise) {
            query = session.createSQLQuery(sqlBuilder.toString())
                    .addScalar("type", StringType.INSTANCE)
                    .addScalar("count", LongType.INSTANCE)
                    .addScalar("isClosed", BooleanType.INSTANCE)
                    .addScalar("districtName", StringType.INSTANCE)
                    .addScalar("districtId", LongType.INSTANCE);
        }
        query.setHint("org.hibernate.cacheable", Boolean.TRUE);
        query.setResultTransformer(Transformers.aliasToBean(TripSummaryVO.class));

        return query.list();
    }

    public List<TripSummaryVO> routeDeviatedAndPanicSummaryReport(String searchDate, Boolean isDistrictWise, Long districtId) {
        List<TripSummaryVO> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        StringBuilder routeDeviationSql = new StringBuilder();
        if (!StringUtils.isEmpty(searchDate)) {
            builder.append(" AND date_trunc('day',request_time) = to_date('" + searchDate + "','YYYY-MM-DD')");
        }
        if (!StringUtils.isEmpty(districtId)) {
            builder.append(" AND district_id = " + districtId);
        }
        routeDeviationSql.append(" select 'routeDeviation' As type, count(r.id) As count, count(tr.id) As tripCount");
        if (isDistrictWise) {
            routeDeviationSql.append(", di.name As districtName, di.id As districtId ");
        }
        routeDeviationSql.append(" from route_deviation r ");
        routeDeviationSql.append(" RIGHT OUTER JOIN trip_details tr ON tr.id = r.trip_id");
        routeDeviationSql.append(" JOIN vehicle_details v ON v.id = tr.vehicle_id");
        routeDeviationSql.append(" JOIN districts di ON di.id = v.district_id");
        routeDeviationSql.append(" where 1=1 ");
        routeDeviationSql.append(builder.toString());
        if (isDistrictWise) {
            routeDeviationSql.append(" GROUP BY di.name, di.id   ");
        }

        Session session = (Session) manager.getDelegate();
        Query query = session.createSQLQuery(routeDeviationSql.toString())
                .addScalar("type", StringType.INSTANCE)
                .addScalar("count", LongType.INSTANCE)
                .addScalar("tripCount", LongType.INSTANCE);
        if (isDistrictWise) {
            query = session.createSQLQuery(routeDeviationSql.toString())
                    .addScalar("type", StringType.INSTANCE)
                    .addScalar("count", LongType.INSTANCE)
                    .addScalar("tripCount", LongType.INSTANCE)
                    .addScalar("districtName", StringType.INSTANCE)
                    .addScalar("districtId", LongType.INSTANCE);
        }
        query.setHint("org.hibernate.cacheable", Boolean.TRUE);
        query.setResultTransformer(Transformers.aliasToBean(TripSummaryVO.class));

        result.addAll(query.list());

        StringBuilder distressSql = new StringBuilder();
        distressSql.append(" select 'distressDetails' As type, count(d.id) As count, count(tr.id) As tripCount");
        if (isDistrictWise) {
            distressSql.append(", di.name As districtName, di.id As districtId ");
        }

        distressSql.append(" from distress_details d ");
        distressSql.append(" RIGHT OUTER JOIN trip_details tr ON tr.id = d.trip_id");
        distressSql.append(" JOIN vehicle_details v ON v.id = tr.vehicle_id");
        distressSql.append(" JOIN districts di ON di.id = v.district_id");
        distressSql.append(" where 1=1 ");
        distressSql.append(builder.toString());
        if (isDistrictWise) {
            distressSql.append(" GROUP BY di.name, di.id   ");
        }

        session = (Session) manager.getDelegate();
        query = session.createSQLQuery(distressSql.toString())
                .addScalar("type", StringType.INSTANCE)
                .addScalar("count", LongType.INSTANCE)
                .addScalar("tripCount", LongType.INSTANCE);
        if (isDistrictWise) {
            query = session.createSQLQuery(distressSql.toString())
                    .addScalar("type", StringType.INSTANCE)
                    .addScalar("count", LongType.INSTANCE)
                    .addScalar("tripCount", LongType.INSTANCE)
                    .addScalar("districtName", StringType.INSTANCE)
                    .addScalar("districtId", LongType.INSTANCE);
        }
        query.setHint("org.hibernate.cacheable", Boolean.TRUE);
        query.setResultTransformer(Transformers.aliasToBean(TripSummaryVO.class));

        result.addAll(query.list());

        return result;
    }
}
