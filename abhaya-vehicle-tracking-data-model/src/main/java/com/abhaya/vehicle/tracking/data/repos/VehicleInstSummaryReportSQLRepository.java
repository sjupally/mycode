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

import com.abhaya.vehicle.tracking.utils.VehicleInstSummaryByDistrictVO;

@SuppressWarnings("deprecation")
@Repository
public class VehicleInstSummaryReportSQLRepository {

    @PersistenceContext
    private EntityManager manager;

    //Until Hibernate 6 release ,you have to use these deprecated methods only,no other alternative to these deprecated methods in 5.3	
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<VehicleInstSummaryByDistrictVO> getVehicleStatusInfo(Long districtId) {
        List<VehicleInstSummaryByDistrictVO> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        StringBuilder sqlBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(districtId)) {
            builder.append(" and district_id=" + districtId);
        }


        sqlBuilder.append("select di.name As name , count(vd.district_id) As value");
        sqlBuilder.append(" from vehicle_details vd ");
        sqlBuilder.append(" JOIN districts di ON di.id = vd.district_id");
        sqlBuilder.append(" where is_device_mapped=true ");
        sqlBuilder.append(builder.toString());
        sqlBuilder.append(" group by di.name");

        Session session = (Session) manager.getDelegate();
        Query query = session.createSQLQuery(sqlBuilder.toString())
        		.addScalar("name", StringType.INSTANCE)
                .addScalar("value", LongType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(VehicleInstSummaryByDistrictVO.class));

        return query.list();
    }
}
