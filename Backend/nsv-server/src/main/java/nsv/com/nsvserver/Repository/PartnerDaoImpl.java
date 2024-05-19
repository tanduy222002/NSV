package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import nsv.com.nsvserver.Dto.DebtDetailDto;
import nsv.com.nsvserver.Dto.PartnerDetailDto;
import nsv.com.nsvserver.Dto.SearchPartnerDto;
import nsv.com.nsvserver.Dto.TransferTicketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartnerDaoImpl implements PartnerDao{
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public PartnerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<SearchPartnerDto> searchWithFilterAndPagination(Integer page, Integer pageSize, String name, String phone) {
        StringBuilder queryString = new StringBuilder(
                "Select New nsv.com.nsvserver.Dto.SearchPartnerDto(p.id, profile.name,profile.phoneNumber, a) FROM Partner p " +
                        "join p.profile as profile join profile.address as a join fetch a.ward as w join fetch w.district as d join fetch d.province WHERE 1=1"
                );

        if(name!=null){
            queryString.append(" AND profile.name LIKE:namePattern");

        }
        if(phone!=null){
            queryString.append(" AND profile.phoneNumber = :phone");

        }
        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");

        }
        if(phone!=null){
            query.setParameter("phone",phone);

        }
        List<SearchPartnerDto> resultList= query.setFirstResult((page-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }

    @Override
    public long countSearchWithFilter(String name, String phone ) {
        StringBuilder countQueryString = new StringBuilder(
                "Select COUNT(p.id) from Partner p join p.profile profile WHERE 1=1"
        );

        if(name!=null){
            countQueryString.append(" AND profile.name LIKE :namePattern");
        }
        if(phone!=null){
            countQueryString.append(" AND profile.phoneNumber = :phone");
        }

        Query countQuery = entityManager.createQuery(countQueryString.toString(), Long.class);

        if(name!=null){

            countQuery.setParameter("namePattern","%"+name+"%");
        }

        if(phone!=null){
            countQuery.setParameter("phone",phone);
        }

        Long count = (Long) countQuery.getSingleResult();
        return count;
    }

    @Override
    public List<SearchPartnerDto> getStatisticWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String phone) {
        StringBuilder queryString = new StringBuilder(
                "Select New nsv.com.nsvserver.Dto.PartnerWithStatisticDto(p.id, profile.name, profile.phoneNumber, a, SUM(b.weight*b.price) ) FROM Partner p " +
                        "left join p.profile as profile left join profile.address as a left join fetch a.ward as w left join fetch w.district as d left join fetch d.province " +
                        "left join p.transferTickets as tt left join tt.debt as debt left join tt.bins as b WHERE (tt.status IS NULL or tt.status='APPROVED')  "


        );

        if(name!=null){
            queryString.append(" AND profile.name LIKE:namePattern");

        }
        if(phone!=null){
            queryString.append(" AND profile.phoneNumber = :phone");

        }
        queryString.append(" GROUP BY p.id,profile.name,profile.phoneNumber,a");
        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");

        }
        if(phone!=null){
            query.setParameter("phone",phone);

        }
        List<SearchPartnerDto> resultList= query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }



    @Override
    public PartnerDetailDto getPartnerDetailById(Integer id) {
        StringBuilder queryString = new StringBuilder(
                "Select New nsv.com.nsvserver.Dto.PartnerDetailDto(p.id, profile.name, profile.phoneNumber, profile.email, a, partner.bankAccount, partner.taxNumber, partner.faxNumber, COUNT(DISTINCT tt.id), SUM(b.weight*b.price) ) FROM Partner p " +
                        "left join p.profile as profile left join profile.address as a left join fetch a.ward as w left join fetch w.district as d left join fetch d.province " +
                        "left join p.transferTickets as tt left join tt.debt as debt left join tt.bins as b WHERE (tt.status IS NULL or tt.status='APPROVED') AND p.id =:id " +
                        "GROUP BY p.id,profile.name,profile.phoneNumber,a,partner.bankAccount, partner.taxNumber, partner.faxNumber  "


        );

       Query query = entityManager.createQuery(queryString.toString());
       query.setParameter("id",id);
       return (PartnerDetailDto) query.getSingleResult();
    }

    @Override
    public long countGetStatisticWithFilter(String name, String phone) {

        StringBuilder queryString = new StringBuilder(
                "Select COUNT(p.id) FROM Partner p left join p.profile as profile WHERE 1=1"


        );

        if(name!=null){
            queryString.append(" AND profile.name LIKE:namePattern");

        }
        if(phone!=null){
            queryString.append(" AND profile.phoneNumber = :phone");

        }
        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");

        }
        if(phone!=null){
            query.setParameter("phone",phone);

        }
        return (long) query.getSingleResult();
    }

    @Override
    public List<TransferTicketDto> getTransactionsOfPartnerById( Integer pageIndex, Integer pageSize, Integer id, String name, Boolean isPaid) {
        StringBuilder queryString = new StringBuilder(
                "Select New nsv.com.nsvserver.Dto.TransferTicketDto( tt.id,tt.name,tt.transportDate,tt.description, SUM(b.weight), COUNT(DISTINCT b.id), tt.status, tt.type, SUM(b.weight * b.price) ) " +
                        " FROM Partner p " +
                        "left join p.transferTickets as tt left join tt.debt as debt left join tt.bins as b WHERE (tt.status IS NULL or tt.status='APPROVED') AND p.id =:id "
        );



        if(name!=null){
            queryString.append(" AND tt.name LIKE:namePattern");

        }
        if(isPaid!=null){
            if(isPaid) {
                queryString.append(" AND (debt.isPaid IS NULL or debt.isPaid = TRUE)");
            }else{
                queryString.append(" AND debt.isPaid = FALSE ");
            }
        }

        queryString.append(" GROUP BY tt.id,tt.name,tt.transportDate,tt.description, tt.status,tt.type ");

        queryString.append(" ORDER BY tt.transportDate DESC");


        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");

        }

        query.setParameter("id", id);

        return query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public long countTransactionsOfPartnerById(Integer pageIndex, Integer pageSize, Integer id, String name, Boolean isPaid) {
        StringBuilder queryString = new StringBuilder(
                "Select COUNT(tt.id)" +
                        " FROM Partner p" +
                        " left join p.transferTickets as tt left join tt.debt as debt WHERE (tt.status IS NULL or tt.status='APPROVED') AND p.id =:id "
        );



        if(name!=null){
            queryString.append(" AND tt.name LIKE:namePattern");

        }
        if(isPaid!=null){
            if(isPaid) {
                queryString.append(" AND (debt.isPaid IS NULL or debt.isPaid = TRUE)");
            }else{
                queryString.append(" AND debt.isPaid = FALSE ");
            }
        }



        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");

        }

        query.setParameter("id", id);

        return (long)query.getSingleResult();
    }

    @Override
    public List<DebtDetailDto> getDebtsOfPartnerById(Integer pageIndex, Integer pageSize, Integer partnerId, Boolean isPaid){
        StringBuilder queryString = new StringBuilder(
                        """ 
                        SELECT new nsv.com.nsvserver.Dto.DebtDetailDto(d.id, d.name, d.amount, d.createDate, d.dueDate, d.isPaid, d.note, d.unit, d.paidDate) 
                        FROM debt d left join d.transferTicket as t join t.partner as p on p.id=:partnerId """
        );

        if(isPaid!=null){
            queryString.append(" WHERE d.isPaid=: isPaid");
        }
        Query query= entityManager.createQuery(queryString.toString());

        if(isPaid!=null){
            query.setParameter("isPaid", isPaid);
        }
        query.setParameter("partnerId",partnerId);

        return query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public long countDebtsOfPartnerById(Integer pageIndex, Integer pageSize, Integer partnerId, Boolean isPaid){
        StringBuilder queryString = new StringBuilder(
                """
                SELECT COUNT(d.id) FROM debt d join d.transferTicket as t join t.partner as p on p.id=:partnerId """
        );

        if(isPaid!=null){
            queryString.append(" WHERE d.isPaid =:isPaid");
        }
        Query query= entityManager.createQuery(queryString.toString());

        if(isPaid!=null){
            query.setParameter("isPaid", isPaid);
        }

        query.setParameter("partnerId",partnerId);

        return (long) query.getSingleResult();
    }

}
