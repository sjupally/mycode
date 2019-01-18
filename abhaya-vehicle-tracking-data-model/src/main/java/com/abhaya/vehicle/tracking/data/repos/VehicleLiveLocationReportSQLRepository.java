package com.abhaya.vehicle.tracking.data.repos;

import java.util.ArrayList;
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

import com.abhaya.vehicle.tracking.utils.VehicleLiveLocByDistrictVO;

@SuppressWarnings("deprecation")
@Repository
public class VehicleLiveLocationReportSQLRepository {

    @PersistenceContext
    private EntityManager manager;

    //Until Hibernate 6 release ,you have to use these deprecated methods only,no other alternative to these deprecated methods in 5.3	
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<VehicleLiveLocByDistrictVO> getVehicleStatusInfo(Long districtId, String searchDate) {
        List<VehicleLiveLocByDistrictVO> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        StringBuilder sqlBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(districtId)) {
            builder.append(" and district_id=" + districtId);
        }


        sqlBuilder.append("select A.dist As name, count(A.maxId) As value FROM (");
        sqlBuilder.append(" select (max(tt.id)) as maxId, di.name as dist ");
        sqlBuilder.append(" from travel_tracking tt");
        sqlBuilder.append(" JOIN vehicle_details v ON v.serial_number = tt.serial_number");
        sqlBuilder.append(" JOIN districts di ON di.id = v.district_id");
        sqlBuilder.append(" where 1=1 ");
        sqlBuilder.append(builder.toString());
        sqlBuilder.append(" GROUP By v.serial_number, di.name) A ");
        sqlBuilder.append(" GROUP BY A.dist ");

        Session session = (Session) manager.getDelegate();
        Query query = session.createSQLQuery(sqlBuilder.toString())
                .addScalar("name", StringType.INSTANCE)
                .addScalar("value", LongType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(VehicleLiveLocByDistrictVO.class));

        return query.list();
    }
}
