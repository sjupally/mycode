package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.utils.DeviceCommunicationSummaryVO;

@SuppressWarnings("deprecation")
@Repository
public class DeviceCommunicationSummarySQLRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<DeviceCommunicationSummaryVO> getDeviceCommunicationSummary(DeviceCommunicationSummaryVO request, Boolean isDistrictWise) {
        StringBuilder builder = new StringBuilder();
        StringBuilder sql = new StringBuilder();
        if (!StringUtils.isEmpty(request.getStateId())) {
            builder.append(" and v.state_id=" + request.getStateId());
        }
        if (!StringUtils.isEmpty(request.getDistrictId())) {
            builder.append(" and v.district_id=" + request.getDistrictId());
        }
        if (!StringUtils.isEmpty(request.getCityId())) {
            builder.append(" and v.city_id=" + request.getCityId());
        }
        if (!StringUtils.isEmpty(request.getSearchDate())) {
            builder.append(" and dc.packet_date=" + '\'' +request.getSearchDate()+ '\'');
        }

        sql.append("select count(dc.status) As count, dc.status As statusType ");//,  di.name As districtName, di.id As districtId
        if (isDistrictWise) {
            sql.append(", di.name As districtName, di.id As districtId ");
        }
        sql.append(" from device_communication dc");
        sql.append(" JOIN vehicle_details v ON v.serial_number = dc.serial_number");
        sql.append(" JOIN state s ON s.id = v.state_id");
        sql.append(" JOIN districts di ON di.id = v.district_id");
        sql.append(" JOIN city ci ON ci.id = v.city_id");
        sql.append(" where 1=1");
        sql.append(builder.toString());
        sql.append(" GROUP BY dc.status");//, di.name, di.id
        if (isDistrictWise) {
            sql.append(", di.name, di.id ");
        }

        Session session = (Session) manager.getDelegate();
        Query query = session.createSQLQuery(sql.toString())
                .addScalar("statusType", StringType.INSTANCE)
                .addScalar("count", LongType.INSTANCE);
        if (isDistrictWise) {
            query = session.createSQLQuery(sql.toString())
                    .addScalar("statusType", StringType.INSTANCE)
                    .addScalar("count", LongType.INSTANCE)
                    .addScalar("districtName", StringType.INSTANCE)
                    .addScalar("districtId", LongType.INSTANCE);
        }
        query.setHint("org.hibernate.cacheable", Boolean.TRUE);
        query.setResultTransformer(Transformers.aliasToBean(DeviceCommunicationSummaryVO.class));

        return query.list();
    }

    public List<DeviceCommunicationSummaryVO> getDeviceCommunicationMovementSummary(DeviceCommunicationSummaryVO request, Boolean isDistrictWise) {
        StringBuilder builder = new StringBuilder();
        StringBuilder sql = new StringBuilder();
        if (!StringUtils.isEmpty(request.getStateId())) {
            builder.append(" and v.state_id=" + request.getStateId());
        }
        if (!StringUtils.isEmpty(request.getDistrictId())) {
            builder.append(" and v.district_id=" + request.getDistrictId());
        }
        if (!StringUtils.isEmpty(request.getCityId())) {
            builder.append(" and v.city_id=" + request.getCityId());
        }
        if (!StringUtils.isEmpty(request.getSearchDate())) {
            builder.append(" and dc.packet_date=" + '\'' +request.getSearchDate()+ '\'');
        }

        sql.append(" select count(dc.movement) As count, dc.movement As statusType");
        if (isDistrictWise) {
            sql.append(", di.name As districtName, di.id As districtId ");
        }
        sql.append(" from device_communication dc");
        sql.append(" JOIN vehicle_details v ON v.serial_number = dc.serial_number");
        sql.append(" JOIN state s ON s.id = v.state_id");
        sql.append(" JOIN districts di ON di.id = v.district_id");
        sql.append(" JOIN city ci ON ci.id = v.city_id");
        sql.append(" where 1=1");
        sql.append(builder.toString());
        sql.append(" GROUP BY dc.movement");//, di.name, di.id
        if (isDistrictWise) {
            sql.append(", di.name, di.id ");
        }

        Session session = (Session) manager.getDelegate();
        Query query = session.createSQLQuery(sql.toString())
                .addScalar("statusType", StringType.INSTANCE)
                .addScalar("count", LongType.INSTANCE);
        if (isDistrictWise) {
            query = session.createSQLQuery(sql.toString())
                    .addScalar("statusType", StringType.INSTANCE)
                    .addScalar("count", LongType.INSTANCE)
                    .addScalar("districtName", StringType.INSTANCE)
                    .addScalar("districtId", LongType.INSTANCE);
        }
        query.setHint("org.hibernate.cacheable", Boolean.TRUE);
        query.setResultTransformer(Transformers.aliasToBean(DeviceCommunicationSummaryVO.class));

        return query.list();
    }

}
